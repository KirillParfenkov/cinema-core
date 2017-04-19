package by.cinema.service.event;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;
import by.cinema.bean.Rating;
import by.cinema.dao.auditorium.AuditoriumBookingDao;
import by.cinema.dao.auditorium.AuditoriumDao;
import by.cinema.dao.event.EventDao;
import by.cinema.service.auditorium.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
@Service("eventService")
public class EventServiceImpl implements EventService{

    @Autowired
    private EventDao eventDao;
    @Autowired
    private AuditoriumBookingDao auditoriumBookingDao;



    public Event create(String name, long price, Rating rating) {
        return eventDao.add(new Event(name, price, rating));
    }

    public Event create(Event event) {
        return eventDao.add(event);
    }

    public void remove(Event event) {
        eventDao.remove(event.getId());
    }

    public void remove(String eventId) {
        eventDao.remove(eventId);
    }

    public Event getByName(String name) {
        return eventDao.getByName(name);
    }

    public List<Event> getAll() {
        return eventDao.getList();
    }

    public List<Event> getForDateRange(Date from, Date to) {
        return eventDao.getList(from, to);
    }

    public List<Event> getNextEvents(Date to) {
        return eventDao.getList(to);
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        eventDao.assignAuditorium(event, auditorium, date);
    }

    public List<AuditoriumBooking> getAuditoriumBookings(Event event) {
        return auditoriumBookingDao.getByEvent(event.getId());
    }

    @Override
    public AuditoriumBooking getAuditoriumBookingById(String id) {
        return auditoriumBookingDao.get(id);
    }
}
