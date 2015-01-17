package ttt.model.dao;

import java.util.List;

import ttt.model.gameUser;

public interface gameUserDao {

	List<gameUser> getAllUser();

	gameUser getUser(String username);

	gameUser saveUser(gameUser userdetail);

	gameUser getUserById(Integer id);

	gameUser getPasswordById(Integer id);

	boolean checkUser(String username, String password);

}
