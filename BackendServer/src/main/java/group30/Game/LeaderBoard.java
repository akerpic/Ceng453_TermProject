package group30.Game;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LeaderBoard {

    @Id
    String username;
    Integer score;


    // Constructors

    public LeaderBoard() {

    }

    public LeaderBoard(String username, Integer score) {
        super();
        this.username = username;
        this.score = score;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }



}