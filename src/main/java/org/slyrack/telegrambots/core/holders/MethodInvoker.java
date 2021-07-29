package org.slyrack.telegrambots.core.holders;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.slyrack.telegrambots.Model;
import org.slyrack.telegrambots.annotations.ModelAtr;
import org.slyrack.telegrambots.annotations.SessionAtr;
import org.slyrack.telegrambots.session.Session;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public abstract class MethodInvoker<T> {

    @NonNull
    private final Class<T> invokeReturnType;
    @NonNull
    private final Object methodHolder;
    @NonNull
    private final Method methodToCall;

    protected MethodInvoker(@NonNull final Class<T> invokeReturnType,
                            @NonNull final Object methodHolder,
                            @NonNull final Method methodToCall) {
        this.invokeReturnType = invokeReturnType;
        this.methodHolder = methodHolder;
        this.methodToCall = methodToCall;
    }


    @SneakyThrows
    protected final Optional<T> invoke(final Object[] params) {
        try {
            final Object callResult = methodToCall.invoke(methodHolder, params);
            if (callResult == null)
                return Optional.empty();

            if (invokeReturnType.isInstance(callResult))
                return Optional.of(invokeReturnType.cast(callResult));

            return Optional.empty();
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    protected final Object[] prepareParams(@Nullable final Session session,
                                           @Nullable final Model model,
                                           final Object... passedParams) {

        final Class<?>[] parameterTypes = methodToCall.getParameterTypes();
        final Object[] params = new Object[parameterTypes.length];

        final Annotation[][] parameterAnnotations = methodToCall.getParameterAnnotations();
        for (int i = 0; i < parameterTypes.length; i++) {
            final Optional<SessionAtr> sessionAtr = getSessionAtr(parameterAnnotations[i]);
            if (sessionAtr.isPresent()) {
                if (session == null)
                    continue;

                params[i] = session.getAttribute(sessionAtr.get().value());
                continue;
            }
            final Optional<ModelAtr> modelAtr = getModelAtr(parameterAnnotations[i]);
            if (modelAtr.isPresent()) {
                if (model == null)
                    continue;

                params[i] = model.getAttribute(modelAtr.get().value());
                continue;
            }

            final Class<?> type = parameterTypes[i];
            if (type.isInstance(session)) {
                params[i] = session;
                continue;
            }
            if (type.isInstance(model)) {
                params[i] = model;
                continue;
            }

            for (final Object param : passedParams)
                if (type.isInstance(param))
                    params[i] = param;
        }

        return params;
    }

    private static Optional<SessionAtr> getSessionAtr(final Annotation[] paramAnnotations) {
        for (final Annotation paramAnnotation : paramAnnotations)
            if (paramAnnotation instanceof SessionAtr)
                return Optional.of((SessionAtr) paramAnnotation);

        return Optional.empty();
    }

    private static Optional<ModelAtr> getModelAtr(final Annotation[] paramAnnotations) {
        for (final Annotation paramAnnotation : paramAnnotations)
            if (paramAnnotation instanceof ModelAtr)
                return Optional.of((ModelAtr) paramAnnotation);

        return Optional.empty();
    }
}
