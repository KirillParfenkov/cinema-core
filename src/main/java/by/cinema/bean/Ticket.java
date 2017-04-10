package by.cinema.bean;
import java.util.Date;
import java.util.Set;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public class Ticket {

    private String id;
    private Event event;
    private Date date;
    private Set<Integer> seats;
    private User user;
    private long price;
    private long discount;


    public Ticket(Date date, Set<Integer> seats, User user) {
        this.date = date;
        this.seats = seats;
        this.user = user;
    }

    public Ticket(Date date, Event event, Set<Integer> seats, User user) {
        this(date, seats, user);
        this.event = event;
    }

    public Ticket(String id, Date date, Event event, Set<Integer> seats, User user, long price, long discount ) {
        this(date, event, seats, user);
        this.id = id;
        this.price = price;
        this.discount = discount;
    }

    public Ticket(String id, Ticket ticket) {
        this(id, ticket.getDate(), ticket.getEvent(), ticket.getSeats(),
                ticket.getUser(), ticket.getPrice(), ticket.getDiscount());
    }


    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Integer> getSeats() {
        return seats;
    }

    public void setSeats(Set<Integer> seats) {
        this.seats = seats;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}