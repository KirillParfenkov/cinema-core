package by.cinema.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public class Auditorium {

    private String id;
    private String name;
    private int numberSeats;
    private Set<Integer> vipSeats;


    public Auditorium(String id, String name, int numberSeats) {
        this.id = id;
        this.name = name;
        this.numberSeats = numberSeats;
    }

    public Auditorium(String id, String name, int numberSeats, String vipSeats) {

        this(id, name, numberSeats);
        this.vipSeats = new HashSet<Integer>();
        for(String vipSeat: vipSeats.split(",")) {
            this.vipSeats.add(Integer.valueOf(vipSeat));
        }

    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + numberSeats + " | " + vipSeats;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public Set<Integer> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Set<Integer> vipSeats) {
        this.vipSeats = vipSeats;
    }
}
