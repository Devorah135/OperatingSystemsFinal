import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
	
	public static void main(String[] args) throws InterruptedException {
		
		int port = 30123;// Integer.parseInt(args[0]);
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {        
			System.out.println("Master started on port " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
                new Thread(new ClientHandler(clientSocket)).start(); // Start a new thread for each client
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
