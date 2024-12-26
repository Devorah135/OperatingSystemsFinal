import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Master {

	public static void main(String[] args) throws InterruptedException {

		int port = 30123;// Integer.parseInt(args[0]);
		BlockingQueue<Packet> queue = new LinkedBlockingQueue<>();

		try (
				ServerSocket serverSocket = new ServerSocket(port);
				Socket slaveSocket = new Socket("localhost", port);
				) {
			System.out.println("Master started on port " + port);
			System.out.println("Waiting for client connection...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected: " + clientSocket.getInetAddress());

				/*
				 * Slave1 slave1 = new Slave1(new LinkedBlockingQueue<Packet>());
				 * slave1.start(); 
				 * //System.out.println("Started slave 1"); 
				 * Slave2 slave2 = new Slave2(new LinkedBlockingQueue<Packet>()); slave2.start();
				 */
				//System.out.println("Started slave 2");
				
				MasterToSlave ms = new MasterToSlave(slaveSocket, queue);

				// Create the ClientHandler and set it for both slaves
				ClientHandler clientHandler = new ClientHandler(clientSocket, queue, slave1, slave2);

				// Set the client handler for both slaves
				slave1.setClientHandler(clientHandler);
				slave2.setClientHandler(clientHandler);

				// Start the ClientHandler in a new thread
				new Thread(clientHandler).start(); // Start a new thread for each client
				
				System.out.println("Started client handler");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// receive packet
		// parse the packet
		// algorithm to choose slave
		// pass job to slave that is chosen
		// slave passes back String confirmation
		// master passes the confirmation to the client

	}
}
