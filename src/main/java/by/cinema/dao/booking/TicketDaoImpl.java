package by.cinema.dao.booking;

import by.cinema.bean.Event;
import by.cinema.bean.Ticket;
import by.cinema.bean.User;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.*;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
public class TicketDaoImpl implements TicketDao {

    private List<Ticket> tickets;
    /**
     * Map for quick access to the Ticket object by Id of Event
     * Event Id -- > Ticket
     */
    private Map<String, List<Ticket>> ticketsEventMap;
    /**
     * Map for quick access to the Ticket object by Id of User
     * User Id -- > Ticket
     */
    private Map<String, List<Ticket>> ticketsUserMap;

    public TicketDaoImpl() {

        tickets = new ArrayList<Ticket>();
        ticketsEventMap = new HashMap<String, List<Ticket>>();
        ticketsUserMap = new HashMap<String, List<Ticket>>();
    }

    public List<Ticket> getList() {
        return tickets;
    }

    public Ticket add(Ticket ticket) {
        // Generates id for ticket;
        Ticket newTicker = new Ticket(UUID.randomUUID().toString(), ticket);

        tickets.add(newTicker);
        addToMaps(newTicker);
        return newTicker;

    }

    public List<Ticket> getTicketsByEvent(String eventId) {
        return ticketsEventMap.get(eventId);
    }

    public List<Ticket> getList(User user) {
        return ticketsUserMap.get(user.getId());
    }

    public List<Ticket> getList(Event event, Date date) {

        List<Ticket> result = new ArrayList<Ticket>();

        if ( ticketsEventMap.containsKey(event.getId())) {
            for( Ticket ticket: ticketsEventMap.get(event.getId()) ) {
                if (ticket.getDate().equals(date)) {
                    result.add(ticket);
                }
            }
        }
        return result;
    }

    /**
     * Update ticketsEventMap and ticketsUserMap maps.
     * @param ticket
     */
    private void addToMaps(Ticket ticket) {

        Event event = ticket.getEvent();

        if (event != null) {
            List<Ticket> eventTickets;

            eventTickets = ticketsEventMap.containsKey(event.getId()) ?
                    ticketsEventMap.get(event.getId()) :
                    new ArrayList<Ticket>();

            eventTickets.add(ticket);
            ticketsEventMap.put(event.getId(), eventTickets);
        }

        User user = ticket.getUser();

        if (user != null) {
            List<Ticket> userTickets;

            userTickets = ticketsUserMap.containsKey(user.getId()) ?
                    ticketsUserMap.get(user.getId()) :
                    new ArrayList<Ticket>();

            userTickets.add(ticket);
            ticketsUserMap.put(user.getId(), userTickets);
        }
    }

    /**
     * Update ticketsEventMap and ticketsUserMap maps.
     * @param ticket
     */
    private void removeFromMaps(Ticket ticket) {
        Event event = ticket.getEvent();
        User user = ticket.getUser();
        if (event != null) {
            List<Ticket> eventTickets = ticketsEventMap.get(event.getId());
            eventTickets.remove(ticket);

            List<Ticket> userTickets = ticketsUserMap.get(user.getId());
            userTickets.remove(user);
        }
    }
}
