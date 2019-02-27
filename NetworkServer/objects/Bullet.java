package objects;

import main.ConstantValues;


// Bullet class for creating bullet and shooting enemies
public class Bullet extends Object{

    private double displacementY;                       // diplacement on the Y direction of the bullet
    private int damage = ConstantValues.BULLET_DAMAGE;  // health damage of the bullet
    private int objectNo;


    // Constructor for the bullet with X-Y coordinate and diplacement amount on the Y direction
    // it takes X-Y coordinate where the bullet will created and calls the super class constructor with default bullet width and height
    // it also change the default displacement amount on the Y direction
    public Bullet(double originX, double originY, double displacementY, int objectNo) {
        super(ConstantValues.BULLET_WIDTH, ConstantValues.BULLET_HEIGHT);
        setCoordX(originX);
        setCoordY(originY);
        this.displacementY = displacementY;
        this.objectNo = objectNo;
    }

    public int getObjectNo()
    {
        return this.objectNo;
    }

    // get damage of the bullet
    public int getDamage() {
        return damage;
    }

    // set damage of the bullet
    public void setDamage(int damage) {
        this.damage = damage;
    }

    // get displacement amount of the bullet on the Y direction
    public double getDisplacementY() {
        return displacementY;
    }

    // set displacement amount of the bullet on the Y direction
    public void setDisplacementY(double displacementY) {
        this.displacementY = displacementY;
    }

    // override the move method, bullet only move through Y axis with the bullets diplacementY
    @Override
    public void move() {
        setCoordY(getCoordY() - displacementY);
    }
}
