package objects;
import main.ConstantValues;

/** strong enemy, it can move through Y axis and fire bullets
 * it score and health is bigger than the weak enemy and medium enemy
 * it also moves faster int the Y direction but opposite in the X direction */
public class StrongEnemy extends Enemy{

    /** constructor of the medium enemy with healt 50 */
    public StrongEnemy() {
        super( ConstantValues.STRONG_ENEMY_WIDTH, ConstantValues.STRONG_ENEMY_HEIGHT, ConstantValues.STRONG_ENEMY_SCORE, ConstantValues.STRONG_ENEMY_DISPLACEMENTX,
                (Math.random() < 0.5 ? Direction.LEFT : Direction.RIGHT), 50);
    }

    /** move the medium enemy on the Y direction */
    public void moveDown()
    {
        // displacement amount of the Y axis
        double displacementY = ConstantValues.STRONG_ENEMY_DISPLACEMENTY;
        setCoordY(getCoordY() + displacementY);
    }

}
