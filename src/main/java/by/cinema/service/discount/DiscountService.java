package by.cinema.service.discount;

import by.cinema.bean.Event;
import by.cinema.bean.User;

import java.util.Date;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public interface DiscountService {
    long getDiscount(User user, Event event, Date date, long price);
}
