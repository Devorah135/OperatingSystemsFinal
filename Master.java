import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
	
	public static void main(String[] args) {
		
		int portNumber = 30123;// Integer.parseInt(args[0]);
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				Socket slave1Socket = serverSocket.accept();
				Socket slave2Socket = serverSocket.accept();
				
				// Output streams to send data to the client
				PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader requestReader = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				) {
			System.out.println("Server up and running!");
		//need a thread for all ways - reading and writing
		Thread slave1a = new Slave1(0, Operation.JOB_1);
		Thread slave2a = new Slave2(0, Operation.JOB_1);
		Thread slave1b = new Slave1(0, Operation.JOB_2);
		Thread slave2b = new Slave2(0, Operation.JOB_2);
		
		slave1a.start();
		slave2a.start();
		slave1b.start();
		slave2b.start();
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		//receive packet
		//parse the packet
		//algorithm to choose slave
		//pass job to slave that is chosen
		//slave passes back String confirmation
		//master passes the confirmation to the client
		
		
		
		
	}
}
