package ru.mail.park.repositories;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import ru.mail.park.dataSets.UserDataSet;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by kirrok on 21.10.16.
 */
@Repository
public class SessionDAO {
    private JdbcTemplate jdbcTemplate;

    public SessionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getUserId(HttpSession session) {
        final String SQL = "SELECT user_id FROM session WHERE session_id = ?;";
        return jdbcTemplate.queryForObject(SQL, Long.class, session.getId());
    }

    public void addUser(HttpSession session, UserDataSet user) {
        final String SQL =
                "INSERT INTO session (session_id, creation_time, last_accessed_time, user_id) VALUES (?, ? ,?, ?);";
        final Date dateCrTime = new Date(session.getCreationTime());
        final Timestamp timestampCreationTime = new Timestamp(dateCrTime.getTime());

        final Date dateAccessedTime = new Date(session.getLastAccessedTime());
        final Timestamp timestampAccessedTime = new Timestamp(dateAccessedTime.getTime());

        jdbcTemplate.update(SQL, session.getId(), timestampCreationTime, timestampAccessedTime, user.getId());
    }

    public void updateLastAccessedTime(HttpSession session) {
        final String SQL = "UPDATE session SET last_accessed_time = ? WHERE session_id = ?";
        final Date date = new Date(session.getLastAccessedTime());
        final Timestamp timestamp = new Timestamp(date.getTime());

        jdbcTemplate.update(SQL, timestamp, session.getId());
    }

    public void delUser(HttpSession session){
        final String sql = "DELETE FROM session WHERE session_id = ?";
        Util.println(session.getId());
        jdbcTemplate.update(sql, session.getId());
    }
}
