package ru.mail.park.mechanics.game;

import ru.mail.park.models.Id;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kirrok on 02.12.16.
 */
public abstract class GameObject {
    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    private Map<Class<?>, GamePart> parts = new HashMap<>();
    private Id<GameObject> id;

    public GameObject() {
        this.id = Id.of(ID_GENERATOR.getAndIncrement());
    }


    public <T extends GamePart> void addPart(Class<T> clazz, T gamePart ){
        parts.put(clazz, gamePart);
    }
}
