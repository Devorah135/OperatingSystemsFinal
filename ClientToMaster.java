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
			
<<<<<<< HEAD
			output.writeObject("from the client");
=======
			output.writeObject(  "from the client" );
			Master master = new Master();
			
>>>>>>> 476464fcd2cb9224152cf11ce298f0c40361bf67
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
