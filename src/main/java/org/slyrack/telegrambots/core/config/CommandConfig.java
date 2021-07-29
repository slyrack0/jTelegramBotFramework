package org.slyrack.telegrambots.core.config;

import org.slyrack.telegrambots.annotations.Command;
import org.slyrack.telegrambots.annotations.Controller;
import org.slyrack.telegrambots.annotations.HasText;
import org.slyrack.telegrambots.core.holders.CommandHolder;
import org.slyrack.telegrambots.flags.TextTarget;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

@Configuration
public class CommandConfig {
    @Bean
    public List<CommandHolder> commands(final ApplicationContext applicationContext) {
        final Map<String, Object> controllersMap = applicationContext.getBeansWithAnnotation(Controller.class);

        final List<CommandHolder> commandHolders = new ArrayList<>();
        for (final Object obj : controllersMap.values()) {
            final Class<?> objClz = obj.getClass();
            for (final Method m : objClz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Command.class)) {
                    final Command commandAnnotation = m.getAnnotation(Command.class);

                    final String[] states = commandAnnotation.state();
                    final boolean exclusive = commandAnnotation.exclusive();
                    final List<Predicate<Update>> triggers = new ArrayList<>(Arrays.asList(commandAnnotation.value()));
                    if (m.isAnnotationPresent(HasText.class))
                        triggers.add(buildHasTextTrigger(m.getAnnotation(HasText.class)));

                    commandHolders.add(new CommandHolder(states, triggers, obj, m, exclusive));
                }
            }
        }

        return commandHolders;
    }


    private static Predicate<Update> buildHasTextTrigger(final HasText hasText) {
        final TextTarget textTarget = hasText.textTarget();
        final AtomicReference<Predicate<String>> textPredicate = new AtomicReference<>(null);

        final String equals = hasText.equals();
        if (notBlank(equals))
            setAndThrowIfNotNull(textPredicate, line -> line.equals(equals));

        final String equalsIgnoreCase = hasText.equalsIgnoreCase();
        if (notBlank(equalsIgnoreCase))
            setAndThrowIfNotNull(textPredicate, line -> line.equalsIgnoreCase(equalsIgnoreCase));

        final String contains = hasText.contains();
        if (notBlank(contains))
            setAndThrowIfNotNull(textPredicate, line -> line.contains(contains));

        final String startsWith = hasText.startsWith();
        if (notBlank(startsWith))
            setAndThrowIfNotNull(textPredicate, line -> line.startsWith(startsWith));

        final String endsWith = hasText.endsWith();
        if (notBlank(endsWith))
            setAndThrowIfNotNull(textPredicate, line -> line.endsWith(endsWith));


        final Predicate<String> stringPredicate = textPredicate.get();
        if (stringPredicate == null)
            throw new IllegalArgumentException("HasText annotation must have one trigger. Passed 0.");


        return update -> {
            final Optional<String> strOpt = textTarget.apply(update);
            if (strOpt.isEmpty())
                return false;

            return stringPredicate.test(strOpt.get());
        };
    }

    private static boolean notBlank(final String str) {
        return !str.isBlank();
    }

    private static void setAndThrowIfNotNull(final AtomicReference<Predicate<String>> reference,
                                             final Predicate<String> textPredicate) {

        reference.getAndAccumulate(textPredicate, (prev, current) -> {
            if (prev != null)
                throw new IllegalArgumentException("HasText annotation may have only one text trigger");

            return current;
        });
    }
}
