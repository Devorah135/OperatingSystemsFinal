import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: java SimpleClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket clientSocket = new Socket(hostName, portNumber);
             PrintWriter requestWriter = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader responseReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            // Enter message to send to the server
            System.out.println("Enter your message: ");
            String userMessage = stdIn.readLine();
            requestWriter.println(userMessage);

            // Receive packets from the server
            ArrayList<String> packetsString = new ArrayList<>();
            int totalPacketsReceived = -1;
            String serverResponse;

            while ((serverResponse = responseReader.readLine()) != null) {
                Packet receivedPacket = Packet.fromString(serverResponse);

                if (receivedPacket.getData().equals("END")) {
                    break;
                }

                if (totalPacketsReceived == -1) {
                    totalPacketsReceived = receivedPacket.getTotalpackets();
                    for (int i = 0; i < totalPacketsReceived; i++) {
                        packetsString.add(null);
                    }
                }

                // Store the received packet
                packetsString.set(receivedPacket.getPacketnumber(), receivedPacket.getData());
            }

            // Identify missing packets
            List<Integer> missingPacketsInt = new ArrayList<>();
            for (int i = 0; i < totalPacketsReceived; i++) {
                if (packetsString.get(i) == null) {
                    missingPacketsInt.add(i);
                }
            }

            while (!missingPacketsInt.isEmpty()) {
                System.out.println("Requesting missing packets: " + missingPacketsInt.toString());
                requestWriter.println("Request missing packets: " + missingPacketsInt.toString());

                serverResponse = responseReader.readLine();
                Packet packet = Packet.fromString(serverResponse);
                packetsString.set(packet.getPacketnumber(), packet.getData());

                missingPacketsInt.remove(Integer.valueOf(packet.getPacketnumber()));
            }

            StringBuilder fullMessage = new StringBuilder();
            for (String packetInfo : packetsString) {
                fullMessage.append(packetInfo);
            }
            System.out.println("Full message: " + fullMessage.toString().trim());

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}