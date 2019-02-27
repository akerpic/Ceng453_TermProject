package group30.Game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LeaderBoardController {

    @Autowired
    private LeaderBoardRepository leaderBoardRepository;

    //This function gets player's user name and their total scores as a leader board entry for all time leader board

    @RequestMapping("/leaderboard_all")
    public List<LeaderBoard> getLeaderBoardAll()
    {
        return leaderBoardRepository.getAllLeaderBoard();
    }

    //This function gets player's user name and their total scores as a leader board entry for weekly leader board

    @RequestMapping("/leaderboard_weekly")
    public List<LeaderBoard> getLeaderBoardWeekly()
    {
        return leaderBoardRepository.getWeeklyLeaderBoard();
    }

}