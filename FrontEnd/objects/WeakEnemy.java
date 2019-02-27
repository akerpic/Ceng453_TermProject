package objects;

import game.ConstantValues;

// a subclass of the enemy
public class WeakEnemy extends Enemy{

    // it creates a enemy with the health 10, and some diplacement amount on the X direction, it also has score value which player get when player shoot the enemy
    // score value = 10, direction chooses randomly
    public WeakEnemy() {
        super(ConstantValues.ENEMY_IMAGEURL, ConstantValues.WEAK_ENEMY_WIDTH, ConstantValues.WEAK_ENEMY_HEIGHT, ConstantValues.WEAK_ENEMY_SCORE, ConstantValues.WEAK_ENEMY_DISPLACEMENTX,
                (Math.random() < ConstantValues.ARRANGE_ENEMY_RANDOM_DIRECTION ? Direction.LEFT : Direction.RIGHT), ConstantValues.WEAK_ENEMY_HEALTH);
    }
}
