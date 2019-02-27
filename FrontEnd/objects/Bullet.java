package objects;

import game.ConstantValues;


// Bullet class for creating bullet and shooting enemies
public class Bullet extends Object{

    private double displacementY;                       // diplacement on the Y direction of the bullet

    // Constructor for the bullet
    // it takes and image and calls the super class constructor with default bullet width and height
    public Bullet() {
        super(ConstantValues.BULLET_IMAGEURL, ConstantValues.BULLET_WIDTH, ConstantValues.BULLET_HEIGHT);
    }

    // Constructor for the bullet with X-Y coordinate and diplacement amount on the Y direction
    // it takes X-Y coordinate where the bullet will created and calls the super class constructor with default bullet width and height
    // it also change the default displacement amount on the Y direction
    Bullet(String imageURL, double originX, double originY, double displacementY) {
        super(imageURL, ConstantValues.BULLET_WIDTH, ConstantValues.BULLET_HEIGHT);
        setX(originX);
        setY(originY);
        this.displacementY = displacementY;
    }

    // get damage of the bullet
    public int getDamage() {
        // health damage of the bullet
        return ConstantValues.BULLET_DAMAGE;
    }

    // get displacement amount of the bullet on the Y direction
    public double getDisplacementY() {
        return displacementY;
    }

    // override the move method, bullet only move through Y axis with the bullets diplacementY
    @Override
    public void move() {
        setY(getY() - displacementY);
    }
}
