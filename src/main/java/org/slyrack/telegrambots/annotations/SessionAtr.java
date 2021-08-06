package org.slyrack.telegrambots.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation allow to receive session attribute as parameter of {@link MiddleHandler}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface SessionAtr {
    String value();
}
