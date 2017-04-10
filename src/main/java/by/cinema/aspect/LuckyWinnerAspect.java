package by.cinema.aspect;

import by.cinema.bean.Ticket;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Kiryl_Parfiankou on 11/6/2015.
 */
@Aspect
@Component
public class LuckyWinnerAspect {

    public static final String LUCKY_EVENT = "LUCKY_EVENT";

    @Pointcut("execution(* by.cinema.service.booking.BookingService.bookTicket(..)) && args(ticket,..)")
    public void bookTicketMethod(Ticket ticket){}

    @Around("bookTicketMethod(ticket)")
    public Object checkLucky(ProceedingJoinPoint pjp, Ticket ticket) throws Throwable {

        if (Math.random() > 0.9) {
            ticket.getUser().getEvents().add(LUCKY_EVENT);
            ticket.setPrice(0);
        }
        return pjp.proceed(new Object[]{ticket});
    }
}