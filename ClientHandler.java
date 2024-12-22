import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

	private Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

			String input;
			while ((input = in.readLine()) != null) {
				Packet receivedPacket = Packet.fromString(input);
				System.out.println("Received: " + input);

				out.println("Job '" + input + "' acknowledged by master.");
				
				Operation op = receivedPacket.getOperation();
				switch (op) {
				case JOB_1:				
					Slave1 slave1 = new Slave1(receivedPacket.getId(), receivedPacket.getOperation());
					slave1.start();
					break;
				case JOB_2:
					Slave2 slave2 = new Slave2(receivedPacket.getId(), receivedPacket.getOperation());				
					slave2.start();
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
