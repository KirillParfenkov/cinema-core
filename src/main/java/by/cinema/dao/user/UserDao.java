package by.cinema.dao.user;

import by.cinema.bean.User;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public interface UserDao {

    User add(User user);
    User get(String id);
    void remove(String id);
    User getByEmail(String email);
    User getByName(String name);

}
