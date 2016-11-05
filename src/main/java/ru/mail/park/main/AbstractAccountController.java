package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mail.park.services.AccountService;

/**
 * Created by kirrok on 05.11.16.
 */

public abstract class AbstractAccountController {
    @Autowired
    protected AccountService accountService;
}