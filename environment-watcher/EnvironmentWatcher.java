import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EnvironmentWatcher {
	public static final int SSH_PORT = 22;

	private String environment;
	private String command;
	private int refreshRate;

	private Properties properties;
	private Session session;
	private Channel channel;
	private PipedOutputStream pipedOutputStream;
	private BufferedReader consoleOutput;

	private List<String> currentCommand = new ArrayList<>();

	public EnvironmentWatcher(String environment) {
		this.environment = environment;
	}

	private void initializeEnvironment() throws Exception {
		System.out.println("Checking environment: " + environment + " ...");

		this.properties = new Properties();
		FileReader fileReader = new FileReader("environment.properties");

		properties.load(fileReader);
		fileReader.close();

		String username = properties.getProperty(environment + ".username");
		String hostname = properties.getProperty(environment + ".hostname");
		String password = properties.getProperty(environment + ".password");
	
		this.command = properties.getProperty(environment + ".command");
		if (command == null) this.command = properties.getProperty("default.command");

		this.refreshRate = Integer.parseInt(properties.getProperty("default.refreshRate"));

		this.session = getSession(username, password, hostname);
	}

	private Session getSession(String username, String password, String host) throws JSchException {
		JSch jsch = new JSch();
		session = jsch.getSession(username, host, SSH_PORT);
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setConfig("PrefererredAuthentications", "publickey,keyboard-interative,password");
		session.connect();
		return session;
	}

	private void connectShell() throws Exception {
		channel = session.openChannel("shell");
		InputStream inputStream = new PipedInputStream();
		pipedOutputStream = new PipedOutputStream((PipedInputStream) inputStream);
		OutputStream outputStream = new PipedOutputStream();
		PipedInputStream pipedInputStream = new PipedInputStream((PipedOutputStream) outputStream);
		
		consoleOutput = new BufferedReader(new InputStreamReader(pipedInputStream));

		channel.setInputStream(inputStream);
		channel.setOutputStream(outputStream);
		channel.connect();
	}

	private void disconnectShell() throws IOException {
		if (channel != null && channel.isConnected()) {
			pipedOutputStream.close();
			consoleOutput.close();
		}
		channel.disconnect();
	}

	private void startWatching() throws Exception {
		System.out.println("Start watching environment " + environment + " ...");

		while (true) {
			if (channel.isClosed()) {
				System.out.println("exit-status: " + channel.getExitStatus());
				disconnectShell();
				return;
			}

			List<String> outputList = runScript(command);
			List<String> list = new ArrayList<>();
			StringBuilder fileLog = new StringBuilder();

			for (String output: outputList) {
				if (output.contains("+ ")) {
					output.replaceAll("\\+", "");
					list.add(output);

					if (! currentCommand.contains(output)) {
						fileLog.append("[In " + System.currentTimeMillis() + "] " + output);
						System.out.println("[In " + System.currentTimeMillis() + "] " + output);
						fileLog.append("\n");
					}
				}
			}

			for (String line: currentCommand) {
				if (! list.contains(line)) {
					fileLog.append("[Out " + System.currentTimeMillis() + "] " + line);
					System.out.println("[Out " + System.currentTimeMillis() + "] " + line);
					fileLog.append("\n");
				}
			}

			if (fileLog.toString().length() != 0) writeLog(fileLog.toString());
			currentCommand = list;

			Thread.sleep(refreshRate);
			System.out.println("\nWatching ...");
		}
	}

	private List<String> runScript(String command) throws Exception {
		List<String> buffer = new ArrayList<>();
		pipedOutputStream.write(command.getBytes());
		
		while (true) {
			String output = consoleOutput.readLine();
			if (output.contains("grep")) continue;
			if (output.contains("-187")) break;
			buffer.add(output);
		}
		return buffer;
	}

	private void writeLog(String message) throws IOException {
		String fileName = properties.getProperty("log.directory") + environment + ".log";
		File file = new File(fileName);

		if (!file.exists()) file.createNewFile();

		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		bufferedWriter.write(message);

		bufferedWriter.close();
		fileWriter.close();
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments. Expecting 1 argument for environment name");
		}

		EnvironmentWatcher environmentWatcher = new EnvironmentWatcher(args[0]);
		environmentWatcher.initializeEnvironment();
		environmentWatcher.connectShell();
		environmentWatcher.writeLog("[New Run]");
		environmentWatcher.startWatching();
	}
}
