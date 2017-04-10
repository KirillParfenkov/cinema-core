package by.cinema.dao.auditorium;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;

import java.util.Date;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/27/2015.
 *
 * Dao for binding Auditorium and Event objects
 */
public interface AuditoriumBookingDao {

    List<AuditoriumBooking> getAll();
    List<AuditoriumBooking> getByEvent(String eventId);
    List<AuditoriumBooking> getByAuditorium(String auditoriumId);
    List<AuditoriumBooking> getByEventAuditorium(String eventId, String auditoriumId);
    void assignAuditorium(Event event, Auditorium auditorium, Date date);
    void removeByEvent(String eventId);
    void removeByAuditorium(String auditoriumId);
}
