package objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// abstract super class for creating Game objects such as Bullet, Enemy, Player
// it extends imageview class for use image for the objects
public abstract class Object extends ImageView{

    double width;      // objects width
    double height;    // objects height

    // constructor for when the imageURL, width and heigth are given
    // it uses imageURL to create new image and calls to constructor of the imageview class with given width and height
    Object(String imageURL, double width, double height) {
        this.width = width;
        this.height = height;
        // objects image
        Image image = new Image(imageURL, width, height, false, false);
        super.setImage(image);
        setFitHeight(height);
        setFitWidth(width);
    }

    // Getter method for width
    public double getWidth() {
        return width;
    }

    // Getter method for height
    public double getHeight() {
        return height;
    }

    // Getter method for getting X coordinate of the object
    double getCenterX() {
        return getX() + (width / 2);
    }

    // abstract method move, it overwrited in subclasses
    public abstract void move();
}
