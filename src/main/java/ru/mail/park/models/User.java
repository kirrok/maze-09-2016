package ru.mail.park.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Solovyev on 17/09/16.
 */
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
    public String toString() {

        return login + " " + password + " " + Integer.toString(maxScore ) + " " + Long.toString(id);
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
}
