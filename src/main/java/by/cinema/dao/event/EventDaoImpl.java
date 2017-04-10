package by.cinema.dao.event;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;
import by.cinema.dao.auditorium.AuditoriumBookingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
public class EventDaoImpl implements EventDao {

    private List<Event> events;

    /**
     * Map for quick access to the Event object by Id
     * Id -- > Event
     */
    private Map<String, Event> eventIdMap;
    /**
     * Map for quick access to the Event object by Name
     * Name -- > Event
     */
    private Map<String, Event> eventNameMap;

    @Autowired()
    private AuditoriumBookingDao auditoriumBookingDao;

    public EventDaoImpl(List<Event> events) {

        this.events = events;
        eventNameMap = new HashMap<String, Event>();
        eventIdMap = new HashMap<String, Event>();

        for(Event event: events) {
            addToMaps(event);
        }
    }

    public EventDaoImpl() {

        this.events = new ArrayList<Event>();
        eventNameMap = new HashMap<String, Event>();
        eventIdMap = new HashMap<String, Event>();
    }

    public Event add(Event event) {
        //Generate if for Event
        Event addedEvent = new Event(UUID.randomUUID().toString(), event);

        events.add(addedEvent);
        addToMaps(addedEvent);
        return addedEvent;
    }

    public Event get(String id) {
        return eventIdMap.get(id);
    }

    public void remove(String id) {
        events.remove(eventIdMap.get(id));
    }

    public List<Event> getList() {
        return events;
    }

    public List<Event> getList(Date from, Date to) {

        Set<Event> result= new HashSet<Event>();
        Date date;

        for(AuditoriumBooking auditoriumBooking: auditoriumBookingDao.getAll()) {
            date = auditoriumBooking.getDate();
            if (from.before(date) && to.after(date)) {
                result.add(auditoriumBooking.getEvent());
            }
        }
        return new ArrayList<Event>(result);
    }

    public List<Event> getList(Date to) {

        Set<Event> result= new HashSet<Event>();
        Date date;
        Date now = new Date();
        for(AuditoriumBooking auditoriumBooking: auditoriumBookingDao.getAll()) {
            date = auditoriumBooking.getDate();
            if (now.before(date) && to.after(date)) {
                result.add(auditoriumBooking.getEvent());
            }
        }
        return new ArrayList<Event>(result);
    }

    public Event getByName(String name) {
        return eventNameMap.get(name);
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        auditoriumBookingDao.assignAuditorium(event, auditorium, date) ;
    }

    /**
     * Update eventIdMap and eventNameMap
     * @param event
     */
    private void addToMaps(Event event) {
        eventIdMap.put(event.getId(), event);
        eventNameMap.put(event.getName(), event);
    }

    /**
     * Update eventIdMap and eventNameMap
     * @param event
     */
    private void removeFromMaps(Event event) {
        eventIdMap.remove(event.getId());
        eventNameMap.remove(event.getName());
        for (AuditoriumBooking auditoriumBooking: auditoriumBookingDao.getAll()) {
            if (auditoriumBooking.getEvent().getId() == event.getId()) {
                auditoriumBookingDao.removeByEvent(event.getId());
            }
        }
    }
}
