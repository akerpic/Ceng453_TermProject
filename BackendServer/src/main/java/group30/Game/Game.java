package group30.Game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import group30.Player.Player;


@Entity
@Table(name = "Games")
public class Game {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer gid;

	@ManyToOne @JoinColumn(name="pid")
	private Player player;


	private Integer score;

	@GeneratedValue(strategy=GenerationType.AUTO)
	private String gametime;

	// Constructors

	public Game() {

	}

	public Game(Integer gid, Integer pid, Integer score, String time) {
		super();
		this.gid = gid;
		this.player = new Player(pid, "", "");
		this.score = score;
		this.gametime = time;
	}

	// Getters and Setters

	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getTime() {
		return gametime;
	}
	public void setTime(String time) {
		this.gametime = time;
	}

}