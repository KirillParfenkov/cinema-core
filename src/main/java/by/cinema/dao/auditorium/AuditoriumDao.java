package by.cinema.dao.auditorium;

import by.cinema.bean.Auditorium;

import java.util.List;
import java.util.Set;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
public interface AuditoriumDao {
    List<Auditorium> getList();
    Auditorium get(String id);
}
