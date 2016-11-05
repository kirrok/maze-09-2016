package ru.mail.park.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by kirork on 17/09/16.
 */
@SuppressWarnings("OverlyComplexBooleanExpression")
public class User {
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private int maxScore;
    private long id;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final User user = (User)obj;

        return user.id == this.id &&
                Objects.equals(user.login, this.login) &&
                user.maxScore == this.maxScore &&
                Objects.equals(user.password, this.password);
    }
}
