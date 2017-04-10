package by.cinema;

import by.cinema.aspect.CounterAspect;
import by.cinema.aspect.DiscountAspect;
import by.cinema.bean.*;
import by.cinema.configuration.ApplicationConfiguration;
import by.cinema.dao.counter.CounterDao;
import by.cinema.dao.user.UserDao;
import by.cinema.service.auditorium.AuditoriumService;
import by.cinema.service.booking.BookingService;
import by.cinema.service.event.EventService;
import by.cinema.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
@Component("app")
public class App {

    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private CounterAspect counterAspect;
    @Autowired
    private DiscountAspect discountAspect;
    @Autowired
    private CounterDao counterDao;

    @Autowired
    private UserDao userDao;

    public static void main(String args[], String test) {

    }


    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        App app = ctx.getBean("app", App.class);

        // Test User Service
        Date today = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            today = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User bobBobs = app.userService.register(new User("bob.bobs@email.com", "Bob", "Bobs", today));
        User vasiaPup = app.userService.register(new User("vasia.pup@gmail.com", "Vasia", "Pup"));
        User testUser = app.userService.register(new User("test.user@gmail.com", "Test", "User"));


        app.counterDao.add(new Counter(testUser.getId(), 0));
        app.counterDao.update(testUser.getId());
        app.counterDao.update(testUser.getId());
        app.counterDao.update(testUser.getId());
        Counter testCounter = app.counterDao.get(testUser.getId());
        System.out.println("Counter: " + testCounter.getCounter());


        System.out.println("\nUser Service Test:");
        System.out.println("User by id: " + app.userService.getById(bobBobs.getId()));
        System.out.println("User by email: " + app.userService.getUserByEmail(vasiaPup.getEmail()));
        System.out.println("User by name: " + app.userService.getUsersByName(testUser.getLastName()));

        // Test Test Auditorium Service

        app.auditoriumService.getAuditoriums();
        System.out.println("\nAuditorium Service Test:");
        for(Auditorium auditorium: app.auditoriumService.getAuditoriums()) {
            System.out.println(auditorium);
        }

        // Test Event Service
        System.out.println("\nEvent Service Test:");
        Event starWars = app.eventService.create(new Event("Star Wars", 900, Rating.HIGH));
        Event martian = app.eventService.create(new Event("The Martian", 900, Rating.MID));
        Event scream = app.eventService.create(new Event("Scream", 900, Rating.LOW));

        Auditorium redAuditorium = app.auditoriumService.get(AuditoriumCode.RED_ROOM.toString());
        Auditorium blackAuditorium = app.auditoriumService.get(AuditoriumCode.BLACK_ROOM.toString());
        Auditorium yellowAuditorium = app.auditoriumService.get(AuditoriumCode.YELLOW_ROOM.toString());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            app.eventService.assignAuditorium(starWars, redAuditorium, df.parse("21/12/2015"));
            app.eventService.assignAuditorium(starWars, redAuditorium, df.parse("22/12/2015"));
            app.eventService.assignAuditorium(martian, blackAuditorium, df.parse("23/12/2015"));
            app.eventService.assignAuditorium(scream, yellowAuditorium, df.parse("24/12/2015"));


            List<Event> events = app.eventService.getAll();
            System.out.println("\nEvent Service Test:");
            System.out.println("All events:");
            for (Event event: events) {
                System.out.println(event);
            }

            Date from = df.parse("22/12/2015");
            Date to = df.parse("25/12/2015");

            System.out.println("\nEvents from 22/12/2015 to 25/12/2015");
            for(Event event: app.eventService.getForDateRange(from, to)) {
                System.out.println(event);
            }

            System.out.println("\nEvents from now to 24/12/2015");
            for(Event event: app.eventService.getNextEvents(df.parse("24/12/2015"))) {
                System.out.println(event);
            }



        } catch (ParseException e){
            e.printStackTrace();
        }

        // Test Booking Service
        System.out.println("\nBooking Service Test:");
        Set<Integer> vasiaSeats = null;
        Set<Integer> bobSeats = null;
        try {

            bobSeats = new HashSet<Integer>();
            bobSeats.add(6);// vip = 900 * 2
            bobSeats.add(7);// no vip = 900
            bobSeats.add(8);// no vip = 900

            Ticket bobTicket = app.bookingService.create(starWars, df.parse("22/12/2015"), bobSeats, bobBobs);
            System.out.println("Bob Price: " + bobTicket.getPrice());
            System.out.println("Bob Discount: " + bobTicket.getDiscount());

            boolean isBooked = app.bookingService.bookTicket(bobTicket);
            System.out.println("Bob Ticket booked: " + isBooked); // true

            vasiaSeats = new HashSet<Integer>();
            vasiaSeats.add(1);// vip = 900 * 2
            vasiaSeats.add(2);// vip = 900 * 2
            vasiaSeats.add(6);// vip = 900 * 2
            vasiaSeats.add(8);// no vip = 900

            Ticket vasiaTicket = app.bookingService.create(starWars,df.parse("22/12/2015"),vasiaSeats, vasiaPup);
            System.out.println("Vasia Price: " + vasiaTicket.getPrice());
            System.out.println("Vasia Discount: " + vasiaTicket.getDiscount());
            isBooked = app.bookingService.bookTicket(vasiaTicket);
            System.out.println("Ticket booked: " + isBooked); //false

        } catch (ParseException e){
            e.printStackTrace();
        }


        // ========== AOP Home task ===============
        System.out.println("\n-------- AOP Home task --------");
        // >>>>>>>>>> Test CounterAspect <<<<<<<<<<
        System.out.println("\nTest CounterAspect");
        app.eventService.getByName("Star Wars");
        app.eventService.getByName("Star Wars 2");
        app.eventService.getByName("Star Wars 3");
        app.eventService.getByName("The Martian");
        app.eventService.getByName("Scream");

        try {
            app.bookingService.getTicketPrice(starWars, df.parse("22/12/2015"), vasiaSeats, vasiaPup); // In this case we work with proxy
            System.out.println("BookingService class name: " + app.bookingService.getClass().getName());
        } catch (ParseException e){
            e.printStackTrace();
        }


        Map<String, Long> counterAspectStatist = app.counterAspect.getStatistic();
        System.out.println("EVENTS_ACCESED: " + counterAspectStatist.get(CounterAspect.EVENTS_ACCESSED_KEY));
        System.out.println("PRICES_QUERIED_KEY: " + counterAspectStatist.get(CounterAspect.PRICES_QUERIED_KEY));
        System.out.println("TICKETS_BOOKED_KEY: " + counterAspectStatist.get(CounterAspect.TICKETS_BOOKED_KEY));
        /*
            PRICES_QUERIED_KEY The counter contains the value 1, but really the method is called 3 times.
            2 times a method 'getTicketPrice' was called with method 'bookTicket'.
        */

        // >>>>>>> DiscountAspect <<<<<<<<<<
        System.out.println("\nTest DiscountAspect");
        // In previous tests already once invoked discount method.

        Set<Integer> tmpSeats;
        Ticket newVasiaTicket;

        try {
            for (int i = 0; i < 21; i++) {
                tmpSeats = new HashSet<Integer>();
                tmpSeats.add(i + 10);
                newVasiaTicket = app.bookingService.create(starWars, df.parse("22/12/2015"), tmpSeats, vasiaPup);
                app.bookingService.bookTicket(newVasiaTicket);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // DISCOUNT STRATEGY BIRTHDAY
        System.out.println("DISCOUNT_STRATEGY_BIRTHDAY");
        Counter vasiaCounter = app.discountAspect.getStatistic(DiscountAspect.DISCOUNT_STRATEGY_BIRTHDAY + bobBobs.getId());
        System.out.println(vasiaPup.getFullName() + " - " + vasiaCounter.getCounter());

        System.out.println("DISCOUNT_STRATEGY_DOZEN");
        Counter bobCounter =  app.discountAspect.getStatistic(DiscountAspect.DISCOUNT_STRATEGY_DOZEN + vasiaPup.getId());
        System.out.println(bobBobs.getFullName() + " - " + bobCounter.getCounter());

    }

    public AuditoriumService getAuditoriumService() {
        return auditoriumService;
    }

    public UserService getUserService() {
        return userService;
    }
}
