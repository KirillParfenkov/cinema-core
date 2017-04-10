package by.cinema.aspect;

import by.cinema.bean.Counter;
import by.cinema.dao.counter.CounterDao;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kiryl_Parfiankou on 11/3/2015.
 */
@Aspect
@Component
public class CounterAspect {

    public static final String EVENTS_ACCESSED_KEY = "eventAccessed";
    public static final String PRICES_QUERIED_KEY = "pricesQueried";
    public static final String TICKETS_BOOKED_KEY = "ticketsBooked";

    @Autowired
    private CounterDao counterDao;

    @Pointcut("execution(public * by.cinema.service.event.EventService.getByName(..))")
    public void eventGetByNameMethod(){}
    @Pointcut("execution(public * by.cinema.service.booking.BookingService.getTicketPrice(..))")
    public void getTicketPriceMethod(){}
    @Pointcut("execution(public * by.cinema.service.booking.BookingService.bookTicket(..))")
    public void bookTicketMethod(){}

    @Before("eventGetByNameMethod()")
    void countTimesEventAccessedByName() {
        updateCounter(EVENTS_ACCESSED_KEY);
    }

    @Before("getTicketPriceMethod()")
    public void countTimesGetTicketPrice() {
        updateCounter(PRICES_QUERIED_KEY);
    }

    @Before("bookTicketMethod()")
    public void countTimesBookTicketMethod() {
        updateCounter(TICKETS_BOOKED_KEY);
    }

    public Map<String, Long> getStatistic() {

        Map<String, Long> map = new HashMap<String, Long>();
        Counter eventAccessed = counterDao.get(EVENTS_ACCESSED_KEY);
        Counter pricesQueried = counterDao.get(PRICES_QUERIED_KEY);
        Counter ticketsBooked = counterDao.get(TICKETS_BOOKED_KEY);

        map.put(EVENTS_ACCESSED_KEY, eventAccessed != null ? eventAccessed.getCounter() : 0);
        map.put(PRICES_QUERIED_KEY, pricesQueried != null ? pricesQueried.getCounter() : 0);
        map.put(TICKETS_BOOKED_KEY, ticketsBooked != null ? ticketsBooked.getCounter() : 0);
        return map;
    }

    private void updateCounter(String key) {

        if (counterDao.get(key) != null) {
            counterDao.update(key);
        } else {
            counterDao.add(new Counter(key, 1));
        }
    }
}
