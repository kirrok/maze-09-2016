package ru.mail.park.services;

import ru.mail.park.dataSets.UserDataSet;

import javax.servlet.http.HttpSession;

/**
 * Created by kirrok on 21.10.16.
 */
public interface SessionService {

    void addUser(HttpSession session, UserDataSet user);

    UserDataSet getUser(HttpSession session);

    void delUser(HttpSession session);
}
