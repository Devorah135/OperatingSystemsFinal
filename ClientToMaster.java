import java.io.*;
import java.net.*;

public class ClientToMaster extends Thread {

	private Socket socket;
	
	public ClientToMaster(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());) 
		
		{
			output.writeObject("from the client");
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
