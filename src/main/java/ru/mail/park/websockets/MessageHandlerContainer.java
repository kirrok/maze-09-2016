package ru.mail.park.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.park.models.Id;
import ru.mail.park.models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kirrok on 26.11.16.
 */
public class MessageHandlerContainer {

    private Map<Class<?>, MessageHandler<?>> handlers = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerContainer.class);

    public void handle(Message message, Id<User> forUser) throws HandleException {
        final Class messageType;

        try {
            messageType = Class.forName(message.getType());
        } catch (ClassNotFoundException e) {
            throw new HandleException("Can't handle message of " + message.getType() + " type", e);
        }
        final MessageHandler<?> handler = handlers.get(messageType);

        if (handler == null) {
            throw new HandleException("no handler for message of " + message.getType() + " type");
        }
        handler.handle(message, forUser);
        LOGGER.debug("message handled: type =[" + message.getType() + "], content=[" + message.getContent() + ']');
    }

    public <T> void registerHandler(Class<T> messageType, MessageHandler<T> handlerType) {
        handlers.put(messageType, handlerType);
    }
}
