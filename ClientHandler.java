import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BlockingQueue<Packet> queue;
    private Slave1 slave1;
    private Slave2 slave2;

    private int counter1 = 0; // shared resource for queue1
    private int counter2 = 0; // shared resource for queue2

    // Client handler accepts the socket, a queue, and the two slave threads from main
    public ClientHandler(Socket clientSocket, BlockingQueue<Packet> queue) {
        this.clientSocket = clientSocket;
        this.queue = queue;
//        this.slave1 = slave1;
//        this.slave2 = slave2;
    }

    @Override
    public void run() {
        try (
        	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {

            String input; // the packet that is received from the client, in string form
            while ((input = in.readLine()) != null) {
                Packet receivedPacket = Packet.fromString(input); // convert packet string to a packet
                queue.put(receivedPacket); // add packet to shared queue of ALL packets
                System.out.println("Received: " + receivedPacket.toString());
                
                Operation op = receivedPacket.getOperation();
                int id = receivedPacket.getId();
                
                out.println("Packet #" + id + ", (" + op +  ") acknowledged by master.");

                // Decide where to put the packet based on the current counters before updating them
                synchronized (this) {
                	// display the counters
                    System.out.println("Counter for queue 1: " + counter1);
                    System.out.println("Counter for queue 2: " + counter2);

                    switch (op) {
                        case JOB_1:
                            System.out.println("Received a packet with job 1. Placing it on a slave...");
                            if ((counter1 - counter2) >= 6) {
                                // Send to slave 2 if counts1 is significantly higher
                                slave2.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 2");
                                counter2 += 10;
                            } else {
                                // Otherwise, send to slave 1
                                slave1.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 1");
                                counter1 += 2;
                            }
                            break;

                        case JOB_2:
                            System.out.println("Received a packet with job 2. Placing it on a slave...");
                            if ((counter2 - counter1) >= 6) {
                                // Send to slave 1 if counts2 is significantly higher
                                slave1.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 1");
                                counter1 += 10;
                            } else {
                                // Otherwise, send to slave 2
                                slave2.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 2");
                                counter2 += 2;
                            }
                            break;
                    }

                    // Send back the confirmation to the client
                    out.println(op + " completed by slaves for packet #" + id);
                }
            }
        } catch (IOException | InterruptedException e) {
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

    // Synchronized method to update counters after job completion
    public synchronized void updateCounterAfterCompletion(Operation op, int slave) {
        if (op == Operation.JOB_1) {
        	if (slave == 1)
        		counter1 -= 2;  // Decrease counter after JOB_1 is completed
        	else if (slave == 2)
        		counter2 -= 10;
        } else if (op == Operation.JOB_2) {
        	if (slave == 1)
        		counter1 -= 10; // Decrease counter after JOB_2 is completed
        	else if (slave == 2)
        		counter2 -= 2;
        }

       // System.out.println("Updated counters: Counter 1 = " + counter1 + ", Counter 2 = " + counter2);
    }
}

