package by.cinema.dao.account.jdbc;

import by.cinema.bean.User;
import by.cinema.bean.UserAccount;
import by.cinema.dao.account.UserAccountDao;
import by.cinema.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Kiryl_Parfiankou on 4/25/2017.
 */
@Repository
public class JdbcUserAccountDao implements UserAccountDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    private final class UserAccountMapper implements RowMapper<UserAccount> {
        @Override
        public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {

            UserAccount userAccount = new UserAccount();
            userAccount.setId(rs.getString("id"));
            userAccount.setAmount(rs.getLong("amount"));
            String userId = rs.getString("user_id");
            User user = userDao.get(userId);
            userAccount.setUser(user);

            return userAccount;
        }
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<UserAccount> get() {
        String sql = "SELECT * FROM user_accounts";
        return jdbcTemplate.query(sql, new UserAccountMapper());
    }

    @Override
    public UserAccount get(String id) {

        String sql = "SELECT * FROM user_accounts WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                                        .addValue("id", id);

        List<UserAccount> result
                = jdbcTemplate.query(sql, namedParameters, new UserAccountMapper());

        if (result.size() < 1) {
            throw new RuntimeException("User Account Not Found");
        } else if(result.size() > 1) {
            throw new RuntimeException("Data Is Not Consistent");
        }

        return result.get(0);
    }

    @Override
    public UserAccount getByUser(String userId) {

        String sql = "SELECT * FROM user_accounts WHERE user_id = :userId";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId);

        List<UserAccount> result
                = jdbcTemplate.query(sql, namedParameters, new UserAccountMapper());

        if (result.size() < 1) {
            throw new RuntimeException("User Account Not Found");
        } else if(result.size() > 1) {
            throw new RuntimeException("Data Is Not Consistent");
        }

        return result.get(0);
    }

    @Override
    public UserAccount add(UserAccount userAccount) {

        userAccount.setId(UUID.randomUUID().toString());
        String sql = "INSERT INTO user_accounts VALUES (:id, :amount, :user)";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                                        .addValue("id", userAccount.getId())
                                                        .addValue("amount", userAccount.getAmount())
                                                        .addValue("user", userAccount.getUser().getId());

        jdbcTemplate.update(sql, namedParameters);
        return userAccount;
    }

    @Override
    public void update(UserAccount userAccount) {

        String sql = "UPDATE user_accounts SET amount = :amount WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                                                        .addValue("amount", userAccount.getAmount())
                                                        .addValue("id", userAccount.getId());
        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void remove(String id) {

        //TODO implement method
    }
}