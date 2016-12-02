package ru.mail.park.mechanics.avatar;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.base.Coords;
import ru.mail.park.mechanics.base.Direction;
import ru.mail.park.mechanics.game.GamePart;
import ru.mail.park.mechanics.game.Snap;

/**
 * Created by kirrok on 02.12.16.
 */
public class PositionPart implements GamePart {

    private Coords coords;

    @Override
    public Snap<? extends GamePart> takeSnap() {
        return new PositionSnap(this);
    }

    private static final class PositionSnap implements Snap<PositionPart>{
        @NotNull
        private final Coords coords;

        PositionSnap(@NotNull PositionPart positionPart) {
            coords = positionPart.coords;
        }

        @NotNull
        public Coords getBody() {
            return coords;
        }
    }
}
