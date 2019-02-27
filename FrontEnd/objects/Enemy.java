package objects;

import game.ConstantValues;

// Enemy class for different type of enemies, extends from Object class
public class Enemy extends Object{

    private double displacementX;  // displacement amount in the X direction through given timeline in the game engine
    private int scoreValue;                                             // score value the player get when the player kills the enemy
    private Direction dir;                             // enemies move left-to-right and right-to-left, default right-to-left
    private int health;                                                 // health of the enemy, health differs according to type of the enemy


    // Constructor for the enemy
    // it takes image url and width, height to set enemy image and set scorevalue for the enemy
    // it also sets the diplacement amount on the X direction and health
    Enemy(String imageURL, double width, double height, int scoreValue, double displacementX, Direction dir, int health) {
        super(imageURL, width, height);
        setRandomInitialPosition(width, height);
        this.scoreValue = scoreValue;
        this.displacementX = displacementX;
        this.dir = dir;
        this.health = health;
    }

    // set initial position of the enemy randomly
    // it takes enemy width and height as input and set X coordinate of the enemy randomly
    // set Y coordinate of the enemy some top parts on the screen
    private void setRandomInitialPosition(double width, double height) {
        setX(Math.random() * (ConstantValues.SCREEN_WIDTH - width));
        setY(height * (int)(ConstantValues.ENEMY_INITIAL_POSITION_ARRANGE + Math.random() * ConstantValues.SCREEN_HEIGHT / ConstantValues.ENEMY_INITIAL_POSITION_ARRANGE_Y / height));
    }

    // Getter and setter for the score value
    public int getScoreValue() {
        return scoreValue;
    }

    // Getter and setter for the health of the enemy
    public int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
    }

    // damage funcion when the bullet hits the enemy, it decreases its health
    public void damage(int damage)
    {
        setHealth(getHealth() - damage);
    }

    // move method for the enemy
    // it moves only left and right direction
    // it checks the condition when the enemy reaches the border area of the game
    @Override
    public void move() {
        switch (dir) {
            case LEFT:
                if (getX() >= displacementX) setX(getX() - displacementX);
                else dir = Direction.RIGHT;
                break;
            case RIGHT:
                if (getX() < (ConstantValues.SCREEN_WIDTH - width - displacementX))
                    setX(getX() + displacementX);
                else dir = Direction.LEFT;
        }
    }

}