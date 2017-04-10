package by.cinema.bean;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public class Event {

    private String id;
    private String name;
    private long price;
    private Rating rating;

    public Event(String id, Event event) {
        this(id, event.getName(), event.getPrice(), event.getRating());
    }

    public Event(String id, String name, long price, Rating rating) {
        this(name, price, rating);
        this.id = id;
    }

    public Event(String name, long price, Rating rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name + " | " + price + " | " + rating.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
