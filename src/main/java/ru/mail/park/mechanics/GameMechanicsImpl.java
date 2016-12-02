package ru.mail.park.mechanics;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.park.services.AccountService;

/**
 * Created by kirrok on 01.12.16.
 */
@Service
public class GameMechanicsImpl {
    private static final Logger LOGGER = LogManager.getLogger(GameMechanicsImpl.class);
    private AccountService accountService;
}
