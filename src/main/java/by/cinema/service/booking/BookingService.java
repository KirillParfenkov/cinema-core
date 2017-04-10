package by.cinema.service.booking;

import by.cinema.bean.Event;
import by.cinema.bean.Ticket;
import by.cinema.bean.User;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
public interface BookingService {

    Ticket create(Event event, Date date, Set<Integer> seats, User user);
    long getTicketPrice(Event event, Date date, Set<Integer> seats, User user);
    boolean bookTicket(Ticket ticket);
    List<Ticket> getTicketsForEvent(Event event, Date date);
}
