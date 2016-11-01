package ru.mail.park.dataSets;

/**
 * Created by Solovyev on 17/09/16.
 */
public class UserDataSet {
    private String login;
    private String password;
    private int maxScore;
    private long id;

    public UserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
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
