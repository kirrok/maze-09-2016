package ru.mail.park.mechanics;

import ru.mail.park.models.Id;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kirrok on 01.12.16.
 */

public class GameSession {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private HashSet<Gamer> gamers;

    private final Id<GameSession> sessionId;

    public GameSession(Gamer gamer1, Gamer gamer2, Id<GameSession> sessionId) {
        gamers.add(gamer1);
        gamers.add(gamer2);
        this.sessionId = Id.of(ID_GENERATOR.getAndIncrement());
    }
}