package org.slyrack.telegrambots.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * view used for annotated method that send view (presentation of any information or process)
 * to user by using {@link org.telegram.telegrambots.meta.bots.AbsSender}
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface View {
    /**
     * Name of view
     */
    String value();
}
