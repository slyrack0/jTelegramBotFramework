package org.slyrack.telegrambots.state;

import lombok.NonNull;
import org.slyrack.telegrambots.Model;
import org.springframework.lang.Nullable;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStateManager implements StateManager {

    private final ConcurrentHashMap<Long, State> stateModelMap;

    public InMemoryStateManager() {
        this.stateModelMap = new ConcurrentHashMap<>();
    }


    @Override
    public void saveState(final long userId, @NonNull final String name, @NonNull final Model model) {
        stateModelMap.put(userId, new SimpleState(userId, name, model));
    }

    @Override
    public void removeState(final long userId) {
        stateModelMap.remove(userId);
    }

    @Nullable
    @Override
    public State getState(final long userId) {
        return stateModelMap.get(userId);
    }
}
