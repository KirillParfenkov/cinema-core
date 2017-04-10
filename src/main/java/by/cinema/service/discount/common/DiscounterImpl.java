package by.cinema.service.discount.common;

import by.cinema.bean.Event;
import by.cinema.bean.Ticket;
import by.cinema.bean.User;
import by.cinema.service.discount.common.strategy.DiscountStrategy;
import by.cinema.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 11/5/2015.
 */
@Component
public class DiscounterImpl implements Discounter{

    @Autowired
    private DiscountStrategy discountStrategyBirthday;
    @Autowired
    private DiscountStrategy discountStrategyDozen;
    @Autowired
    private UserService userService;


    public double getDiscount(User user, Event event, Date date) {

        double discount = 1;

        Date today = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            today = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //5% discount for fhe person whose birthday
        if (user.getBirthday() != null && user.getBirthday().equals(today)) {
            discount *= discountStrategyBirthday.execute(user, event, date);
        }

        List<Ticket> tickets = userService.getBookedTickets(user);

        //50% discount for fhe person with 10 tickets
        if ((tickets != null && tickets.size() % 10 == 0) && tickets.size() > 0) {
            discount *= discountStrategyDozen.execute(user, event, date);
        }

        return discount;
    }
}
