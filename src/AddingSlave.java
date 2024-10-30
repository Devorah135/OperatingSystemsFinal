public class AddingSlave {

    private int num1;
    private int num2;

    public AddingSlave(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public int add() {
        return num1 + num2;
    }

    // inefficient way to subtr.
    public int subtract(){
        int diff = num1 - num2;
        return diff;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
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
