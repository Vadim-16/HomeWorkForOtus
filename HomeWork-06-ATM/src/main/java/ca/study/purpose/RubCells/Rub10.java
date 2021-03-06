package ca.study.purpose.RubCells;


public class Rub10 implements ATMCell {
    public static final int value = 10;
    private int count;

    public Rub10(int count) {
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
