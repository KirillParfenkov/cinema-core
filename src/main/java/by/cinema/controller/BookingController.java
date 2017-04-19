package by.cinema.controller;

import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;
import by.cinema.bean.Ticket;
import by.cinema.bean.User;
import by.cinema.service.booking.BookingService;
import by.cinema.service.event.EventService;
import by.cinema.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Kiryl_Parfiankou on 4/19/2017.
 */
@Controller
@RequestMapping("booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getBookForm(@RequestParam(required = true) String audBookId) {

        AuditoriumBooking auditoriumBooking
                = eventService.getAuditoriumBookingById(audBookId);
        return new ModelAndView("book-ticket").addObject("audBook", auditoriumBooking);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String book(@RequestParam(required = true) String seats,
                       @RequestParam(required = true) String audBookId) {


        String[] seatArr = seats.split(",");
        Set<Integer> seatSet = Arrays.stream(seatArr)
                .map( seat -> Integer.valueOf(seat))
                .collect(Collectors.toSet());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUsersByName(userDetails.getUsername());

        AuditoriumBooking auditoriumBooking
                = eventService.getAuditoriumBookingById(audBookId);

        Ticket ticket = bookingService.create(auditoriumBooking.getEvent(),
                              auditoriumBooking.getDate(),
                              seatSet, user);
        bookingService.bookTicket(ticket);

        return "redirect:bookSuccess";
    }

    @RequestMapping("showAllTickets")
    public ModelAndView showAllTickets(@RequestParam(required = true) String audBookId) {

        AuditoriumBooking auditoriumBooking
                = eventService.getAuditoriumBookingById(audBookId);
        List<Ticket> tickets = bookingService.getTicketsForEvent(auditoriumBooking.getEvent(),
                                                                 auditoriumBooking.getDate());
        return new ModelAndView("show-tickets").addObject("tickets", tickets);
    }

}
