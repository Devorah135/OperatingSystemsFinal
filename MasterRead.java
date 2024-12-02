import java.io.*;
import java.net.*;

public class MasterRead extends Thread {
	
	private Socket socket;
	
	public MasterRead(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());) 
		{
			Object o = input.readObject();
			System.out.println(o.toString());
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

