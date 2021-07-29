package org.slyrack.telegrambots.state;

import org.slyrack.telegrambots.Model;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * This interface need to storage current state and his model
 */
public interface StateManager {

    /**
     * Overrides old value.
     * @param userId userId to save state
     * @param name state name
     * @param model state model
     */
    void saveState(final long userId, @NonNull final String name, @NonNull final Model model);
    void removeState(final long userId);

    @Nullable
    State getState(final long userId);
}
