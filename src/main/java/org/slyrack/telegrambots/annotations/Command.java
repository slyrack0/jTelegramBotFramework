package org.slyrack.telegrambots.annotations;

import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.flags.UpdateType;
import org.slyrack.telegrambots.session.Session;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation mark method to allow accept updates with selected {@link UpdateType},
 * also can be filter by state and be exclusive.
 *
 * Also command method receive this params:
 *
 * <ul>
 *     <li>Update - current update</li>
 *     <li>{@link AbsSender}</li>
 *     <li>{@link Session} - current session if enabled session management</li>
 *     <li>{@link Model} - model of current state if state management enabled and previous state is present</li>
 *     <li>{@link ModelAtr}</li>
 *     <li>{@link SessionAtr}</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command {
    String STATELESS = "stateless";
    String NO_STATE = "no-state";

    /**
     * Which state must have this command to be handled.
     * Must be unique state name.
     * <p>
     * Reserved states:
     * - stateless: invokes in any state
     * - no-state: invokes only if no have state
     */
    String[] state() default NO_STATE;

    /**
     * Update type to handle for this command.
     */
    UpdateType[] value();

    /**
     * If this command contains in suitable list - will be executed only it.
     * Must be 1 exclusive command in suitable list
     */
    boolean exclusive() default false;
}
