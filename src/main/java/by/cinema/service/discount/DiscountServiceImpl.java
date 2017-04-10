package by.cinema.service.discount;

import by.cinema.bean.Event;
import by.cinema.bean.User;
import by.cinema.service.discount.common.Discounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Kiryl_Parfiankou on 10/27/2015.
 */
@Component
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private Discounter discounter;

    public long getDiscount(User user, Event event, Date date, long price) {

        long discount = price - Math.round(price * discounter.getDiscount(user, event, date));

        return discount;
    }
}
