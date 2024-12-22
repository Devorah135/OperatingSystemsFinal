import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BlockingQueue<Packet> queue;
    private Slave1 slave1;
    private Slave2 slave2;

    private int counts1 = 0; // shared resource for queue1
    private int counts2 = 0; // shared resource for queue2

    public ClientHandler(Socket clientSocket, BlockingQueue<Packet> queue, Slave1 slave1, Slave2 slave2) {
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
                System.out.println("Received: id " + receivedPacket.toString());

                out.println("Job '" + input + "' acknowledged by master.");

                Operation op = receivedPacket.getOperation();

                // Decide where to put the packet based on the current counters before updating them
                synchronized (this) {
                    System.out.println("Counter for queue 1: " + counts1);
                    System.out.println("Counter for queue 2: " + counts2);

                    switch (op) {
                        case JOB_1:
                            System.out.println("Received a packet with job 1. Placing it on a slave...");
                            if ((counts1 - counts2) >= 6) {
                                // Send to slave 2 if counts1 is significantly higher
                                slave2.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 2");
                            } else {
                                // Otherwise, send to slave 1
                                slave1.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 1");
                            }
                            counts1 += 2;  // Update counts after the decision
                            break;

                        case JOB_2:
                            System.out.println("Received a packet with job 2. Placing it on a slave...");
                            if ((counts2 - counts1) >= 6) {
                                // Send to slave 1 if counts2 is significantly higher
                                slave1.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 1");
                            } else {
                                // Otherwise, send to slave 2
                                slave2.getQueue().put(receivedPacket);
                                System.out.println("Packet sent to slave 2");
                            }
                            counts2 += 10;  // Update counts after the decision
                            break;
                    }

                    // Send back the confirmation to the client
                    out.println("Job completed by slaves for packet: " + input);
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
        		counts1 -= 2;  // Decrease counter after JOB_1 is completed
        	else if (slave == 2)
        		counts2 -= 10;
        } else if (op == Operation.JOB_2) {
        	if (slave == 1)
        		counts1 -= 10; // Decrease counter after JOB_2 is completed
        	else if (slave == 2)
        		counts2 -= 2;
        }

        System.out.println("Updated counters: Counter 1 = " + counts1 + ", Counter 2 = " + counts2);
    }
}

