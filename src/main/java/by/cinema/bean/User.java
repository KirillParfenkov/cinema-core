package by.cinema.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public class User {

    private String id;
    private String email;
    private String lastName;
    private String firstName;
    private Date birthday;
    private List<String> events;

    public User(String id, User user, String lastName, String firstName, Date birthday) {
        this(user.getEmail(), user.getLastName(), user.getFirstName(), user.getBirthday());
        this.id = id;
    }

    public User(String email, String lastName, String firstName) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.events = new ArrayList<String>();
    }

    public User(String email, String lastName, String firstName, Date birthday) {
        this(email, lastName, firstName);
        this.birthday = birthday;
    }

    public User(String id, String email, String lastName, String firstName, Date birthday) {
        this(email, lastName, firstName, birthday);
        this.id = id;
    }

    public User(String id, User user) {
        this(id, user.getEmail(), user.getLastName(), user.getFirstName(), user.getBirthday());
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}