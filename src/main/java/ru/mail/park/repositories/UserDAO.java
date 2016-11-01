package ru.mail.park.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mail.park.Application;
import ru.mail.park.dataSets.UserDataSet;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * Created by kirrok on 21.10.16.
 */

@SuppressWarnings("all")
@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    final String ADD_USER = "INSERT INTO user (login, password) VALUES(?, ?);";
    final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?;";
    final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?;";
    final String UPDATE_USER = "UPDATE user SET max_score = ?, password = ?, login = ? WHERE id = ?;";
    final String GET_SCORES = "SELECT login, max_score FROM user order by -max_score LIMIT ?;";
    final String DELETE_USER = "DELETE FROM user WHERE id = ?";


    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addUser(String login, String password) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

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

    public UserDataSet getUserByLogin(String login) {
        return jdbcTemplate.queryForObject(GET_USER_BY_LOGIN,
                (rs, numRow) -> {
                    final UserDataSet tmpUser =
                            new UserDataSet(rs.getString("login"), rs.getString("password"));
                    tmpUser.setMaxScore(rs.getInt("max_score"));
                    tmpUser.setId(rs.getLong("id"));
                    return tmpUser;
                }, login);
    }

    public UserDataSet getUserById(long userId) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID,
                (rs, numRow) -> {
                    final UserDataSet tmpUser =
                            new UserDataSet(rs.getString("login"), rs.getString("password"));
                    tmpUser.setMaxScore(rs.getInt("max_score"));
                    tmpUser.setId(rs.getLong("id"));
                    return tmpUser;
                }, userId);
    }

    public void updateUser(UserDataSet user) {
        jdbcTemplate.update(UPDATE_USER, user.getMaxScore(), user.getPassword(), user.getLogin(), user.getId());
    }

    public List<Map<String, Object>> getUsersScore(String limit) {
        final int lim = Integer.parseInt(limit);
        return jdbcTemplate.queryForList(GET_SCORES, lim);
    }
}
