package ru.mail.park.services;

import ru.mail.park.models.User;

import java.util.List;
import java.util.Map;

/**
 * Created by kirrok on 21.10.16.
 */
public interface AccountService {

    Long addUser(User user);

    void deleteUser(Long user);

    void updateUser(User user);

    User getUserByLogin(String login);

    User getUserById(long id);

    List<Map<String, Object>> score(String limit);

    boolean passwordIsCorrect(User user, String password);
}
