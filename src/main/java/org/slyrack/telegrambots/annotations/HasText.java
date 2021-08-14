package org.slyrack.telegrambots.annotations;

import org.slyrack.telegrambots.flags.TextTarget;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation allow to receive only commands containing text that meets one of the conditions:
 * <ul>
 *     <li>{@link HasText#equals()}</li>
 *     <li>{@link HasText#equalsIgnoreCase()}</li>
 *     <li>{@link HasText#contains()}</li>
 *     <li>{@link HasText#startsWith()}</li>
 *     <li>{@link HasText#endsWith()}</li>
 * </ul>
 *
 * Text for conditions will be giving from {@link TextTarget#apply(Update)}
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasText {
    TextTarget textTarget();

    /**
     * @see {@link String#equals(Object)}
     */
    String equals() default "";

    /**
     * @see {@link String#equalsIgnoreCase(String)}
     */
    String equalsIgnoreCase() default "";

    /**
     * @see {@link String#contains(CharSequence)}
     */
    String contains() default "";

    /**
     * @see {@link String#startsWith(String)}
     */
    String startsWith() default "";

    /**
     * @see {@link String#endsWith(String)}
     */
    String endsWith() default "";
}
