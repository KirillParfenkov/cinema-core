package by.cinema.dao.account;

import by.cinema.bean.UserAccount;

import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 4/25/2017.
 */
public interface UserAccountDao {

    List<UserAccount> get();
    UserAccount get(String id);
    UserAccount getByUser(String userId);
    UserAccount add(UserAccount userAccount);
    void update(UserAccount userAccount);
    void remove(String id);
}
