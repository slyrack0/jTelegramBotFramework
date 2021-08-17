package org.slyrack.telegrambots.annotations;

import org.slyrack.telegrambots.ModelAndView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Takes attribute from current model and place it to marked parameter.
 * Can be null.
 * This annotation can be used to mark the parameter in few methods with different behavior:
 * <ul>
 *     <li>{@link Controller} - if use this annotation in controller method - model can be present by current user state,
 *     and this attribute can be taken from it.</li>
 *     <li>{@link MiddleHandler} - similarly to controller</li>
 *     <li>{@link View} - in this case model presents by Controller return ({@link ModelAndView}).</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ModelAtr {
    String value();
}
