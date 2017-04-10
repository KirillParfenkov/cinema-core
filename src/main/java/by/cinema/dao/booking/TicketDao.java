package by.cinema.dao.booking;

import by.cinema.bean.Event;
import by.cinema.bean.Ticket;
import by.cinema.bean.User;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
public interface TicketDao {
    List<Ticket> getList();
    List<Ticket> getList(User user);
    List<Ticket> getList(Event event, Date date);
    Ticket add(Ticket ticket);
    List<Ticket> getTicketsByEvent(String eventId);
}
