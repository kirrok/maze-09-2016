package ru.mail.park.services;

import ru.mail.park.dataSets.UserDataSet;

/**
 * Created by kirrok on 21.10.16.
 */
public interface AccountService {
    Long addUser(UserDataSet user);

    void deleteUser(long id);

    void updateUser(UserDataSet user);

    UserDataSet getUserByLogin(String email);

    UserDataSet getUserById(long id);

}
