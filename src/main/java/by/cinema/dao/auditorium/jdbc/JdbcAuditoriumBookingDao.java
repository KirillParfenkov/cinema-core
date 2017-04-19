package by.cinema.dao.auditorium.jdbc;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;
import by.cinema.dao.auditorium.AuditoriumBookingDao;
import by.cinema.dao.auditorium.AuditoriumDao;
import by.cinema.dao.event.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Kiryl_Parfiankou on 11/16/2015.
 */
@Repository
public class JdbcAuditoriumBookingDao implements AuditoriumBookingDao{

    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Autowired
    private EventDao eventDao;
    @Autowired
    private AuditoriumDao auditoriumDao;

    private final class AuditoriumBookingMapper implements RowMapper<AuditoriumBooking> {
        public AuditoriumBooking mapRow(ResultSet resultSet, int i) throws SQLException {

            return new AuditoriumBooking(
                    resultSet.getString("id"),
                    resultSet.getDate("date"),
                    auditoriumDao.get(resultSet.getString("auditorium_id")),
                    eventDao.get(resultSet.getString("event_id")));
        }
    }

    public List<AuditoriumBooking> getAll() {
        String sql = "SELECT * FROM auditorium_bookings";
        return jdbcTemplate.query(sql, new AuditoriumBookingMapper());
    }

    public List<AuditoriumBooking> getByEvent(String eventId) {
        String sql = "SELECT * FROM auditorium_bookings WHERE event_id = :eventId";
        SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);
        return jdbcTemplate.query(sql, parameters, new AuditoriumBookingMapper());
    }

    public List<AuditoriumBooking> getByAuditorium(String auditoriumId) {
        String sql = "SELECT * FROM auditorium_bookings WHERE auditorium_id = :auditoriumId";
        SqlParameterSource parameters = new MapSqlParameterSource("auditoriumId", auditoriumId);
        return jdbcTemplate.query(sql, parameters, new AuditoriumBookingMapper());
    }

    public List<AuditoriumBooking> getByEventAuditorium(String eventId, String auditoriumId) {
        String sql = "SELECT * FROM auditorium_bookings WHERE event_id = :eventId AND auditorium_id = :auditoriumId";
        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("eventId", eventId)
                                                .addValue("auditoriumId", auditoriumId);
        return jdbcTemplate.query(sql, new AuditoriumBookingMapper());
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {

        String sql = "INSERT INTO auditorium_bookings VALUES (:id, :date, :auditoriumId, :eventId)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("id", UUID.randomUUID().toString())
                                                .addValue("date", date)
                                                .addValue("auditoriumId", auditorium.getId())
                                                .addValue("eventId", event.getId());
        jdbcTemplate.update(sql,parameters);
    }

    public void removeByEvent(String eventId) {
        String sql = "DELETE FROM auditorium_bookings WHERE event_id = :eventId";
        SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);
        jdbcTemplate.update(sql,parameters);
    }

    public void removeByAuditorium(String auditoriumId) {
        String sql = "DELETE FROM auditorium_bookings WHERE auditorium_id = :auditoriumId";
        SqlParameterSource parameters = new MapSqlParameterSource("auditoriumId", auditoriumId);
        jdbcTemplate.update(sql,parameters);
    }

    @Override
    public AuditoriumBooking get(String id) {
        String sql = "SELECT * FROM auditorium_bookings WHERE id = :id";
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        List<AuditoriumBooking> result = jdbcTemplate.query(sql, parameters, new AuditoriumBookingMapper());
        return result != null ? result.get(0): null;
    }
}
