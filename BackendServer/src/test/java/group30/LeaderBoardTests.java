package group30;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import group30.Game.GameController;
import group30.Game.LeaderBoard;
import group30.Game.LeaderBoardController;
import group30.Player.Player;
import group30.Player.PlayerController;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LeaderBoardTests {

	@Autowired
	PlayerController test_player;
	
	@Autowired
	GameController test_game;
	
	@Autowired
	LeaderBoardController test_leader_board;
	
	@Test
	public void contextLoads() {
	}
	
	// Test leader board all weeks
	@Test
	public void getGameIdTest() {
		List<LeaderBoard> leaderboard = test_leader_board.getLeaderBoardAll();
		Integer sumScore = leaderboard.get(0).getScore();
		String userName = leaderboard.get(0).getUsername();
		Player player = test_player.getPlayerWithUsername(userName);
		Integer testSum=0;
		for(int i=0;i<test_game.getAllGames(player.getPid()).size();i++)
			testSum += test_game.getAllGames(player.getPid()).get(i).getScore();
		
		assertEquals(sumScore,testSum);
	}

}