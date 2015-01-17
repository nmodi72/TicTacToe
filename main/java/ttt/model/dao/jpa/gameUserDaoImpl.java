package ttt.model.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.gameUser;
import ttt.model.dao.gameUserDao;

@Repository
public class gameUserDaoImpl implements gameUserDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public gameUser getUser(String username) {
		System.out.println("username: " + username);
		return (gameUser) entityManager
				.createQuery("from gameUser where username=:user_name")
				.setParameter("user_name", username).getSingleResult();

		// return entityManager.createQuery(
		// "from gameUser where username='"+username+"'", gameUser.class
		// ).getSingleResult();

	}



	@Override
	public List<gameUser> getAllUser() {
		// TODO Auto-generated method stub

		return entityManager.createQuery("from gameUser", gameUser.class)
				.getResultList();
	}

	@Override
	@Transactional
	public gameUser saveUser(gameUser userdetail) {
		return entityManager.merge(userdetail);
	}

	@Override
	public gameUser getUserById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public gameUser getPasswordById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkUser(String username, String password) {
		// TODO Auto-generated method stub
		try {
			gameUser gameusers = entityManager
					.createQuery(
							"from gameUser where username = :u and password = :p",
							gameUser.class).setParameter("u", username)
					.setParameter("p", password).getSingleResult();
			if (gameusers.getUsername() != null) {
				return true;
			} else {

				return false;
			}
		} catch (NoResultException noresult) {
			return false;
		}

	}

}
