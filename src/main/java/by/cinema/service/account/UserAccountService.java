package by.cinema.service.account;

import by.cinema.bean.User;
import by.cinema.bean.UserAccount;

import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 4/25/2017.
 */
public interface UserAccountService {

    UserAccount create(UserAccount userAccount);
    void update(UserAccount userAccount);
    UserAccount get(String id);
    List<UserAccount> get();
    UserAccount getByUser(String userId);
    void refilling(User user, long amount);
    void withdraw(User user, long amount);
}
