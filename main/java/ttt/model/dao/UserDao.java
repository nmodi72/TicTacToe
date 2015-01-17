package ttt.model.dao;

import java.util.List;

import ttt.model.User;

public interface UserDao {

   /* User getUser( Integer id );

    List<User> getUsers();*/
    User getUser( String username );
}