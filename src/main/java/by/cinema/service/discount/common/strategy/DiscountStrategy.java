package by.cinema.service.discount.common.strategy;

import by.cinema.bean.Event;
import by.cinema.bean.User;

import java.util.Date;

/**
 * Created by Kiryl_Parfiankou on 10/27/2015.
 */
public interface DiscountStrategy {
    double execute(User user, Event event, Date date);
}
