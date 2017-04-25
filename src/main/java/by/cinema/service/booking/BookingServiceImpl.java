package by.cinema.service.booking;

import by.cinema.bean.*;
import by.cinema.dao.auditorium.AuditoriumBookingDao;
import by.cinema.dao.booking.TicketDao;
import by.cinema.dao.user.UserDao;
import by.cinema.service.account.UserAccountService;
import by.cinema.service.discount.DiscountService;
import by.cinema.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
@Transactional
@Service("bookingService")
public class BookingServiceImpl implements BookingService {

    static private long BASE_PRICE = 900;

    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private UserDao userDAO;
    @Autowired
    private EventService eventService;
    @Autowired
    private AuditoriumBookingDao auditoriumBookingDao;
    @Autowired
    private DiscountService discountService;
    @Autowired
    @Value("${booking.vip.coefficient}")
    private float vipCoefficient;
    @Autowired
    private UserAccountService userAccountService;


    /**
     * Create Ticket with date, time, user, seats, events, price, discount
     *
     * @param event
     * @param date date with time
     * @param seats set of seats
     * @param user
     * @return new Ticket object
     */
    public Ticket create(Event event, Date date, Set<Integer> seats, User user) {

        Ticket ticket = new Ticket(date, event, seats, user);
        ticket.setPrice(getTicketPrice(ticket, user));
        ticket.setDiscount(discountService.getDiscount(user, event, date, ticket.getPrice()));

        return ticket;
    }

    private Auditorium getTicketsForEvent(Ticket ticket) {
        return getAuditorium(ticket.getEvent(), ticket.getDate());
    }

    private Auditorium getAuditorium(Event event, Date date) {

        List<AuditoriumBooking> eventAuditoriums = auditoriumBookingDao.getByEvent(event.getId());
        if (eventAuditoriums == null) {
            return null;
        }

        Date dateWithoutTime = null;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateWithoutTime = df.parse(df.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Auditorium auditorium = null;
        for (AuditoriumBooking auditoriumBooking: eventAuditoriums) {
            if (auditoriumBooking.getDate().equals(dateWithoutTime)) {
                auditorium = auditoriumBooking.getAuditorium();
                break;
            }
        }

        return auditorium;
    }

    public Ticket create(Ticket ticket, long price) {
        return ticket;
    }

    /**
     * Calculate price.  For the method no matter the time of event.
     *
     * @param ticket
     * @param user
     * @return price for ticket
     */
    @Transactional(readOnly = true)
    public long getTicketPrice(Ticket ticket, User user) {

        Set<Integer> seats = ticket.getSeats();
        Event event = ticket.getEvent();
        Auditorium auditorium = getTicketsForEvent(ticket);

        long price = 0;
        Set<Integer> vipSeats = auditorium.getVipSeats();

        for(Integer seat: seats) {
            if (vipSeats.contains(seat)) {
                price += event.getPrice() * vipCoefficient;
            } else {
                price += event.getPrice();
            }
        }

        return price;
    }

    /**
     * Calculate price.  For the method no matter the time of event.
     *
     * @param event
     * @param date date of event
     * @param seats
     * @param user
     * @return price for ticket
     */
    @Transactional(readOnly = true)
    public long getTicketPrice(Event event, Date date, Set<Integer> seats, User user) {

        List<Ticket> eventTickets = ticketDao.getTicketsByEvent(event.getId());
        Auditorium auditorium = getAuditorium(event, date);

        long price = 0;
        Set<Integer> vipSeats = auditorium.getVipSeats();
        for(Integer seat: seats) {
            if (vipSeats.contains(seat)) {
                price += BASE_PRICE * vipCoefficient;
            } else {
                price += BASE_PRICE;
            }
        }

        return price;
    }

    /**
     * Book ticket object.
     * @param ticket
     * @return false if at least one place on a specified time busy.
     */
    public boolean bookTicket(Ticket ticket) {

        List<Ticket> bookedTickets = ticketDao.getList(ticket.getEvent(), ticket.getDate());
        Set<Integer> bookedSeats = new HashSet<Integer>();

        for(Ticket bookedTicket: bookedTickets) {
            bookedSeats.addAll(bookedTicket.getSeats());
        }

        for (Integer seat: ticket.getSeats() ) {
            if (bookedSeats.contains(seat)) {
                return false;
            }
        }
        ticketDao.add(ticket);
        userAccountService.withdraw(ticket.getUser(), ticket.getPrice());
        return true;
    }

    @Transactional(readOnly = true)
    public List<Ticket> getTicketsForEvent(Event event, Date date) {
        return ticketDao.getList(event, date);
    }
}
