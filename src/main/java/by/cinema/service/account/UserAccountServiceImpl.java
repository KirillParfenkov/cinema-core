package by.cinema.service.account;

import by.cinema.bean.User;
import by.cinema.bean.UserAccount;
import by.cinema.dao.account.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 4/25/2017.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountDao userAccountDao;


    @Override
    public UserAccount create(UserAccount userAccount) {
        return userAccountDao.add(userAccount);
    }

    @Override
    public void update(UserAccount userAccount) {
        userAccountDao.update(userAccount);
    }

    @Override
    public UserAccount get(String id) {
        return userAccountDao.get(id);
    }

    @Override
    public List<UserAccount> get() {
        return userAccountDao.get();
    }

    @Override
    public UserAccount getByUser(String userId) {
        return userAccountDao.getByUser(userId);
    }

    @Override
    public void refilling(User user, long amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Amount should not be negative");
        }

        UserAccount userAccount = userAccountDao.getByUser(user.getId());
        long accountAmount = userAccount.getAmount();
        userAccount.setAmount(accountAmount + amount);

        userAccountDao.update(userAccount);
    }


    @Override
    public void withdraw (User user, long amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Amount should not be negative");
        }

        UserAccount userAccount = userAccountDao.getByUser(user.getId());
        long accountAmount = userAccount.getAmount();

        if (accountAmount < amount ) {
            throw new RuntimeException("Not enough money");
        }

        userAccount.setAmount(accountAmount - amount);
        userAccountDao.update(userAccount);
    }
}