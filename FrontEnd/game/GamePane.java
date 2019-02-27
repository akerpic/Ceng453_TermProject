package game;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import Controller.GameEngine;

public class GamePane extends Pane{

    private GameEngine engine;      // engine which will be showed in the screen

    private Label levelLabel;       // to show level in the screen and count level id
    private Label scoreLabel;       // to show total score in the screen in real time
    private Label userNameLabel;    // to show userName on top-left of the screen

    // Constructor: set background color and create 3 new label to show on the top of the screen
    public GamePane() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(ConstantValues.BACKGROUND_VALUE, ConstantValues.BACKGROUND_VALUE, ConstantValues.BACKGROUND_VALUE), null, null)));     // set Background color

        levelLabel = new Label();                               // create new label for level
        levelLabel.setMinWidth(ConstantValues.SCREEN_WIDTH);    // set min width to label
        levelLabel.setTextFill(Color.WHITE);                    // set color to label
        levelLabel.setAlignment(Pos.TOP_CENTER);                // arrange the position of the label
        levelLabel.setTextAlignment(TextAlignment.CENTER);      // arrange the position of the label text
        levelLabel.setTranslateY(ConstantValues.POSITION_Y);                            // arrange the position of the label in Y axis


        scoreLabel = new Label();                               // create new label for score
        scoreLabel.setMinWidth(ConstantValues.SCREEN_WIDTH);    // set min width to label
        scoreLabel.setTextFill(Color.WHITE);                    // set color to label
        scoreLabel.setAlignment(Pos.TOP_RIGHT);                 // arrange the position of the label
        scoreLabel.setTextAlignment(TextAlignment.RIGHT);       // arrange the position of the label text
        scoreLabel.setTranslateY(ConstantValues.POSITION_Y);                            // arrange the position of the label in Y axis


        userNameLabel = new Label();                            // create new label for userName
        userNameLabel.setTextFill(Color.WHITE);                 // set color to label
        userNameLabel.setTranslateY(ConstantValues.POSITION_Y);                         // arrange the position of the label in Y axis

        this.getChildren().addAll(userNameLabel, levelLabel, scoreLabel);   // add labels to screen
    }

    // bind level to int value to show in the screen
    public void bindLevel(ReadOnlyIntegerProperty level) {
        levelLabel.textProperty().bind(new SimpleStringProperty("LEVEL ").concat(level.asString()));
    }

    // bind level to int value to show in the screen
    public void bindScore(ReadOnlyIntegerProperty score) {
        scoreLabel.textProperty().bind(score.asString());
    }

    // bind level to int string to show in the screen
    public void bindUserName(ReadOnlyStringProperty userName) {
        userNameLabel.textProperty().bind(userName);
    }

    // Getter and setter for GameEngine
    public GameEngine getEngine() {
        return engine;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }
}
