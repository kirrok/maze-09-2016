package ru.mail.park.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by kirrok on 01.12.16.
 */
public class Coords {

    public Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @JsonProperty("x")
    public final double x;
    @JsonProperty("y")
    public final double y;

    @NotNull
    public Coords add(@NotNull Coords addition) {
        return new Coords(x + addition.x, y + addition.y);
    }
}
