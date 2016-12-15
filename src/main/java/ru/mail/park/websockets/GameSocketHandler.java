package ru.mail.park.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.mail.park.models.Id;
import ru.mail.park.models.User;
import ru.mail.park.services.AccountService;

import javax.naming.AuthenticationException;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Created by kirrok on 25.11.16.
 */

public class GameSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameSocketHandler.class);

    @NotNull
    private AccountService accountService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final RemotePointService remotePointService;
    @NotNull
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GameSocketHandler(AccountService accountService, MessageHandlerContainer messageHandlerContainer, RemotePointService remotePointService) {
        this.accountService = accountService;
        this.messageHandlerContainer = messageHandlerContainer;
        this.remotePointService = remotePointService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws AuthenticationException {
        LOGGER.info("handleTextMessage");
        final Long userId = (Long) session.getAttributes().get("userId");
        final User user;
        if (userId == null || (user = accountService.getUserById(Id.of(userId))) == null) {
            throw new AuthenticationException("Only authenticated users allowed to play a game");
        }
        handleMessage(user, message);
    }

    private void handleMessage(User userProfile, TextMessage text) {
        LOGGER.info("handleMessage");
        final Message message;
        try {
            message = objectMapper.readValue(text.getPayload(), Message.class);
        } catch (IOException ex) {
            LOGGER.error("wrong json format at ping response", ex);
            return;
        }
        try {
            //noinspection ConstantConditions
            messageHandlerContainer.handle(message, userProfile.getId());
        } catch (HandleException e) {
            LOGGER.error("Can't handle message of type " + message.getType() + " with content: " + message.getContent(), e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.error("Transport error: ", exception);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws AuthenticationException {
        LOGGER.info("Connection established, session : {}", session.getId());
        final Long id = (Long) session.getAttributes().get("userId");

        if (id == null || accountService.getUserById(Id.of(id)) == null) {
            LOGGER.warn("throwing exception !");
            throw new AuthenticationException("Only authenticated users allowed to play a game");
        }
        final Id<User> userId = Id.of(id);
        remotePointService.registerUser(userId, session);
    }
}
