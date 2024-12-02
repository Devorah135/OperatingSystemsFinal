
public class Packet {
    private Operation operation;
    private int id;

    public Packet(int id, Operation operation) {
        this.operation = operation;
        this.id = id;
    }

    public Packet() {
        this(0, null);
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

   

    @Override
    public String toString() {
        return id + "|" + operation;
    }

    public static Packet fromString(String packetString) {
        String[] parts = packetString.split("\\|", -1);

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid packet string: " + packetString);
        }
        
		int id = Integer.parseInt(parts[0]);
        Operation operation = Operation.valueOf(parts[1]);
        
        return new Packet(id, operation);
    }

    public String getData() {
        return toString();
    }
}