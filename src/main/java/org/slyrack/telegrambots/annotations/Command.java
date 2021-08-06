package org.slyrack.telegrambots.annotations;

import org.slyrack.telegrambots.flags.UpdateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation allow to handle telegram text commands (like: /start, /help) and callbacks
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
