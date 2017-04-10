package by.cinema.dao.booking.jdbc;

import by.cinema.bean.Event;
import by.cinema.bean.Ticket;
import by.cinema.bean.User;
import by.cinema.dao.booking.TicketDao;
import by.cinema.dao.event.EventDao;
import by.cinema.dao.user.UserDao;
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
import java.util.*;

/**
 * Created by Kiryl_Parfiankou on 11/16/2015.
 */
@Repository
public class JdbcTicketDao implements TicketDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Autowired
    private UserDao userDao;
    @Autowired
    private EventDao eventDao;

    private final class  TicketMapper implements RowMapper<Ticket> {
        private final static String SEPARATOR = ",";

        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Ticket(resultSet.getString("id"),
                              resultSet.getDate("date"),
                              eventDao.get(resultSet.getString("event_id")),
                              new HashSet<Integer>(convert(resultSet.getString("seats"))),
                              userDao.get(resultSet.getString("user_id")),
                              resultSet.getLong("price"),
                              resultSet.getLong("discount"));
        }

        private Set<Integer> convert(String str) {
            Set<Integer> set = new HashSet<Integer>();
            for(String snippet: str.split(SEPARATOR)) {
                set.add(Integer.valueOf(snippet));
            }
            return set;
        }

    }

    public List<Ticket> getList() {
        String sql = "SELECT * FROM tickets";
        return jdbcTemplate.query(sql, new TicketMapper());
    }

    public List<Ticket> getList(User user) {
        String sql = "SELECT * FROM tickets WHERE user_id = :userId";
        SqlParameterSource parameters = new MapSqlParameterSource("userId", user.getId());
        return jdbcTemplate.query(sql, parameters, new TicketMapper());
    }

    public List<Ticket> getList(Event event, Date date) {
        String sql = "SELECT * FROM tickets WHERE event_id = :eventId AND date = :date";
        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("eventId", event.getId())
                                                .addValue("date", date);
        return jdbcTemplate.query(sql, parameters, new TicketMapper());
    }

    public List<Ticket> getTicketsByEvent(String eventId) {
        String sql = "SELECT * FROM tickets WHERE event_id = :eventId";
        SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);
        return jdbcTemplate.query(sql, parameters, new TicketMapper());
    }

    public Ticket add(Ticket ticket) {
        ticket.setId(UUID.randomUUID().toString());

        String sql = "INSERT INTO tickets VALUES(:id, :eventId, :date, :seats, :userId, :price, :discount)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("eventId", ticket.getEvent().getId())
                .addValue("date", ticket.getDate())
                .addValue("seats", convert(ticket.getSeats()))
                .addValue("userId", ticket.getUser().getId())
                .addValue("price", ticket.getPrice())
                .addValue("discount", ticket.getDiscount());

        jdbcTemplate.update(sql,parameters);
        return ticket;
    }

    private String convert(Set<Integer> set) {
        StringBuffer sb = new StringBuffer();
        for(Integer snippet: set) {
            sb.append(snippet);
        }
        return sb.toString();
    }
}
