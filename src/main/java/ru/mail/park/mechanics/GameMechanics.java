package ru.mail.park.mechanics;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.base.ClientSnap;
import ru.mail.park.models.Id;
import ru.mail.park.models.User;

/**
 * Created by kirrok on 01.12.16.
 */
public interface GameMechanics {
    void addClientSnapshot(@NotNull Id<User> userId, @NotNull ClientSnap clientSnap);

    void addUser(@NotNull Id<User> user);

    void gmStep(long frameTime);

}
