package by.cinema.dao.event.jdbc;

import by.cinema.bean.Auditorium;
import by.cinema.bean.Event;
import by.cinema.bean.Rating;
import by.cinema.bean.User;
import by.cinema.dao.auditorium.AuditoriumBookingDao;
import by.cinema.dao.event.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Kiryl_Parfiankou on 11/16/2015.
 */

@Repository
public class JdbcEventDao implements EventDao{

    @Autowired
    private AuditoriumBookingDao auditoriumBookingDao;

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final class EventMapper implements RowMapper<Event> {
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Event(resultSet.getString("id"),
                             resultSet.getString("name"),
                             resultSet.getLong("price"),
                             Rating.valueOf(resultSet.getString("rating")));
        }
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Event add(Event event) {

        event.setId(UUID.randomUUID().toString());

        String sql = "INSERT INTO events VALUES (:id, :name, :price, :rating)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                                    .addValue("id", event.getId())
                                                    .addValue("name", event.getName())
                                                    .addValue("price", event.getPrice())
                                                    .addValue("rating", event.getRating().toString());
        this.jdbcTemplate.update(sql,namedParameters);

        return event;
    }

    public Event get(String id) {

        String sql = "SELECT * FROM events WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        Event event = null;

        try {
            event = this.jdbcTemplate.queryForObject(sql, namedParameters, new EventMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return event;
    }

    public void remove(String id) {
        String sql = "DELETE FROM events WHERE id = :id";
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        this.jdbcTemplate.update(sql, parameters);
    }

    public List<Event> getList() {
        String sql = "SELECT * FROM events";
        return this.jdbcTemplate.query(sql, new EventMapper());
    }

    public List<Event> getList(Date from, Date to) {
        String sql = "SELECT id, name, price, rating FROM events, auditorium_bookings  WHERE date > :from AND date < :to";
        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("from", from)
                                                .addValue("to", to);
        return this.jdbcTemplate.query(sql, parameters, new EventMapper());
    }

    public List<Event> getList(Date to) {
        String sql = "SELECT id, name, price, rating FROM events,auditorium_bookings WHERE date > CURRENT_DATE AND date < :to";
        SqlParameterSource parameters = new MapSqlParameterSource("to", to);
        return this.jdbcTemplate.query(sql, parameters, new EventMapper());
    }

    public Event getByName(String name) {

        String sql = "SELECT * FROM events WHERE name = :name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        Event event = null;

        try {
            event = this.jdbcTemplate.queryForObject(sql, namedParameters, new EventMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return event;
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        auditoriumBookingDao.assignAuditorium(event, auditorium, date) ;
    }
}
