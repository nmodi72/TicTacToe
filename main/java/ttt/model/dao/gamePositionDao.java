package ttt.model.dao;

import java.util.Date;
import java.util.List;

import ttt.model.gamePosition;
import ttt.model.gameRecord;
import ttt.model.gameUser;

public interface gamePositionDao {

	gamePosition SaveGame(gamePosition positions);

	List<gamePosition> getSavedGames(gameUser user);

	List<gamePosition> getSavedGameTime(gameRecord game);

	gamePosition getCheckEntry(Integer recordid);

	void DeleteGame(gamePosition positions);

}
