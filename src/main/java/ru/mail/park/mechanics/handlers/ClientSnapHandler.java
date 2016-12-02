package ru.mail.park.mechanics.handlers;

import org.springframework.stereotype.Component;
import ru.mail.park.mechanics.GameMechanics;
import ru.mail.park.mechanics.base.ClientSnap;
import ru.mail.park.models.Id;
import ru.mail.park.models.User;
import ru.mail.park.websockets.MessageHandler;
import ru.mail.park.websockets.MessageHandlerContainer;

import javax.annotation.PostConstruct;

/**
 * Created by kirrok on 26.11.16.
 */

//@Component
public class ClientSnapHandler extends MessageHandler<ClientSnap> {
    private MessageHandlerContainer messageHandlerContainer;

    private GameMechanics gameMechanics;

    public ClientSnapHandler(Class<ClientSnap> clazz) {
        super(clazz);
    }

    @PostConstruct
    private void init() {

    }


    @Override
    public void handle(ClientSnap message, Id<User> forUser) {

    }
}
