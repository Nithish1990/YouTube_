package application.utilities.constant.speed;

public enum Speed {
    A(1), B(1.5);
    private double numVal;

    Speed(double numVal) {
        this.numVal = numVal;
    }

    public double getNumVal() {
        return numVal;
    }
}
