package org.slyrack.telegrambots.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation allow to define middleware
 * declared method can have params with type:
 * <ul>
 *     <li>Update</li>
 *     <li>AbsSender</li>
 *     <li>Session</li>
 *     <li>Model</li>
 *     <li>Params annotated with {@link ModelAtr}</li>
 *     <li>Params annotated with {@link SessionAtr}</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Component
public @interface MiddleHandler {
}
