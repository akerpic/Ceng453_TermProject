package objects;

import game.ConstantValues;
import game.GamePane;

// medium enemy, it can move through Y axis
// it score and health is bigger than the weak enemy and smaller than the strong enemy
public class MediumEnemy extends Enemy {

    // constructor of the medium enemy with healt 30
    public MediumEnemy() {
        super(ConstantValues.ENEMY_IMAGEURL, ConstantValues.MEDIUM_ENEMY_WIDTH, ConstantValues.MEDIUM_ENEMY_HEIGHT, ConstantValues.MEDIUM_ENEMY_SCORE, ConstantValues.MEDIUM_ENEMY_DISPLACEMENTX,
                (Math.random() < ConstantValues.ARRANGE_ENEMY_RANDOM_DIRECTION ? Direction.LEFT : Direction.RIGHT), ConstantValues.MEDIUM_ENEMY_HEALTH);
    }

    // medium enemy can fire downside with some displacement Y which is smaller than the strong enemies
    public void fireBullet() {
        ((GamePane)getParent()).getEngine().queueAddition(
                new Bullet(ConstantValues.ENEMY_BULLET_IMAGEURL,getCenterX() + (ConstantValues.BULLET_WIDTH / 2), getY() + ConstantValues.BULLET_HEIGHT, ConstantValues.MEDIUM_ENEMY_BULLET_DISPLACEMENTY));
    }

    // move the medium enemy on the Y direction
    public void moveDown()
    {
        // displacement amount of the Y axis
        double displacementY = ConstantValues.MEDIUM_ENEMY_DISPLACEMENTY;
        setY(getY() + displacementY);
    }
}
