import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {

	private Socket clientSocket;
	private BlockingQueue<Packet> queue;

	public ClientHandler(Socket clientSocket,  BlockingQueue<Packet> queue) {
		this.clientSocket = clientSocket;
		this.queue = queue;

	}

	@Override
	public void run() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

			String input;
			while ((input = in.readLine()) != null) {
				Packet receivedPacket = Packet.fromString(input);
				queue.put(receivedPacket);
				System.out.println("Received: " + input);

				out.println("Job '" + input + "' acknowledged by master.");
				
				Operation op = receivedPacket.getOperation();
				
				switch (op) {
				case JOB_1:				
					
					break;
				case JOB_2:
				
					break;
				}

				// Start the slave threads
				// Wait for the slave threads to finish and get their results
				//slave1.join();
				//slave2.join();
				// Send back the confirmation to the client
				out.println("Job completed by slaves for packet: " + input);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Client disconnected.");
		}
	}
}
