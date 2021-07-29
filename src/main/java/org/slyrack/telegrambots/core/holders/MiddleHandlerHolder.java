package org.slyrack.telegrambots.core.holders;

import lombok.SneakyThrows;
import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.session.Session;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.reflect.Method;

public class MiddleHandlerHolder extends MethodInvoker<Void> {

    public MiddleHandlerHolder(final Object methodHolder, final Method methodToCall) {
        super(Void.TYPE, methodHolder, methodToCall);
    }

    @SneakyThrows
    public void handle(@NonNull final Update update,
                       @NonNull final AbsSender absSender,
                       @Nullable final Session session,
                       @Nullable final Model stateModel) {

        final Object[] params = prepareParams(
                session, stateModel, update, absSender
        );
        invoke(params);
    }
}
