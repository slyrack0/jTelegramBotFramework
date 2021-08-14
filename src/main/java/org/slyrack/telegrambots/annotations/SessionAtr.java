package org.slyrack.telegrambots.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Takes attribute from current session and place it to marked parameter.
 * <lu>
 *     <li>If session management disabled - always null</li>
 *     <li>If session not present - null.</li>
 *     <li>If attribute not fount - null.</li>
 * </lu>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface SessionAtr {
    String value();
}
