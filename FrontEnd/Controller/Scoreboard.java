package Controller;

import javafx.beans.property.SimpleStringProperty;

public class Scoreboard {
    private final SimpleStringProperty usernames;
    private final SimpleStringProperty scores;

    public Scoreboard(String usernames, String scores) {
        this.usernames = new SimpleStringProperty(usernames);
        this.scores = new SimpleStringProperty(scores);
    }

    public String getUsernames() {
        return usernames.get();
    }

    public SimpleStringProperty usernamesProperty() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames.set(usernames);
    }

    public String getScores() {
        return scores.get();
    }

    public SimpleStringProperty scoresProperty() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores.set(scores);
    }
}
