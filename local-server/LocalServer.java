import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LocalServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		String answer = "";
		try
		{
			serverSocket = new ServerSocket(7777);
			System.out.println("Listening : 7777");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		while(true)
		{
			try
			{
				socket = serverSocket.accept();
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				answer = input.readLine();
				System.out.println("ip: " + socket.getInetAddress());
				//System.out.println("mesage: " + dis.readUTF());
				System.out.println("mesage: " + answer);
				dos.writeUTF("Hello! " + socket.getInetAddress().toString());
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (socket != null)
	    		{
	    			try
	    			{
	    				socket.close();
	    			}
	    			catch(IOException e)
	    			{
	    				e.printStackTrace();
	    			}
	    		}
	    		
	    		if (dos != null)
	    		{
	    			try
	    			{
	    				dos.close();
	    			}
	    			catch(IOException e)
	    			{
	    				e.printStackTrace();
	    			}
	    		}
	    		
	    		if (dis != null)
	    		{
	    			try
	    			{
	    				dis.close();
	    			}
	    			catch(IOException e)
	    			{
	    				e.printStackTrace();
	    			}
	    		}
			}
		}
	}
}
