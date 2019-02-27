package objects;

/** abstract super class for creating Game objects such as Bullet, Enemy, Player */
public abstract class Object{

    double width ;      // objects width
    double height ;    // objects height

    private double coordX;
    private double coordY;

    private boolean isAlive = true;

    /** constructor for when the imageURL, width and heigth are given
     it uses imageURL to create new image and calls to constructor of the imageview class with given width and height */
    Object(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getCoordX()
    {
        return coordX;
    }
    public double getCoordY()
    {
        return coordY;
    }

    /** Getter method for isAlive */
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setCoordX(double coordX)
    {
        this.coordX = coordX;
    }

    /** Setter function method for setting Y coordinate of the object */
    public void setCoordY(double coordY)
    {
        this.coordY = coordY;
    }

    /** if intersection occur return true else false */
    public boolean intersects(double coordX, double coordY, double width, double height) {
        if( (this.coordY + this.height < coordY) || this.coordY  > coordY + height )
            return false;
        return !(this.coordX + this.width < coordX) && !(this.coordX > coordX + width);
    }

    /** Getter method for width */
    public double getWidth() {
        return width;
    }

    /** Getter method for height */
    public double getHeight() {
        return height;
    }

    /** Getter method for getting X coordinate of the object */
    public double getCenterX() {
        return coordX + (width / 2);
    }

    /** Getter method for getting Y coordinate of the object */
    double getCenterY() {
        return coordY + (height / 2);
    }

    /** abstract method move, it overwrited in subclasses */
    public abstract void move();
}
