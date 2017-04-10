package by.cinema.service.user;

import by.cinema.bean.Ticket;
import by.cinema.bean.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
@Service
public interface UserService {

    User register(User user);
    void remove(User user);
    User getById(String id);
    User getUserByEmail(String email);
    User getUsersByName(String name);
    List<Ticket> getBookedTickets(User user);
    List<Ticket> getBookedTickets(String userId);
}
