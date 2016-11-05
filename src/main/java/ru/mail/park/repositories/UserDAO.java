package ru.mail.park.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mail.park.models.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * Created by kirrok on 21.10.16.
 */

@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    private static final String ADD_USER = "INSERT INTO user (login, password) VALUES(?, ?);";
    private static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?;";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?;";
    private static final String UPDATE_USER = "UPDATE user SET password = ?, login = ? WHERE id = ?;";
    private static final String GET_SCORES = "SELECT login, max_score FROM user order by max_score DESC LIMIT ?;";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";


    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addUser(String login, String password) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement ps = connection.prepareStatement(ADD_USER, new String[]{"id"});
                    ps.setString(1, login);
                    ps.setString(2, password);
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteUser(long id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    private static final RowMapper<User> ROW_MAPPER = (rs, numRow) -> {
        final User tmpUser =
                new User(rs.getString("login"), rs.getString("password"));
        tmpUser.setMaxScore(rs.getInt("max_score"));
        tmpUser.setId(rs.getLong("id"));
        return tmpUser;
    };

    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject(GET_USER_BY_LOGIN, ROW_MAPPER, login);
    }

    public User getUserById(long userId) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, ROW_MAPPER, userId);
    }

    public void updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getPassword(), user.getLogin(), user.getId());
    }

    public List<Map<String, Object>> getUsersScore(String limit) {
        final int lim = Integer.parseInt(limit);
        return jdbcTemplate.queryForList(GET_SCORES, lim);
    }
}
