
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

	static int id = 0;

	public static void main(String[] args) throws IOException {

		/*
		 * if (args.length != 2) {
		 * System.err.println("Usage: java SimpleClient <host name> <port number>");
		 * System.exit(1); }
		 */

		String hostName = "localhost"; // args[0];
		int port = 8080; // Integer.parseInt(args[1]);

		try (Socket socket = new Socket(hostName, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			Scanner scanner = new Scanner(System.in);

			while (true) {
				System.out.println("Enter 1 for Job1 or 2 for Job2 or 3 to quit: ");
				int job = scanner.nextInt();
				scanner.nextLine();
				if (job == 3) {
					break; // Exit loop if user wants to stop
				}

				Packet packet = null;
				if (job == 1) {
					packet = new Packet(id++, Operation.JOB_1);
				} else if (job == 2) {
					packet = new Packet(id++, Operation.JOB_2);
				}
				out.println(packet.toString()); // Send job to master
				System.out.println("Sent: " + packet.toString());

				// Receive a response packet
				String response = in.readLine();
				System.out.println("Received: " + response);
			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			e.printStackTrace();
			System.exit(1);
		}
	}
}