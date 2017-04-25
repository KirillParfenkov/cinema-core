package by.cinema.service.user;

import by.cinema.bean.Ticket;
import by.cinema.bean.User;
import by.cinema.bean.UserAccount;
import by.cinema.dao.booking.TicketDao;
import by.cinema.dao.user.UserDao;
import by.cinema.service.account.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountService userAccountService;


    public static final String DEFAULT_ROLE = "ROLE_REGISTERED_USER";


    public User register(User user) {

        user.setRoles(DEFAULT_ROLE);
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        User addUser = userDao.add(user);

        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        userAccount.setAmount(100);
        userAccountService.create(userAccount);
        return addUser;
    }

    public void remove(User user) {
        userDao.remove(user.getId());
    }

    public User getById(String id) {
        return userDao.get(id);
    }

    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User getUsersByName(String name) {
        return userDao.getByName(name);
    }

    public List<Ticket> getBookedTickets(User user) {

        return ticketDao.getList(user);
    }

    public List<Ticket> getBookedTickets(String userId) {
        return ticketDao.getList(userDao.get(userId));
    }
}
