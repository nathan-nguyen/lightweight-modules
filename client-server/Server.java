import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Port Number Required\n java Server <portNumber>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while (true) {
                ClientThread clientThread = new ClientThread(serverSocket.accept());
                Thread t = new Thread(clientThread);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
