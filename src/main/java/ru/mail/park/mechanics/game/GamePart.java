package ru.mail.park.mechanics.game;

/**
 * Created by kirrok on 02.12.16.
 */
public interface GamePart {
    Snap<? extends GamePart> takeSnap();
}
