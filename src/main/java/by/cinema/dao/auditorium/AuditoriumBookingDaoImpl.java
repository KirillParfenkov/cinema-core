package by.cinema.dao.auditorium;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Kiryl_Parfiankou on 10/27/2015.
 *
 * Dao for binding Auditorium and Event objects
 */
public class AuditoriumBookingDaoImpl implements AuditoriumBookingDao {

    private List<AuditoriumBooking> auditoriumBookingList;


    /**
     * Map for quick access to the AuditoriumBooking object by Id of Event
     * Event Id -- > AuditoriumBooking
     */
    private Map<String, List<AuditoriumBooking>> eventAuditoriumBookingMap;
    /**
     * Map for quick access to the AuditoriumBooking object by Id of Auditorium
     * Auditorium Id -- > AuditoriumBooking
     */
    private Map<String, List<AuditoriumBooking>> audAuditoriumBookingMap;


    public AuditoriumBookingDaoImpl() {
        auditoriumBookingList = new ArrayList<AuditoriumBooking>();
        eventAuditoriumBookingMap = new HashMap<String, List<AuditoriumBooking>>();
        audAuditoriumBookingMap = new HashMap<String, List<AuditoriumBooking>>();
    }

    public List<AuditoriumBooking> getAll() {
        return auditoriumBookingList;
    }

    public List<AuditoriumBooking> getByEvent(String eventId) {
        return eventAuditoriumBookingMap.get(eventId);
    }

    public List<AuditoriumBooking> getByAuditorium(String auditoriumId) {
        return audAuditoriumBookingMap.get(auditoriumId);
    }

    public List<AuditoriumBooking> getByEventAuditorium(String eventId, String  auditoriumId) {

        List<AuditoriumBooking> result = new ArrayList<AuditoriumBooking>();

        List<AuditoriumBooking> audBookingList = audAuditoriumBookingMap.get(eventId);
        for(AuditoriumBooking auditoriumBooking: audBookingList) {
            if(auditoriumBooking.getAuditorium().getId().equals(auditoriumId)) {
                result.add(auditoriumBooking);
            }
        }
        return result;
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        AuditoriumBooking auditoriumBooking = new AuditoriumBooking(date, auditorium, event);
        auditoriumBookingList.add(auditoriumBooking);

        List<AuditoriumBooking> eventAuditoriumBookingList = eventAuditoriumBookingMap.containsKey(event.getId()) ?
                                                             eventAuditoriumBookingMap.get(event.getId()) :
                                                             new ArrayList<AuditoriumBooking>();
        eventAuditoriumBookingList.add(auditoriumBooking);
        eventAuditoriumBookingMap.put(event.getId(), eventAuditoriumBookingList);

        List<AuditoriumBooking> audAuditoriumBookingList = audAuditoriumBookingMap.containsKey(auditorium.getId()) ?
                                                           audAuditoriumBookingMap.get(auditorium.getId()) :
                                                           new ArrayList<AuditoriumBooking>();
        audAuditoriumBookingList.add(auditoriumBooking);
        audAuditoriumBookingMap.put(auditorium.getId(), audAuditoriumBookingList);
    }

    public void removeByEvent(String eventId) {
        eventAuditoriumBookingMap.remove(eventId);
    }
    public void removeByAuditorium(String auditoriumId) {
        audAuditoriumBookingMap.remove(auditoriumId);
    }

    @Override
    public AuditoriumBooking get(String id) {
        return null;
    }
}
