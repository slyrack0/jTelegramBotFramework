package org.slyrack.telegrambots.core.holders;

import lombok.Getter;
import lombok.SneakyThrows;
import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.session.Session;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.reflect.Method;

public class ViewHolder extends MethodInvoker<Void> {
    @Getter
    private final String viewName;

    public ViewHolder(final String viewName,
                      final Object methodHolder,
                      final Method methodToCall) {

        super(Void.TYPE, methodHolder, methodToCall);
        this.viewName = viewName;
    }


    @SneakyThrows
    public void handle(@NonNull final Update update,
                       @NonNull final AbsSender absSender,
                       @Nullable final Session session,
                       @Nullable final Model model) {

        final Object[] params = prepareParams(session, model, update, absSender);
        invoke(params);
    }
}
