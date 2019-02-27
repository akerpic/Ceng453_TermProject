package objects;

import main.ConstantValues;

// Enemy class for different type of enemies, extends from Object class
public class Enemy extends Object{

    private double displacementX = ConstantValues.ENEMY_DISPLACEMENTX;  // displacement amount in the X direction through given timeline in the game engine
    private int scoreValue;                                             // score value the player get when the player kills the enemy
    private Direction dir = Direction.LEFT;                             // enemies move left-to-right and right-to-left, default right-to-left
    private int health;                                                 // health of the enemy, health differs according to type of the enemy

    // Constructor for the enemy
    // it takes image url and width, height to set enemy image and set scorevalue for the enemy
    // it also sets the diplacement amount on the X direction and health
    public Enemy(double width, double height, int scoreValue, double displacementX, Direction dir, int health) {
        super(width, height);
        setRandomInitialPosition(width, height);
        this.scoreValue = scoreValue;
        this.displacementX = displacementX;
        this.dir = dir;
        this.health = health;
    }

    // set initial position of the enemy randomly
    // it takes enemy width and height as input and set X coordinate of the enemy randomly
    // set Y coordinate of the enemy some top parts on the screen
    public void setRandomInitialPosition(double width, double height) {
        setCoordX(Math.random() * (ConstantValues.SCREEN_WIDTH - width));
        setCoordY(height * (int)(1 + Math.random() * ConstantValues.SCREEN_HEIGHT / 6 / height));
    }

    // Getter and setter for the displacement amount on the X direction
    public double getDisplacementX() {
        return displacementX;
    }

    public void setDisplacementX(double displacementX) {
        this.displacementX = displacementX;
    }

    // Getter and setter for the score value
    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    // Getter and setter for the direction
    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    // Getter and setter for the health of the enemy
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
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
                if (getCoordX() >= displacementX) setCoordX(getCoordX() - displacementX);
                else dir = Direction.RIGHT;
                break;
            case RIGHT:
                if (getCoordX() < (ConstantValues.SCREEN_WIDTH - width - displacementX))
                    setCoordX(getCoordX() + displacementX);
                else dir = Direction.LEFT;
        }
    }

}