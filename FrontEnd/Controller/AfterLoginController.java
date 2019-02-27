package Controller;

import game.ConstantValues;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//AfterLoginController implements Controller initialization interface
public class AfterLoginController implements Initializable {

    public Label warning;
    private StringBuffer username= new StringBuffer();

    @Override

//  initialize the controller after it's root element has been completely processed.
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("After Login Screen is now loaded!");
    }

//  When Play button is pressed , following function will be called.
//  And the game will be started and username info. will send to the game engine object
    public void handlePlayButton(javafx.event.ActionEvent actionEvent) {
        GameEngine engine = new GameEngine(username.toString());

        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(engine.getScene());
        window.show();
    }

    //  When How to Play button is pressed , following function will be called.
    //  And the How to play screen will be presented.
    public void handleHowToPlayButton(javafx.event.ActionEvent actionEvent) throws IOException {

        FXMLLoader tableViewParent = new FXMLLoader(getClass().getResource(ConstantValues.HOW_TO_PLAY_SCREEN));
        Parent root = tableViewParent.load();

        HowToPlayController theController = tableViewParent.getController();
        theController.transferMessage(username.toString());

        Scene viewScene = new Scene(root);
        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();

    }

    //  When Scoreboard button is pressed , following function will be called.
    //  And the Scoreboard screen will be loaded.
    public void handleScoreboard(javafx.event.ActionEvent actionEvent) throws IOException {

        FXMLLoader tableViewParent = new FXMLLoader(getClass().getResource(ConstantValues.SCOREBOARD_SCREEN));
        Parent root = tableViewParent.load();

        ScoreboardController theController = tableViewParent.getController();
        theController.transferMessage(username.toString());

        Scene viewScene = new Scene(root);
        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();

    }
    //  When Exit button is pressed , following function will be called and the program will be closed properly.
    public void handleExit() {
        Platform.exit();
    }

    //  This function is used for  username info. transmission. between windows
    void transferMessage(String Username)
    {
        username.append(Username);
    }
}
