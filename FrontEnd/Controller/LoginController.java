package Controller;

import game.ConstantValues;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//LoginController implements Controller initialization interface
public class LoginController implements Initializable {
//    make connection with fxml file then the following 3 item will be usable.
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML Label warning;

    @Override
    //  initialize the controller after it's root element has been completely processed.
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Login Screen is now loaded!");
    }

//  produce set of process when Login button is clicked
    public void LoginHandler(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("Login handler is working!");
//      Create a server process object
        ServerProcess process = new ServerProcess();
//      check username and password is matched or not
        if(process.isLogin(username.getText(), password.getText()))
        {
//          if username and password is matched then load after login screen
            FXMLLoader tableViewParent = new FXMLLoader(getClass().getResource(ConstantValues.MAIN_SCREEN));
            Parent root = tableViewParent.load();

//          Create a AfterLoginController objects in order to transfer username information
            AfterLoginController afterlogin = tableViewParent.getController();
//          transfer username information to next window
            afterlogin.transferMessage(username.getText());

//          viewScene is the container for all content in the scene graph.
            Scene viewScene = new Scene(root);
//          Get stage which is initially created.
            Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//          Set scene of window
            window.setScene(viewScene);
//          show the Window by setting it's visibility to true
            window.show();

        }
        else
        {
//          if username and password is not matched then set warning message visible
            warning.setVisible(true);
        }
    }

    public void backToHomeHandler(javafx.event.ActionEvent actionEvent) throws IOException {
//      if back button is pressed then load home screen
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(ConstantValues.HOME_SCREEN));
        Scene viewScene = new Scene(tableViewParent);
//      Get stage which is initially created.
        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//      Set scene of window
        window.setScene(viewScene);
//      show the Window by setting it's visibility to true
        window.show();
    }

}