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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
@Transactional
public class SessionServiceImpl implements SessionService {
//    SessionDAO sessionDAO;
    UserDAO userDAO;

    ConcurrentMap<HttpSession, Long> sessionToUser = new ConcurrentHashMap<>();

    @Autowired
    public SessionServiceImpl(UserDAO userDAO) {
//        this.sessionDAO = sessionDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(HttpSession session, UserDataSet user) {
        sessionToUser.put(session, user.getId());
//        try {
//            sessionDAO.addUser(session, user);
//        } catch (DuplicateKeyException e) {
//            Application.logger.warn(e);
//            sessionDAO.updateLastAccessedTime(session);
//        }
    }

    @Nullable
    @Override
    public UserDataSet getUser(HttpSession session) {
//        final long userId;
//        try {
//            userId = sessionDAO.getUserId(session);
//        } catch (EmptyResultDataAccessException e) {
//            Application.logger.warn(e);
//            return null;
//        }
        final long userId = sessionToUser.get(session);

        return userDAO.getUserById(userId);
    }

    @Override
    public void delUser(HttpSession session) {
//        try {
//            sessionDAO.delUser(session);
//        } catch (DuplicateKeyException e) {
//            Application.logger.warn(e);
//        }

        sessionToUser.remove(session);
    }
}

