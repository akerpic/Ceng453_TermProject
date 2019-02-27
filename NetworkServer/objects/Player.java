package objects;
import main.ConstantValues;
import main.GameEngine;

public class Player extends Object {

    private boolean movingLeft = false;     // check for move in the left direction
    private boolean movingRight = false;    // check for move in the right direction
    private boolean movingUp = false;       // check for move in the up direction
    private boolean movingDown = false;     // check for move in the down direction

    private int score;
    /** Constructors
    // default constructor, it takes an image and some default values and calls its super class Object */
    public Player(int initialWidth) {
        super(ConstantValues.DEFAULT_WIDTH,ConstantValues.DEFAULT_HEIGHT);
        setInitialPosition(initialWidth);
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getScore()
    {
        return this.score;
    }

    /** Set initial position of the player : bottom-center */
    private void setInitialPosition(int playerCoordX) {
        setCoordX((ConstantValues.SCREEN_WIDTH - ConstantValues.PLAYER_WIDTH)*playerCoordX / 4);
        setCoordY(ConstantValues.SCREEN_HEIGHT - ConstantValues.PLAYER_HEIGHT *2);
    }

    /** sets the direction of the movement of the player when arrow keys pressed */
    void startMovement(Direction dir) {
        switch (dir) {
            case UP:
                movingUp = true; break;
            case DOWN:
                movingDown = true; break;
            case LEFT:
                movingLeft = true; break;
            case RIGHT:
                movingRight = true; break;
        }
    }

    /** sets the direction of the movement of the player when arrow keys released */
    void stopMovement(Direction dir) {
        switch (dir) {
            case UP:
                movingUp = false; break;
            case DOWN:
                movingDown = false; break;
            case LEFT:
                movingLeft = false; break;
            case RIGHT:
                movingRight = false; break;
        }
    }

    /** it creates a bullet and adds it to the gameengine with displacement amount of the bullet
     * it also creates bullet at the front center of the player */
    void fireBullet(GameEngine engine, int playerNo) {
        engine.queueAddition(new Bullet(getCenterX() - (ConstantValues.BULLET_WIDTH / 2), getCenterY() - ConstantValues.BULLET_HEIGHT, ConstantValues.PLAYER_BULLET_DISPLACEMENTY, playerNo));
    }

    /** move function for the player
     * overriden from the object class
     * it can go left, right, up, down and also up-left, up-right, down-left, down-right
     * it also check for the border of the game area in the X-Y direction so player can not go to outside of the game area */
    @Override
    public void move() {
        if (movingLeft ^ movingRight) {
            // displacement amount in the X direction through given timeline in the game engine
            double displacementX = ConstantValues.PLAYER_DISPLACEMENTX;
            if (movingLeft) {
                if (getCoordX() >= displacementX) setCoordX(getCoordX() - displacementX);
                else setCoordX(0);
            }
            if (movingRight) {
                if (getCoordX() < (ConstantValues.SCREEN_WIDTH - width - displacementX))
                    setCoordX(getCoordX() + displacementX);
                else setCoordX(ConstantValues.SCREEN_WIDTH - width - displacementX);
            }
        }
        if (movingUp ^ movingDown) {
            // displacement amount in the Y direction through given timeline in the game engine
            double displacementY = ConstantValues.PLAYER_DISPLACEMENTY;
            if (movingUp) {
                if (getCoordY() >= (displacementY + height)) setCoordY(getCoordY() - displacementY);
                else setCoordY(height);
            }
            if (movingDown) {
                if (getCoordY() < (ConstantValues.SCREEN_HEIGHT - height - displacementY))
                    setCoordY(getCoordY() + displacementY);
                else setCoordY(ConstantValues.SCREEN_HEIGHT - height - displacementY);
            }
        }
    }

}
