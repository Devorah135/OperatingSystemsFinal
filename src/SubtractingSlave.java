public class SubtractingSlave {

    private int num1;
    private int num2;

    public SubtractingSlave(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public int subtract() {
        return num1 - num2;
    }

    //inefficient way to add
    public int add(){
        int sum = num1 + num2;
        return sum;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }
}
