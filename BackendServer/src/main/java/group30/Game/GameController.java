package group30.Game;

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

import group30.Player.Player;

@RestController
public class GameController {

	@Autowired
	private GameRepository gameRepository;


	// get all Game record according to player id from URL
	@RequestMapping("/players/{pid}/games")
	public List<Game> getAllGames(@PathVariable Integer pid)
	{
		List<Game> games = new ArrayList<>();
		gameRepository.findByPlayerPid(pid)
				.forEach(games::add);

		return games;
	}

	// get Game record according to player id from URL
	@RequestMapping("/players/{pid}/games/{gid}")
	public Game getGame(@PathVariable Integer gid)
	{
		return gameRepository.findById(gid).get();
	}

	// add Game record according to player id from URL
	@PostMapping("/players/{pid}/games")
	public void addGame(@RequestBody Game game, @PathVariable Integer pid)
	{
		game.setPlayer(new Player(pid, "", ""));
		gameRepository.save(game);
	}

	// update Game record according to player id and game id from URL
	@PutMapping("/players/{pid}/games/{gid}")
	public void updateGame(@RequestBody Game game, @PathVariable String gid, @PathVariable Integer pid)
	{
		game.setPlayer(new Player(pid, "", ""));
		gameRepository.save(game);
	}

	// delete Game record according to player id and game id from URL
	@DeleteMapping("/players/{pid}/games/{gid}")
	public void deleteGame(@PathVariable Integer gid)
	{
		gameRepository.deleteById(gid);
	}

}