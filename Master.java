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
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();
		
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {        
			System.out.println("Master started on port " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
				Slave1 slave1 = new Slave1(receivedPacket.getId(), receivedPacket.getOperation());
					slave1.start();
				Slave2 slave2 = new Slave2(receivedPacket.getId(), receivedPacket.getOperation());				
					slave2.start();
				
                new Thread(new ClientHandler(clientSocket, queue, slave1, slave2 )).start(); // Start a new thread for each client
        
      
			}
		} catch (IOException e) {
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
