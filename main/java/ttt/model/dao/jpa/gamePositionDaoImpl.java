package ttt.model.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.gamePosition;
import ttt.model.gameRecord;
import ttt.model.gameUser;
import ttt.model.dao.gamePositionDao;

@Repository
public class gamePositionDaoImpl implements gamePositionDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<gamePosition> getSavedGames(gameUser user) {
		return entityManager
				.createQuery(
						"from gamePosition gp where INNER JOIN gameRecord gr ON gp.gameRefId = gr.gameid and gr.player1="
								+ user.getId(), gamePosition.class)
				.getResultList();

	}

	@Override
	@Transactional
	public gamePosition SaveGame(gamePosition positions) {
		// TODO Auto-generated method stub
		
		
		return entityManager.merge(positions);
	}
	
	@Override
	@Transactional
	public void DeleteGame(gamePosition positions) {
		// TODO Auto-generated method stub
		
	/*	int id = record.getGameid();

		return  (gamePosition) entityManager
				.createQuery(
						"DELETE from gamePosition where gamerefid_gameid ="
								+ id, gamePosition.class);*/
		entityManager.remove(positions);
		
		/*entityManager.remove(positions);;
		
		*/
	}

	@Override
	public List<gamePosition> getSavedGameTime(gameRecord game) {
		int id = game.getGameid();

		return entityManager.createQuery(
				"from gamePosition where gamerefid_gameid=" + id,
				gamePosition.class).getResultList();
	}
	
	@Override
    public gamePosition getCheckEntry( Integer record_id )
    {
		
		/*return entityManager.createQuery(
				"from gamePosition where gamerefid_gameid=" + id,
				gamePosition.class).getResultList() == null;
		
		*/
		
		
		return entityManager.find( gamePosition.class, record_id );
    }

}
