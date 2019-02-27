package group30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import group30.Game.Game;
import group30.Game.GameController;
import group30.Player.Player;
import group30.Player.PlayerController;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTests {

	@Autowired
	PlayerController test_player;
	
	@Autowired
	GameController test_game;
	
	@Test
	public void contextLoads() {
	}
	
	// test the back end server can match player and game
	@Test
	public void getGameIdTest() {
		Player player = new Player("gameIdTest", "gameIdTest");
		test_player.addPlayer(player);
		Game game = new Game();
		game.setScore(100);		
		game.setPlayer(player);
		test_game.addGame(game, player.getPid());		
		assertEquals(game.getGid(), test_game.getAllGames(player.getPid()).get(0).getGid());
		test_player.deletePlayer(player.getPid());
	}
	
	// test the score of the game is correct
	@Test
	public void getGameScoreTest() {
		Player player = new Player("gameIdTest", "gameIdTest");
		test_player.addPlayer(player);
		Game game = new Game();
		game.setScore(12345);		
		game.setPlayer(player);
		test_game.addGame(game, player.getPid());		
		assertEquals(game.getScore(), test_game.getAllGames(player.getPid()).get(0).getScore());
		test_player.deletePlayer(player.getPid());
	}
	
	// test the time of the game is correct
		@Test
		public void getGameTimeTest() {
			Player player = new Player("gameIdTest", "gameIdTest");
			test_player.addPlayer(player);
			Game game = new Game();
			game.setScore(12345);		
			game.setPlayer(player);
			game.setTime("2019-01-17 05:18:32");
			test_game.addGame(game, player.getPid());		
			assertEquals(game.getTime(), test_game.getAllGames(player.getPid()).get(0).getTime());
			test_player.deletePlayer(player.getPid());
		}

	

}