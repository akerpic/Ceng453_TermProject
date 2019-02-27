package group30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import group30.Game.GameController;
import group30.Player.Player;
import group30.Player.PlayerController;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerTests {

	@Autowired
	PlayerController test_player;
	
	@Autowired
	GameController test_game;
	
	@Test
	public void contextLoads() {
	}
	
	
	/** Testing create account with available user name */
	
	@Test
	public void getIdTest() {
		Player player = new Player("idTest", "idTest");
		test_player.addPlayer(player);
		Player np = test_player.getPlayerWithUsername("idTest");
		assertEquals(player.getPid(), np.getPid());
		test_player.deletePlayer(np.getPid());
	}
	
	/** Testing create account with unavailable user name */
	@Test
	public void testWithTryAndCatchNullPointerException() {
		Player player = new Player("testUnavailable", "testUnavailable");
    	
		Player player_existing = new Player("testUnavailable", "testUnavailable");
	    test_player.addPlayer(player);
		try {
	    	test_player.addPlayer(player_existing);
	        fail();
	    } catch (DataIntegrityViolationException ex) {
	        assertTrue(ex instanceof DataIntegrityViolationException);
	    }
		finally {
			test_player.deletePlayer(player.getPid());
		}
	}
	
	/** Testing login with correct password and user name*/
	@Test
	public void loginTest() {
		Player player = new Player("loginTest", "loginTest");
		test_player.addPlayer(player);
		assertEquals("SUCCESSFUL", test_player.log_in("loginTest","loginTest"));
		test_player.deletePlayer(player.getPid());
	}
	
	/** Testing login with incorrect password */
	@Test
	public void wrongPasswordLoginTest() {
		Player player = new Player("loginTest", "loginTest");
		test_player.addPlayer(player);
		assertEquals("ERROR", test_player.log_in("loginTest","loginTest2"));
		test_player.deletePlayer(player.getPid());
	}
	
	/** Testing login with incorrect user name */
	@Test
	public void wrongUsernameLoginTest() {
		Player player = new Player("loginTest", "loginTest");
		test_player.addPlayer(player);
		assertEquals("ERROR", test_player.log_in("loginTest2","loginTest"));
		test_player.deletePlayer(player.getPid());
	}
	
	/** Testing login with incorrect user name */
	@Test
	public void wrongPassUserloginTest() {
		Player player = new Player("loginTest", "loginTest");
		test_player.addPlayer(player);
		assertEquals("ERROR", test_player.log_in("loginTest2","loginTest2"));
		test_player.deletePlayer(player.getPid());
	}
	
	
	

}