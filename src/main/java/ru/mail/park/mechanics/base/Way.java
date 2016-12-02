package ru.mail.park.mechanics.base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by kirrok on 01.12.16.
 */
public enum Way {
    Left("left"),
    Right("Right"),
    Up("Up"),
    Down("Down"),
    None("None");

    @NotNull
    private String direction;

    Way(@NotNull String direction) {
        this.direction = direction;
    }
}