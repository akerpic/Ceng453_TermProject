package group30.Game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LeaderBoardRepository extends CrudRepository<LeaderBoard, String> {

    //This function gets player's user name and their total scores as a leader board entry for all time leader board

    @Query(value = "select p.username , SUM(g.score) as score from Games g, Players p where p.pid = g.pid group by (g.pid) order by SUM(g.score) DESC", nativeQuery = true)
    public List<LeaderBoard> getAllLeaderBoard();

    //This function gets player's user name and their total scores as a leader board entry for weekly leader board

    @Query(value = "select p.username , SUM(g.score) as score from Games g,Players p where p.pid = g.pid and g.gametime >= date_sub(now(),interval 1 week) group by g.pid order by SUM(g.score) DESC", nativeQuery = true)
    public List<LeaderBoard> getWeeklyLeaderBoard();
}