package Controller;

import game.ConstantValues;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//LoginController implements Controller initialization interface

public class SignUpController implements Initializable {

//    make connection with fxml file then the following 3 item will be usable.
    @FXML
    TextField password, username;
    @FXML
    Label warning;

    @Override
    //  initialize the controller after it's root element has been completely processed.
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SignUp Screen is now loaded!");
    }

//  produce set of process when Create Account button is clicked
    public void SignUpHandler(javafx.event.ActionEvent actionEvent) {
//      Create a server process object
        ServerProcess process = new ServerProcess();
        try
        {
//          Create an account if username is available
            if(process.create_user(username.getText(), password.getText()))
                System.out.println("The user is created successfully with username("+ username.getText() + ").");
//
//          After creating account load home screen.
            Parent tableViewParent = FXMLLoader.load(getClass().getResource(ConstantValues.HOME_SCREEN));
            Scene viewScene = new Scene(tableViewParent);
            Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(viewScene);
            window.show();
        }
        catch (RuntimeException exception) {
//          if username is not available or , internet connection is down set warning's visibility is true
            warning.setVisible(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

//      if back button is pressed then load home screen with using written function from login controller
    public void backToHomeHandler(javafx.event.ActionEvent actionEvent) throws IOException {
        LoginController back = new LoginController();
        back.backToHomeHandler(actionEvent);
    }
}
