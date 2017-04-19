package by.cinema.bean;

import java.util.Date;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 *
 * Service binds Auditorium and Event objects.
 */
public class AuditoriumBooking {

    private String id;
    private Date date;
    private Auditorium auditorium;
    private Event event;

    public AuditoriumBooking() {
    }

    public AuditoriumBooking(String id, Date date, Auditorium auditorium, Event event) {
        this.id = id;
        this.date = date;
        this.auditorium = auditorium;
        this.event = event;
    }

    public AuditoriumBooking(Date date, Auditorium auditorium, Event event) {
        this.date = date;
        this.auditorium = auditorium;
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
