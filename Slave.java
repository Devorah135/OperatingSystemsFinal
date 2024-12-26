import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Slave {
	
	public static void main(String[] args) {
		int slaveType = Integer.parseInt(args[0]);
		int port = 30123;
		
		try (
			Socket slaveSocket = new Socket("localhost", port);
			PrintWriter out = new PrintWriter(slaveSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(slaveSocket.getInputStream()))
			) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Slave started on port " + port);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread slave;
		
		switch(slaveType) {
		case 1:
			slave = new Slave1();
			break;
		case 2: 
			slave = new Slave2();
			break;
		default:
			System.out.println("Invalid args.");
			return;
		}
		
		slave.start();
		
	}

}
