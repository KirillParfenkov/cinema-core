package by.cinema.dao.event;

import by.cinema.bean.Auditorium;
import by.cinema.bean.Event;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
public interface EventDao {
    Event add(Event event);
    Event get(String id);
    void remove(String id);
    List<Event> getList();
    List<Event> getList(Date from, Date to);
    List<Event> getList(Date to);
    Event getByName(String name);
    void assignAuditorium(Event event, Auditorium auditorium, Date date);
}
