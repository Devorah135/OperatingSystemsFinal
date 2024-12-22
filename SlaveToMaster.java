import java.util.concurrent.BlockingQueue;

public class SlaveToMaster extends Thread {
    private BlockingQueue<String> queue;
    private String slaveName;

    public SlaveToMaster(BlockingQueue<String> queue, String slaveName) {
        this.queue = queue;
        this.slaveName = slaveName;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                String message = slaveName + " Message " + i;
                queue.put(message); // Send message to the master
                System.out.println(slaveName + " sent: " + message);
                Thread.sleep(500); // Simulate time to generate the message
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}