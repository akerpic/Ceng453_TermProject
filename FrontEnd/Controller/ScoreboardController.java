package Controller;

import game.ConstantValues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//ScoreboardController implements Controller initialization interface
public class ScoreboardController implements Initializable {

    @FXML TableView<Scoreboard> scoreboard;
    @FXML TableView<Scoreboard> weekly;

    private final ObservableList<Scoreboard> all_list = FXCollections.observableArrayList();
    private final ObservableList<Scoreboard> weekly_list = FXCollections.observableArrayList();
    private StringBuffer get_username = new StringBuffer();


    @Override
//  initialize the controller after it's root element has been completely processed.
    public void initialize(URL location, ResourceBundle resources) {

//      Create columns and set their min width
        TableColumn allNamesColumn = new TableColumn("Name");
        allNamesColumn.setCellValueFactory(new PropertyValueFactory<>("usernames"));
        allNamesColumn .setMinWidth(310);
        TableColumn allScoresColumn = new TableColumn("Score");
        allScoresColumn.setCellValueFactory(new PropertyValueFactory<>("scores"));
        allScoresColumn .setMinWidth(310);

        TableColumn weeklyNamesColumn = new TableColumn("Name");
        weeklyNamesColumn.setCellValueFactory(new PropertyValueFactory<>("usernames"));
        weeklyNamesColumn .setMinWidth(310);
        TableColumn weeklyScoresColumn = new TableColumn("Score");
        weeklyScoresColumn.setCellValueFactory(new PropertyValueFactory<>("scores"));
        weeklyScoresColumn .setMinWidth(310);

//      add items cto scoreboard table
        scoreboard.setItems(all_list);
        scoreboard.getColumns().addAll(allNamesColumn, allScoresColumn);

//      add items and columns to weekly scoreboard table
        weekly.setItems(weekly_list);
        weekly.getColumns().addAll(weeklyNamesColumn, weeklyScoresColumn);

//      get database query result with using web server
        String all_results = ServerProcess.request("GET","","leaderboard_all");
        String weekly_results = ServerProcess.request("GET","","leaderboard_weekly");

        ArrayList<String> all_usernames = new ArrayList<>();
        ArrayList<String> all_scores = new ArrayList<>();

        ArrayList<String> weekly_usernames = new ArrayList<>();
        ArrayList<String> weekly_scores = new ArrayList<>();

        int name_start = 0;
        int score_start = 0;
//       Parse json object that is obtained from database via web server
        for(int i = 0; i<all_results.length(); i++)
        {
            if(all_results.charAt(i) == ':' && all_results.charAt(i+1) == '"')
                name_start = i + 2;

            if(all_results.charAt(i) == ':' && all_results.charAt(i+1) >= '0' && all_results.charAt(i+1) <= '9')
                score_start = i + 1;

            if(all_results.charAt(i) == '}' && score_start != 0 && score_start < i)
            {
                all_scores.add(all_results.substring(score_start,i));
                score_start = 0;
            }

            if(all_results.charAt(i) == '"' && name_start!=0 && name_start < i)
            {
                all_usernames.add(all_results.substring(name_start, i));
                name_start=0;
            }
        }

//      add rows to scoreboard
        for(int i=0;i<all_usernames.size() && i < all_scores.size();i++)
            all_list.add(new Scoreboard(all_usernames.get(i),all_scores.get(i)));

        name_start = 0;
        score_start = 0;
        for(int i = 0; i<weekly_results.length(); i++)
        {
            if(weekly_results.charAt(i) == ':' && weekly_results.charAt(i+1) == '"')
                name_start = i + 2;

            if(weekly_results.charAt(i) == ':' && weekly_results.charAt(i+1) >= '0' && weekly_results.charAt(i+1) <= '9')
                score_start = i + 1;

            if(weekly_results.charAt(i) == '}' && score_start != 0 && score_start < i)
            {
                weekly_scores.add(weekly_results.substring(score_start,i));
                score_start = 0;
            }

            if(weekly_results.charAt(i) == '"' && name_start!=0 && name_start < i)
            {
                weekly_usernames.add(weekly_results.substring(name_start, i));
                name_start=0;
            }
        }

//      add rows to weekly scoreboard
        for(int i=0;i<weekly_usernames.size() && i < weekly_scores.size();i++)
            weekly_list.add(new Scoreboard(weekly_usernames.get(i),weekly_scores.get(i)));

        System.out.println("ScoreBoard Screen is now loaded!");

    }

    //  when back button is pressed , load after login screen and send username information to after login window.
    public void HandleBackButton(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader tableViewParent = new FXMLLoader(getClass().getResource(ConstantValues.MAIN_SCREEN));
        Parent root = tableViewParent.load();

        AfterLoginController afterlogin = tableViewParent.getController();
        afterlogin.transferMessage(get_username.toString());

        Scene viewScene = new Scene(root);
        Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    //  This function is used for  username info. transmission. between windows
    void transferMessage(String Username)
    {
        get_username.append(Username);
    }
}