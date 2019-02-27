import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        System.out.println(ServerProcess.get_playerID("emrecan"));

        Parent root = FXMLLoader.load(getClass().getResource("screens/home_screen.fxml"));

        primaryStage.setTitle("Welcome to Galaxy Shooter");
        primaryStage.setScene(new Scene(root, 640, 640));
        primaryStage.show();


    }




}