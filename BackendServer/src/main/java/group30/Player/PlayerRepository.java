package group30.Player;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

	// This function takes username as an input and tries to find an entry in the database with given username
	public Player findByUsername(String username);

}