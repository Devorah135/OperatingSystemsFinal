import java.util.concurrent.BlockingQueue;

public class Slave2 extends Thread {

	private BlockingQueue<Packet> queue;
	private ClientHandler clientHandler;

	public Slave2(BlockingQueue<Packet> blockingQueue) {
		this.queue = blockingQueue;
	}

	 // Setter for ClientHandler
    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
    
    public String job1() throws InterruptedException {
        Thread.sleep(3000); // Simulate work (different timing for Slave2)
        return "Executed operation 1 in slave 2";
    }

    public String job2() throws InterruptedException {
        Thread.sleep(8000); // Simulate work (different timing for Slave2)
        return "Executed operation 2 in slave 2";
    }

    @Override
    public void run() {
        while (true) { // Keep processing jobs from the queue
            try {
                // Take a job from the queue (blocks if the queue is empty)
                Packet p = queue.take();
                Operation o = p.getOperation();
                String result;

                // Process the job based on its type
                switch (o) {
                    case JOB_1:
                        result = job1();
                        break;
                    case JOB_2:
                        result = job2();
                        break;
                    default:
                        result = "Unknown job type.";
                }
                clientHandler.updateCounterAfterCompletion(o, 2); // Notify master to update counter
                System.out.println("Slave 2 completed: " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Slave 2 interrupted: " + e.getMessage());
                break; // Exit loop on interruption
            }
        }
    }

	public BlockingQueue<Packet> getQueue() {
		return queue;
	}
}

