package by.cinema.bean;

/**
 * Created by Kiryl_Parfiankou on 4/25/2017.
 */
public class UserAccount {

    private String id;
    private long amount;
    private User user;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
