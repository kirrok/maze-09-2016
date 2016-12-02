package ru.mail.park.websockets;

/**
 * Created by kirrok on 26.11.16.
 */

public class HandleException extends Exception {
    public HandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleException(String message) {
        super(message);
    }
}
