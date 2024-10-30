
public class Packet {
    private int packetnumber;
    private int totalpackets;
    private Operation operation;
    private int num1;
    private int num2;

    public Packet(int pn, int tp, int n1, int n2, Operation operation) {
        this.packetnumber = pn;
        this.totalpackets = tp;
        this.num1 = n1;
        this.num2 = n2;
        this.operation = operation;
    }

    public Packet() {
        this(0, 0, 0, 0, null);
    }

    public int getPacketnumber() {
        return packetnumber;
    }

    public void setPacketnumber(int packetnumber) {
        this.packetnumber = packetnumber;
    }

    public int getTotalpackets() {
        return totalpackets;
    }

    public void setTotalpackets(int totalpackets) {
        this.totalpackets = totalpackets;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public int[] getNumbers() {
        int[] array = {num1, num2};
        return array;
    }

    public void setNumbers(int n1, int n2) {
        this.num1 = n1;
        this.num2 = n2;
    }

    @Override
    public String toString() {
        return packetnumber + "|" + totalpackets + "|" + num1 + "|" + num2 + "|" + operation;
    }

    public static Packet fromString(String packetString) {
        String[] parts = packetString.split("\\|", -1);

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid packet string: " + packetString);
        }

        int packetnumber = Integer.parseInt(parts[0]);
        int totalpackets = Integer.parseInt(parts[1]);
        int num1 = Integer.parseInt(parts[2]);
        int num2 = Integer.parseInt(parts[3]);
        Operation operation = Operation.valueOf(parts[4]);
        return new Packet(packetnumber, totalpackets, num1, num2, operation);
    }

}