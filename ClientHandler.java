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
				System.out.println("Received: " + receivedPacket);

				out.println("Job '" + input + "' acknowledged by master.");

				// Choose slaves based on the operation (JOB_1 or JOB_2)
				Slave1 slave1 = new Slave1(receivedPacket.getId(), receivedPacket.getOperation());
				Slave2 slave2 = new Slave2(receivedPacket.getId(), receivedPacket.getOperation());

				// Start the slave threads
				slave1.start();
				slave2.start();

				// Wait for the slave threads to finish and get their results
				slave1.join();
				slave2.join();

				// Send back the confirmation to the client
				out.println("Job completed by slaves for packet: " + packet);

			}
		} catch (IOException e) {
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
