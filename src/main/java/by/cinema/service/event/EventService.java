package by.cinema.service.event;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;
import by.cinema.bean.Rating;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public interface EventService {

    Event create(String name, long price, Rating rating);
    Event create(Event event);
    void remove(Event event);
    void remove(String eventId);
    Event getByName(String name);
    List<Event> getAll();
    List<Event> getForDateRange(Date from, Date to);
    List<Event> getNextEvents(Date to);
    void assignAuditorium(Event event, Auditorium auditorium, Date date);
    List<AuditoriumBooking> getAuditoriumBookings(Event event);
    AuditoriumBooking getAuditoriumBookingById(String id);
}
