import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientToMaster extends Thread {

	private Socket socket;
	private BlockingQueue<Packet> queue;
	
	public ClientToMaster(Socket socket, BlockingQueue<Packet> q) {
		this.socket = socket;
		this.queue = q;
	}
	
	@Override
	public void run() {
		
		try (
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			) 
		
		{
			Packet next = null; 
			
			while (true) {
			if (!queue.isEmpty()) {
				synchronized(this) {
					next = queue.poll();
				}
				output.writeObject(next.toString());
			}
			}
			
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}