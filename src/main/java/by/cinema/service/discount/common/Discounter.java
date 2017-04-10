package by.cinema.service.discount.common;

import by.cinema.bean.Event;
import by.cinema.bean.User;

import java.util.Date;

/**
 * Created by Kiryl_Parfiankou on 11/5/2015.
 *
 * This class allows to calculate the discount.
 */
public interface Discounter {
    /**
     * Then method return discount factor.
     * @param user The user who receives a discount.
     * @param event The event to which the discount applies.
     * @param date event date
     * @return Discount factor.
     */
    public double getDiscount(User user, Event event, Date date);
}
