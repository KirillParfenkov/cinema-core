package by.cinema.service.discount.common.strategy;


import by.cinema.bean.Event;
import by.cinema.bean.User;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Kiryl_Parfiankou on 10/27/2015.
 */
@Component
public class DiscountStrategyBirthday implements DiscountStrategy {
    public double execute(User user, Event event, Date date) {
        return 0.95; // 5% discount
    }
}
