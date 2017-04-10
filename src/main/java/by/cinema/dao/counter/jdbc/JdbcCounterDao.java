package by.cinema.dao.counter.jdbc;

import by.cinema.bean.Counter;
import by.cinema.dao.counter.CounterDao;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;

/**
 * Created by Kiryl_Parfiankou on 11/16/2015.
 */
@Repository
public class JdbcCounterDao implements CounterDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final class CounterRowMapper implements RowMapper<Counter> {
        public Counter mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Counter(resultSet.getString("name"), resultSet.getLong("counter"));
        }
    }

    public Counter get(String name) {
        String sql = "SELECT * FROM counters WHERE name = :name";
        SqlParameterSource parameters = new MapSqlParameterSource("name", name);
        Counter counter = null;
        try {
            counter = jdbcTemplate.queryForObject(sql,parameters, new CounterRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return counter;
    }

    public void update(String name) {
        String sql = "UPDATE counters SET counter = counter + 1 WHERE name = :name";
        SqlParameterSource parameterSource = new MapSqlParameterSource("name", name);
        jdbcTemplate.update(sql, parameterSource);
    }

    public void add(Counter counter) {
        String sql = "INSERT INTO counters VALUES(:name, :counter)";
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(counter);
        jdbcTemplate.update(sql,parameters);
    }
}
