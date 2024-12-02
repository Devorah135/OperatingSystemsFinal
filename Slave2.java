public class Slave2 extends Thread{

    private int id;
    private Operation operation;
    private String message;

    public Slave2(int id, Operation o) {
        this.id = id;
        this.operation = o;
        message = null;
    }

    public String job2() throws InterruptedException {
       Thread.sleep(2000);
       return("Executed operation 2 in slave 2");
    }

    //inefficient way to do job 1
    public String job1() throws InterruptedException {
        Thread.sleep(10000);
        return("Executed operation 1 in slave 2");
    }
    
    public void setMessage(String msg) {
    	this.message = msg;
    }
    
    public String getMessgae() {
    	return message;
    }

   
    @Override
    public void run() {
    	String msg = null;
    	switch (operation) {
    	case JOB_1:
    		try {
				msg = job1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		break;
    	case JOB_2:
    		try {
				msg = job2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		break;
    	default: msg = null;
    		break;
    	}
    	setMessage(msg);
    	System.out.println(msg);
    }
}