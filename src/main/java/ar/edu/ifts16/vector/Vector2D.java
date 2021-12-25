package ar.edu.ifts16.vector;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0.0D;
        this.y = 0.0D;
    }

    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.getX(), this.y + v.getY());
    }

    public Vector2D subtrac(Vector2D v) {
        return new Vector2D(this.x - v.getX(), this.y - v.getY());
    }

    public Vector2D scale(double value) {
        return new Vector2D(this.x * value, this.y * value);
    }

    public Vector2D limit(double value) {
        if (getMagnitude() > value) {
            return  this.normalize().scale(value);
        }
        return this;
    }

    public Vector2D normalize() {
        double magnitude = getMagnitude();
        return new Vector2D(this.x / magnitude, this.y / magnitude);
    }

    public double getMagnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2D setDirection(double angle) {
        double magnitude = getMagnitude();
        return new Vector2D(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

    public double getAngle(){
        return Math.asin(y/getMagnitude());
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
