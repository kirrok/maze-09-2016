package ru.mail.park.services.impl;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.Application;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.repositories.SessionDAO;
import ru.mail.park.repositories.UserDAO;
import ru.mail.park.services.SessionService;

import javax.servlet.http.HttpSession;


@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    SessionDAO sessionDAO;
    UserDAO userDAO;

    @Autowired
    public SessionServiceImpl(SessionDAO sessionDAO, UserDAO userDAO) {
        this.sessionDAO = sessionDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(HttpSession session, UserDataSet user) {
        try {
            sessionDAO.addUser(session, user);
        } catch (DuplicateKeyException e) {
            Application.logger.warn(e);
            sessionDAO.updateLastAccessedTime(session);
        }
    }

    @Nullable
    @Override
    public UserDataSet getUser(HttpSession session) {
        final long userId;
        try {
            userId = sessionDAO.getUserId(session);
        } catch (EmptyResultDataAccessException e) {
            Application.logger.warn(e);
            return null;
        }
        System.out.println("ID FROM getUser: " + userDAO.getUserById(userId).getId());
        return userDAO.getUserById(userId);
    }

    @Override
    public void delUser(HttpSession session) {
        try {
            sessionDAO.delUser(session);
        } catch (DuplicateKeyException e) {
            Application.logger.warn(e);
        }
    }
}

