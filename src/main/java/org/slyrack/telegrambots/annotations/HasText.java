package org.slyrack.telegrambots.annotations;

import org.slyrack.telegrambots.flags.TextTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasText {
    TextTarget textTarget();

    String equals() default "";

    String equalsIgnoreCase() default "";

    String contains() default "";

    String startsWith() default "";

    String endsWith() default "";
}
