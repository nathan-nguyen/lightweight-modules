import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
    public static void main(String[] args){
	String hostName;
	int portNumber;

	try {
	    hostName = args[0];
	    portNumber = Integer.parseInt(args[1]);
	} catch (Exception e){
	    System.out.println("java Client <ipAddress> <portNumber>");
	    return;
	}

	try {
	    Socket s = new Socket(hostName, portNumber);
	    Scanner in = new Scanner(System.in);
	    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
	    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

	    System.out.println("Connected to Server");
	    System.out.print("[Client] : ");
	    String message = in.nextLine();

            while (message != "[End]") {
                try {
                    out.println(message);
                    String answer = input.readLine();

		    if (answer == null) {
			System.out.println("Server disconnected");
			return;
		    }

		    System.out.println("[Server] : " + answer);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Send message IO Exception");
                }
                System.out.print("[Client] : ");
                message = in.nextLine();
            }
            s.close();
        }
        catch (IOException e) {
            System.out.println("Cannot connect to server");
        }
    }
}
