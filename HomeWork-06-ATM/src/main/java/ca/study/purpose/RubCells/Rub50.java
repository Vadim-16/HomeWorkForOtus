package ca.study.purpose.RubCells;


public class Rub50 implements ATMCell {
    public static final int value = 50;
    private int count;

    public Rub50(int count) {
        this.count = count;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void deposit(int billCount) {
        this.count += billCount;
    }

    @Override
    public void withdraw(int billCount) {
        this.count -= billCount;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getAmount() {
        return value * count;
    }

}
