package ru.mail.park.mechanics;

import ru.mail.park.mechanics.base.Coords;
import ru.mail.park.models.Id;
import ru.mail.park.models.User;

/**
 * Created by kirrok on 01.12.16.
 */

public class Gamer {
    private User user;
    private Coords coords;

    public Gamer(User user, Coords coords) {
        this.user = user;
        this.coords = coords;
    }

    public Id<User> getId(){
        return user.getId();
    }

    public Coords getCoords() {
        return coords;
    }
}
