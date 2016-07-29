import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
	int portNumber;

	try {
	    portNumber = Integer.parseInt(args[0]);
	} catch (Exception e) {
	    System.out.println("Port Number Required\n java Server <portNumber>");
	    return;
	}
	
	ServerSocket serverSocket = null;

	try {
	    serverSocket = new ServerSocket(portNumber);
	} catch (IOException e) {
	    System.out.println(e.getMessage());
	}

	while (true){
	    ClientThread clientThread;
	    try{
		clientThread =  new ClientThread(serverSocket.accept());
		Thread t = new Thread(clientThread);
		t.start();
	    } catch (IOException e) {
		System.out.println(e.getMessage());
	    }
	}
    }
}
