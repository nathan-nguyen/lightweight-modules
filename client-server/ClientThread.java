import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket client;

    public ClientThread(Socket client) {
        this.client = client;
    }

    public void run() {
        String line;
        BufferedReader in = null;
        PrintWriter out = null;

        FileIO fileIO = FileIO.instance();

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            System.out.println("Connected to client");
        } catch (IOException e) {
            System.out.println("IO Failed");
            return;
        }

        while (true) {
            try {
                line = in.readLine();
                if (line == null) {
                    System.out.println("Client disconnected");
                    return;
                }
                System.out.println("[Client] : " + line);

                // Start a thread to handle the client message
                OutputThread outputThread = new OutputThread(line);
                Thread t = new Thread(outputThread);
                t.start();

                // Send data back to the client
                out.println("Updated DB");
            } catch (IOException e) {
                System.out.println("Read failed");
                return;
            }
        }
    }

    public class OutputThread implements Runnable {
        private static final String OUTPUT_FILE = "entry.dat";
        private String updateData;

        // Update DB or log to file
        public OutputThread(String updateData) {
            this.updateData = updateData;
        }

        public void run() {
            try {
                FileIO.instance().append(OUTPUT_FILE, updateData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
