package org.slyrack.telegrambots.core.holders;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.ModelAndView;
import org.slyrack.telegrambots.session.Session;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.slyrack.telegrambots.annotations.Command.NO_STATE;
import static org.slyrack.telegrambots.annotations.Command.STATELESS;


public class CommandHolder extends MethodInvoker<ModelAndView> {
    @Getter
    private final Set<String> states;
    private final boolean stateless;
    private final List<Predicate<Update>> triggers;
    @Getter
    private final boolean exclusive;

    public CommandHolder(final String[] states,
                         final List<Predicate<Update>> triggers,
                         final Object methodHolder,
                         final Method methodToCall,
                         final boolean exclusive) {

        super(ModelAndView.class, methodHolder, methodToCall);


        this.states = new HashSet<>(Arrays.asList(states));
        this.stateless = isStateless(states);
        this.triggers = triggers;
        this.exclusive = exclusive;
    }


    public boolean stateCompatibility(String currentStateName) {
        if(stateless)
            return true;

        if (StringUtils.isBlank(currentStateName))
            currentStateName = NO_STATE;

        return states.contains(currentStateName);
    }

    public boolean canHandle(final Update update) {
        final List<Boolean> triggersResults = triggers.stream()
                .map(trigger -> trigger.test(update))
                .collect(Collectors.toList());

        // can handle only if have minimum 1 good result and 0 bad result
        return haveGoodResult(triggersResults) && notHaveBadResult(triggersResults);
    }

    @SneakyThrows
    public Optional<ModelAndView> handle(@NonNull final Update update,
                                         @NonNull final AbsSender absSender,
                                         @Nullable final Session session,
                                         @Nullable final Model stateModel) {

        final Object[] params = prepareParams(
                session, stateModel, update, absSender
        );
        return invoke(params);
    }


    private static boolean haveGoodResult(final List<Boolean> resultList) {
        return resultList.stream().anyMatch(aBoolean -> aBoolean);
    }

    private static boolean notHaveBadResult(final List<Boolean> resultList) {
        return resultList.stream().allMatch(aBoolean -> aBoolean);
    }

    private static boolean isStateless(final String[] states) {
        for (final String state : states)
            if (state.equals(STATELESS))
                return true;

        return false;
    }
}
