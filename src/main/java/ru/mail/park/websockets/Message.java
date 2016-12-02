package ru.mail.park.websockets;

import org.jetbrains.annotations.NotNull;

/**
 * Created by kirrok on 25.11.16.
 */
public class Message {
    @NotNull
    private String type;
    @NotNull
    private String content;
    //Here could be your versioning system
    //private int version = VERSION;

    @NotNull
    public String getType() {
        return type;
    }
    @NotNull
    public String getContent() {
        return content;
    }

    public Message() {
    }

    public Message(@NotNull String type, @NotNull String content) {
        this.type = type;
        this.content = content;
    }

    public Message(@NotNull Class clazz, @NotNull String content) {
        //noinspection ConstantConditions
        this(clazz.getName(), content);
    }
}
