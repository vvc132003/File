package data.model;

public class Rect extends Shape{
    private double a;
    private double b;

    public Rect(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double chuVi() {
        return 2 * (a + b);
    }

    @Override
    public double dienTich() {
        return a * b;
    }
}
