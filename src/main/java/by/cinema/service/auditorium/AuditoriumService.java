package by.cinema.service.auditorium;

import by.cinema.bean.Auditorium;

import java.util.List;
import java.util.Set;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public interface AuditoriumService {

    List<Auditorium> getAuditoriums();
    Auditorium get(String id);

}