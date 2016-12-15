package ru.mail.park.mechanics.handlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mail.park.mechanics.requests.JoinGame;
import ru.mail.park.models.Id;
import ru.mail.park.models.User;
import ru.mail.park.websockets.MessageHandler;
import ru.mail.park.websockets.MessageHandlerContainer;

import javax.annotation.PostConstruct;

/**
 * Created by kirrok on 02.12.16.
 */
@Component
public class JoinGameHandler extends MessageHandler<JoinGame.Request> {
    private Logger logger = LogManager.getLogger(JoinGameHandler.class);

    private MessageHandlerContainer messageHandlerContainer;

    public JoinGameHandler(@NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinGame.Request.class);
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGame.Request.class, this);
    }

    @Override
    public void handle(@NotNull JoinGame.Request message, @NotNull Id<User> forUser) {
        logger.info("Handled message :\"JoinGame\"}");
    }
}
