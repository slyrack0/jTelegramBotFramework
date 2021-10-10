package org.slyrack.telegrambots.annotations;

import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.session.Session;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation mark method to allow accept updates,
 * used for middleware
  *
 * Also middleware method receive this params:
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
@Component
public @interface MiddleHandler {
}
