package by.cinema.service.auditorium;

import by.cinema.bean.Auditorium;
import by.cinema.dao.auditorium.AuditoriumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 *
 * Service allow get Auditorium objects.
 * getSeatsNumber(), getVipSeats() methods implemented in Auditorium class.
 */
@Service()
public class AuditoriumServiceImpl implements AuditoriumService {

    /**
     * Return all Auditorium objects in system
     */
    @Autowired
    private AuditoriumDao auditoriumDao;

    public List<Auditorium> getAuditoriums() {
        return auditoriumDao.getList();
    }

    /**
     *
     *
     * @param id of Auditorium
     * @return Auditorium object
     */
    public Auditorium get(String id) {
        return auditoriumDao.get(id);
    }
}
