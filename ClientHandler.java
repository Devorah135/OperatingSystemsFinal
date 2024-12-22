import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {

	private Socket clientSocket;
	private BlockingQueue<Packet> queue;
	Slave1 slave1;
	Slave2 slave2;

	public ClientHandler(Socket clientSocket,  BlockingQueue<Packet> queue, Slave1 slave1, Slave2 slave2) {
		this.clientSocket = clientSocket;
		this.queue = queue;
		this.slave1 = slave1;
		this.slave2 = slave2;

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
				
				BlockingQueue <Packet>  queue1=slave1.getQueue();
				BlockingQueue <Packet> queue2 = slave2.getQueue();
				int counts1 =0 ;
				int counts2 =0;
				

				for(Packet p : queue1){
					if(p.getOperation() == Operation.JOB_1 ){
						counts1 += 2;
					}
					else if(p.getOperation() == Operation.JOB_2){
						counts1 += 10;
					}
					System.out.println("this is in the queue1 forloop");
				}
				
				for(Packet p : queue2){
					if(p.getOperation() == Operation.JOB_1 ){
						counts2 += 10;
					}
					else if(p.getOperation() == Operation.JOB_2){
						counts2 += 2;
					}
					System.out.println("this is in the queue2 forloop");
				}


                System.out.println("count1 " + counts1 + " count2 " + counts2);
				switch (op) {
				case JOB_1:		
				System.out.println("in job1");	
					if((counts1 - counts2) >= 6 ){
						queue2.put(receivedPacket);
						System.out.println("received packet 1 ");
					}
					else{
						queue1.put(receivedPacket);
						System.out.println("received packet 2 ");
					}
					break;
				case JOB_2:
				System.out.println("in job2");
				if((counts2 - counts1) >= 6 ){
					queue1.put(receivedPacket);
					System.out.println("received packet 2 ");
				}
				else{
					queue2.put(receivedPacket);
					System.out.println("received packet 1 ");
				}
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
