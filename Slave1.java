
public class Slave1 extends Thread {

    private int id;
    private Operation operation;
    private String message;

    public Slave1(int id, Operation o) {
        this.id = id;
        this.operation = o;
        message = null;
    }

    public String job1() throws InterruptedException {
        Thread.sleep(2000);
        return("Executed operation 1 in slave 1");
    }

    // inefficient way to do job 2.
    public String job2() throws InterruptedException {
        Thread.sleep(10000);
        return("Executed operation 2 in slave 1");
    }

    public void setID(int num) {
        this.id = num;
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

/* Progress Report 1
* We did it all together on zoom. Devorah typed the code and came up with idea to use an enum for ADD and SUBTRACT,
* Ilana came up with the idea of how to organize the classes and what jobs the slaves should do.
* Fraida came up with how to implement the methods.
* Nechama formed the packet structure.
*
* We created all classes that we are going to need: Two slaves, an AddingSlave and a Subtracting Slave,
* a Client, a Master, and a Packet. We also made an enum so the packet includes metadata of whether the numbers are
* intended for adding or subtracting.
*
* The Adding slave can add and subtract but subtracting is less efficient, and vice versa for the Subtracting Slave.
*
* We completed the Packet structure, which holds the packet number, the total number of packets,
* and two numbers that it will add or subtract.
*
* */
