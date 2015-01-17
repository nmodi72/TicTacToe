package ttt.model.dao;

import java.util.Date;
import java.util.List;

import ttt.model.gameRecord;
import ttt.model.gameUser;

public interface gameRecordDao {

	gameRecord GameStart(gameRecord startgame);

	gameRecord getGamewithTime(gameUser g, Date stamp);

	List<gameRecord> getAllResumeGames(gameUser g);

	List<gameRecord> getAllCompletedGames(gameUser user);

	List<gameRecord> getCompletedGames1Player(gameUser user);

	List<gameRecord> getCompletedGames2Player(gameUser user);

	List<gameRecord> getAllAgainstAI(gameUser user);

	List<gameRecord> getWinRateAgainstAI(gameUser user);

	List<gameRecord> getAllAgainstHUMAN(gameUser user);

	List<gameRecord> getWinRateAgainstHuman(gameUser user);

	List<gameRecord> getAllData(gameUser user);

	gameRecord getClickedGame(Integer id);
}
