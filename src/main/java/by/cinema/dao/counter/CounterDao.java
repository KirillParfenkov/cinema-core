package by.cinema.dao.counter;

import by.cinema.bean.Counter;

/**
 * Created by Kiryl_Parfiankou on 11/16/2015.
 */
public interface CounterDao {
    Counter get(String name);
    void update(String name);
    void add(Counter counter);
}
