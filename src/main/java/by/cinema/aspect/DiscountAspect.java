package by.cinema.aspect;

import by.cinema.bean.Counter;
import by.cinema.bean.User;
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
 * Created by Kiryl_Parfiankou on 11/5/2015.
 */
@Aspect
@Component
public class DiscountAspect {

    public static final String DISCOUNT_STRATEGY_BIRTHDAY = "DiscountStrategyBirthday";
    public static final String DISCOUNT_STRATEGY_DOZEN = "DiscountStrategyDozen";
    public static final String TOTAL_COUNTER = "Total";

    @Autowired
    private CounterDao counterDao;

    @Pointcut("execution(public * by.cinema.service.discount.common.strategy.DiscountStrategy.execute(..)) && args(user,..)")
    public void executeStrategyMethod(User user){}

    @Before("executeStrategyMethod(user) && target(by.cinema.service.discount.common.strategy.DiscountStrategyBirthday)")
    public void countTimesExecutionDiscountStrategyBirthday(User user) {
        updateCounter(DISCOUNT_STRATEGY_BIRTHDAY, user);
    }

    @Before("executeStrategyMethod(user) && target(by.cinema.service.discount.common.strategy.DiscountStrategyDozen)")
    public void countTimesExecutionDiscountStrategyDozen(User user) {
        updateCounter(DISCOUNT_STRATEGY_DOZEN, user);
    }

    public Counter getStatistic(String key) {
        return counterDao.get(key);
    }

    private void updateCounter(String key, User user) {

        if (counterDao.get(TOTAL_COUNTER) != null) {
            counterDao.update(TOTAL_COUNTER);
        } else {
            counterDao.add(new Counter(TOTAL_COUNTER, 1));
        }

        if (counterDao.get(key + user.getId()) != null) {
            counterDao.update(key + user.getId());
        } else {
            counterDao.add(new Counter(key + user.getId(), 1));
        }
    }
}
