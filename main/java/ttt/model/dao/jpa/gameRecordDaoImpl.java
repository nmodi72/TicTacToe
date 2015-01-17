package ttt.model.dao.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.gameRecord;
import ttt.model.gameUser;
import ttt.model.dao.gameRecordDao;

@Repository
public class gameRecordDaoImpl implements gameRecordDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public gameRecord getGamewithTime(gameUser g, Date stamp) {
		// TODO Auto-generated method stub
		System.out.println("Here is that" + g.getId() + stamp);
		return entityManager
				.createQuery(
						"from gameRecord where ( player1_id=" + g.getId()
								+ "or player2_id=" + g.getId()
								+ ") and starttime =:time", gameRecord.class)
				.setParameter("time", stamp).getSingleResult();
	}

	@Override
	@Transactional
	public gameRecord GameStart(gameRecord startgame) {

		/*
		 * System.out.println(startgame.getStartTime() + "Here was That");
		 */return entityManager.merge(startgame);
	}

	@Override
	public List<gameRecord> getAllCompletedGames(gameUser user) {

		int id = user.getId();

		return entityManager.createQuery(
				"from gameRecord where endtime is not null and player1_id ="
						+ id + "or player2_id=" + id, gameRecord.class)
				.getResultList();
	}

	@Override
	public List<gameRecord> getCompletedGames1Player(gameUser user) {

		int id = user.getId();
		/*
		 * return entityManager.createQuery(
		 * "from gameRecord where endtime!=null and  player1_id =" + id,
		 * gameRecord.class).getResultList();
		 */

		return entityManager.createQuery(
				"from gameRecord where endtime is not null and isai= true and  player1_id ="
						+ id, gameRecord.class).getResultList();
	}

	@Override
	public List<gameRecord> getCompletedGames2Player(gameUser user) {

		int id = user.getId();

		/*
		 * return entityManager.createQuery(
		 * "from gameRecord where endtime!=null and  player2_id =" + id,
		 * gameRecord.class).getResultList();
		 */

		return entityManager.createQuery(
				"from gameRecord where endtime is not null and isai= true and  player2_id ="
						+ id, gameRecord.class).getResultList();
	}

	@Override
	public List<gameRecord> getAllAgainstAI(gameUser user) {

		int id = user.getId();

		return entityManager.createQuery(
				"from gameRecord where endtime is not null and isai= true and  player1_id ="
						+ id + "or player2_id=" + id, gameRecord.class)
				.getResultList();
	}

	@Override
	public List<gameRecord> getAllAgainstHUMAN(gameUser user) {

		int id = user.getId();

		return entityManager.createQuery(
				"from gameRecord where endtime is not null and isai= false and  player1_id ="
						+ id + "or player2_id=" + id, gameRecord.class)
				.getResultList();
	}

	@Override
	public List<gameRecord> getWinRateAgainstAI(gameUser user) {

		int id = user.getId();

		return entityManager
				.createQuery(
						"from gameRecord where endtime is not null and isai= true and  winnername_id ="
								+ id + "and  player1_id =" + id
								+ "or player2_id=" + id, gameRecord.class)
				.getResultList();
	}

	@Override
	public List<gameRecord> getWinRateAgainstHuman(gameUser user) {

		int id = user.getId();

		return entityManager
				.createQuery(
						"from gameRecord where endtime is not null and isai= false and  winnername_id ="
								+ id
								+ "and  player1_id ="
								+ id
								+ "or player2_id=" + id, gameRecord.class)
				.getResultList();
	}

	@Override
	public List<gameRecord> getAllData(gameUser user) {
		// TODO Auto-generated method stub
		int id = user.getId();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH) + 1;
		System.out.println(year + " / " + month);

		return entityManager.createQuery(
				"from gameRecord where (month(endtime)=" + month
						+ "and year(endtime)=" + year + ") and  (player1_id ="
						+ id + "or player2_id=" + id + ")", gameRecord.class)
				.getResultList();
	}

	@Override
	@org.springframework.security.access.prepost.PreAuthorize("principal.username == #g.username")
	public List<gameRecord> getAllResumeGames(gameUser g) {
		// TODO Auto-generated method stub

		int id = g.getId();

		return entityManager.createQuery(
				"from gameRecord  where (player1_id =" + id + " or player2_id="
						+ id + ") and endtime is null and savedat is not null",
				gameRecord.class).getResultList();

	}

	@Override
	public gameRecord getClickedGame(Integer id) {
		// TODO Auto-generated method stub

		return entityManager.find(gameRecord.class, id);

	}

}
