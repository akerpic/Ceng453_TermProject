package Controller;

import game.ConstantValues;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//HomeController  implements Controller initialization interface
public class HomeController implements Initializable {

    @Override
//  initialize the controller after it's root element has been completely processed.
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Home Screen is now loaded!");
    }

//  produce set of process when Login button is clicked
    public void handleLoginButton(javafx.event.ActionEvent actionEvent) throws IOException {

//      Parent loads login_screen.fxml which includes Layout codes of login screen when login button is clicked.
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(ConstantValues.LOGIN_SCREEN));
//      viewScene is the container for all content in the scene graph.
        Scene viewScene = new Scene(tableViewParent);
//      Get stage which is initially created.
        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//      Set scene of window
        window.setScene(viewScene);
//      show the Window by setting it's visibility to true
        window.show();
    }

//  produce set of process when Create Account button is clicked
    public void handleCreateAccountButton(javafx.event.ActionEvent actionEvent) throws IOException {
//      Parent loads signup_screen.fxml which includes Layout codes of sign-up screen when Create Account button is clicked.
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(ConstantValues.SIGNUP_SCREEN));
//      viewScene is the container for all content in the scene graph.
        Scene viewScene = new Scene(tableViewParent);
//      Get stage which is initially created.
        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//      Set scene of window
        window.setScene(viewScene);
//      show the Window by setting it's visibility to true
        window.show();
    }
}