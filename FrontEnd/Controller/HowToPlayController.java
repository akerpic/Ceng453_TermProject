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

//HowToPlayController  implements Controller initialization interface
public class HowToPlayController implements Initializable {
    private StringBuffer username= new StringBuffer();

    @Override
//  initialize the controller after it's root element has been completely processed.
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Home Screen is now loaded!");
    }

//  if back button is pressed then load home screen and send username info. to after login screen
    public void HandleBackButton(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader tableViewParent = new FXMLLoader(getClass().getResource(ConstantValues.MAIN_SCREEN));
        Parent root = tableViewParent.load();

        AfterLoginController afterlogin = tableViewParent.getController();
        afterlogin.transferMessage(username.toString());

        Scene viewScene = new Scene(root);
        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    //  This function is used for  username info. transmission. between windows
    void transferMessage(String Username)
    {
//      get information from after login screen
        username.append(Username);
    }
}