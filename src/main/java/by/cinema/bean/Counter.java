package by.cinema.bean;

/**
 * Created by Kiryl_Parfiankou on 11/17/2015.
 */
public class Counter {

    private String name;
    private long counter;

    public Counter() {
    }

    public Counter(String name, long counter) {
        this.name = name;
        this.counter = counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}
