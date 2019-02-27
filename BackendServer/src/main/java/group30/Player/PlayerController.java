package group30.Player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

	@Autowired
	private PlayerRepository playerRepository;

	// Login function
	@RequestMapping("/login/{username}/{password}")
	public String log_in(@PathVariable String username, @PathVariable String password)
	{
		Player player = playerRepository.findByUsername(username);
		if (player == null) {
			return "ERROR";
		}
		if(player.getPassword().equals(password))
		{
			return "SUCCESSFUL";
		}
		return "ERROR";

	}

	@RequestMapping("/get_playerID/{username}")
	public String get_playerID(@PathVariable String username)
	{
		Player player = playerRepository.findByUsername(username);
		if (player == null) {
			return "ERROR";
		}
		return player.getPid().toString();
	}


	// Get Player with using their user name
	@RequestMapping("/player_name/{username}")
	public Player getPlayerWithUsername(@PathVariable String username)
	{
		return playerRepository.findByUsername(username);
	}
	// Get all Players
	@RequestMapping("/players")
	public List<Player> getAllPlayers()
	{
		List<Player> players = new ArrayList<>();
		playerRepository.findAll()
				.forEach(players::add);

		return players;
	}

	// Get Player with using pid
		@RequestMapping("/players/{pid}")
	public Player getPlayer(@PathVariable Integer pid)
	{
		return playerRepository.findById(pid).get();
	}

	// add Player with using player object
	@PostMapping("/players")
	public void addPlayer(@RequestBody Player player)
	{
		playerRepository.save(player);
	}

	// update Player with using player object and player id from URL
	@PutMapping("/players/{pid}")
	public void updatePlayer(@RequestBody Player player, @PathVariable Integer pid)
	{
		playerRepository.save(player);
	}

	// delete Player with using player id from URL
	@DeleteMapping("/players/{pid}")
	public void deletePlayer(@PathVariable Integer pid)
	{
		playerRepository.deleteById(pid);
	}

}