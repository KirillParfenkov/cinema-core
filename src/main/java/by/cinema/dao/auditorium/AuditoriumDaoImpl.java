package by.cinema.dao.auditorium;

import by.cinema.bean.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kiryl_Parfiankou on 10/26/2015.
 */
@Repository
public class AuditoriumDaoImpl implements AuditoriumDao {

    private List<Auditorium> auditoriums;
    /**
     * Map for quick access to the AuditoriumBooking object by Id of Event
     * Auditorium Id -- > AuditoriumBooking
     */
    private Map<String, Auditorium> auditoriumIdMap;

    public AuditoriumDaoImpl() {
        this.auditoriums = new ArrayList<Auditorium>();
        auditoriumIdMap = new HashMap<String, Auditorium>();
    }

    public List<Auditorium> getList() {
        return auditoriums;
    }

    public Auditorium get(String id) {
        return auditoriumIdMap.get(id);
    }

    @Autowired
    @Resource(name="auditoriumList")
    /**
     * Then Auditorium List contained in auditorium.properties file
     */
    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
        index(auditoriums);
    }

    private void index(List<Auditorium> auditoriums) {
        for(Auditorium auditorium: auditoriums) {
            auditoriumIdMap.put(auditorium.getId(),auditorium);
        }
    }
}
