package objects;

import main.ConstantValues;

/** medium enemy, it can move through Y axis
 * it score and health is bigger than the weak enemy and smaller than the strong enemy */
public class MediumEnemy extends Enemy {

    private double displacementY = ConstantValues.MEDIUM_ENEMY_DISPLACEMENTY; // displacement amount of the Y axis

    /** constructor of the medium enemy with healt 30 */
    public MediumEnemy() {
        super(ConstantValues.MEDIUM_ENEMY_WIDTH, ConstantValues.MEDIUM_ENEMY_HEIGHT, ConstantValues.MEDIUM_ENEMY_SCORE, ConstantValues.MEDIUM_ENEMY_DISPLACEMENTX,
                (Math.random() < 0.5 ? Direction.LEFT : Direction.RIGHT), 30);
    }

    /** move the medium enemy on the Y direction */
    public void moveDown()
    {
        setCoordY(getCoordY() + displacementY);
    }
}
