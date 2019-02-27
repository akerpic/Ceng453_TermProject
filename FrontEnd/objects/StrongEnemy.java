package objects;
import game.ConstantValues;
import game.GamePane;

// strong enemy, it can move through Y axis and fire bullets
// it score and health is bigger than the weak enemy and medium enemy
// it also moves faster int the Y direction but opposite in the X direction
public class StrongEnemy extends Enemy{

    // constructor of the medium enemy with healt 50
    public StrongEnemy() {
        super(ConstantValues.ENEMY_IMAGEURL, ConstantValues.STRONG_ENEMY_WIDTH, ConstantValues.STRONG_ENEMY_HEIGHT, ConstantValues.STRONG_ENEMY_SCORE, ConstantValues.STRONG_ENEMY_DISPLACEMENTX,
                (Math.random() < ConstantValues.ARRANGE_ENEMY_RANDOM_DIRECTION ? Direction.LEFT : Direction.RIGHT), ConstantValues.STRONG_ENEMY_HEALTH);
    }

    // move the medium enemy on the Y direction
    public void moveDown()
    {
        // displacement amount of the Y axis
        double displacementY = ConstantValues.STRONG_ENEMY_DISPLACEMENTY;
        setY(getY() + displacementY);
    }

    // medium enemy can fire downside with some displacement Y
    public void fireBullet() {
        ((GamePane)getParent()).getEngine().queueAddition(
                new Bullet(ConstantValues.ENEMY_BULLET_IMAGEURL,getCenterX() + (ConstantValues.BULLET_WIDTH / 2), getY() + ConstantValues.BULLET_HEIGHT, ConstantValues.STRONG_ENEMY_BULLET_DISPLACEMENTY));
    }
}
