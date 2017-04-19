package by.cinema.dao.user.jdbc;

import by.cinema.bean.User;
import by.cinema.dao.user.UserDao;
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
import java.util.UUID;

/**
 * Created by Kiryl_Parfiankou on 11/16/2015.
 */
@Repository
public class JdbcUserDao implements UserDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setEmail(resultSet.getString("email"));
            user.setLastName(resultSet.getString("lastName"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPassword(resultSet.getString("password"));
            user.setRoles(resultSet.getString("roles"));
            return user;
        }
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public User add(User user) {

        user.setId(UUID.randomUUID().toString());

        String sql = "INSERT INTO users VALUES (:id, :email, :lastName, :firstName, :password, :roles, :birthday)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
        this.jdbcTemplate.update(sql,namedParameters);

        return user;
    }

    public User get(String id) {

        String sql = "SELECT * FROM users WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id",id);
        User user = null;
        try {
            user = this.jdbcTemplate.queryForObject(sql, namedParameters, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    public User getByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = :email";
        SqlParameterSource namedParameters = new MapSqlParameterSource("email",email);
        User user = null;
        try {
            user = this.jdbcTemplate.queryForObject(sql, namedParameters, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    public User getByName(String name) {
        String sql = "SELECT * FROM users WHERE lastName = :name";

        SqlParameterSource namedParameters = new MapSqlParameterSource("name",name);
        User user = null;
        try {
            user = this.jdbcTemplate.queryForObject(sql, namedParameters, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    public void remove(String id) {
        String sql = "DELETE FROM users WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id",id);

        this.jdbcTemplate.update(sql, namedParameters);
    }
}
