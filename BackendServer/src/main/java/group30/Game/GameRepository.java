package group30.Game;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {

	// This function takes player id as an input and tries to find an entry in the database with given player id

	public List<Game> findByPlayerPid(Integer pid);

}