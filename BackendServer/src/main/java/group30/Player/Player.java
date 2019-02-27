package group30.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Players")
public class Player {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pid;
	private String username;
	private String password;

	public Player() { // Constructor

	}

	public Player(String name, String password) { // Constructor without pid
		super();
		this.username = name;
		this.password = password;
	}

	public Player(Integer pid, String name, String password) { // Constructor
		super();
		this.pid = pid;
		this.username = name;
		this.password = password;
	}
	// Getters and Setters

	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}