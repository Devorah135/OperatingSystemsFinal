import java.util.concurrent.BlockingQueue;

public class Slave1 extends Thread {

	private BlockingQueue<Packet> queue;
    private ClientHandler clientHandler;

	public Slave1(BlockingQueue<Packet> blockingQueue) {
		this.queue = blockingQueue;
	}
	
	// Setter for ClientHandler
    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

	public String job1() throws InterruptedException {
		Thread.sleep(2000);
		return ("Executed operation 1 in slave 1");
	}

	// inefficient way to do job 2.
	public String job2() throws InterruptedException {
		Thread.sleep(10000);
		return ("Executed operation 2 in slave 1");
	}

	public void setQueue(BlockingQueue<Packet> queue) {
		this.queue = queue;

	}

	public BlockingQueue<Packet> getQueue() {
		return queue;

	}

	@Override
	public void run() {
		while (true) { // Keep processing jobs from the queue
			try {
				// Take a job from the queue (blocks if the queue is empty)
				Packet p = queue.take();
				Operation operation = p.getOperation();
				String result;

				// Process the job based on its type
				switch (operation) {
				case JOB_1:
					result = job1();
					break;
				case JOB_2:
					result = job2();
					break;
				default:
					result = "Unknown job type.";
				}
                clientHandler.updateCounterAfterCompletion(operation, 1); // Notify master to update counter
				System.out.println("Slave completed: " + result);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.err.println("Slave interrupted: " + e.getMessage());
				break; // Exit loop on interruption
			}
		}
	}
}
