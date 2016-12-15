package ru.mail.park.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.mail.park.Application;
import ru.mail.park.models.Id;
import ru.mail.park.models.User;

import java.io.IOException;

/**
 * Created by kirrok on 26.11.16.
 */
public abstract class MessageHandler<T> {

    private static final Logger LOGGER = LogManager.getLogger(MessageHandler.class);
    private final Class<T> clazz;

    public MessageHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void handle(Message message, Id<User> forUser) throws HandleException {
        LOGGER.info("abstract message handler HANDLE");

        try {
            final Object data = new ObjectMapper().readValue(message.getType(), clazz);
            handle(clazz.cast(data), forUser);
        } catch (IOException | ClassCastException ex) {
            throw new HandleException("Can't read incoming message of type " + message.getType() +
                    " with content: " + message.getContent(), ex);
        }
    }

    public abstract void handle(T message , Id<User> forUser );
}
