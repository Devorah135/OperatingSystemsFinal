import java.net.*;
import java.util.concurrent.BlockingQueue;

public class MasterToSlave extends Thread {

	private Socket socket;
	private BlockingQueue<Packet> q;
	
	public MasterToSlave(Socket socket, BlockingQueue<Packet> q) {
		this.socket = socket;
		this.q = q;
	}
	
	@Override
	public void run() {
		
	}
}
