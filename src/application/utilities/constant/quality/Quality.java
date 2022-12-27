package application.utilities.constant.quality;

public enum Quality{
    A(144), B(240);
    private int numVal;

    Quality(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
