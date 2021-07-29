package org.slyrack.telegrambots.core.config;

import org.slyrack.telegrambots.annotations.Controller;
import org.slyrack.telegrambots.annotations.MiddleHandler;
import org.slyrack.telegrambots.core.holders.MiddleHandlerHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class MiddleHandlerConfig {
    @Bean
    public List<MiddleHandlerHolder> buildMiddleHandlers(ApplicationContext applicationContext) {
        final Map<String, Object> handlersMap = applicationContext.getBeansWithAnnotation(Controller.class);

        final List<MiddleHandlerHolder> handlers = new ArrayList<>();

        for (final Object obj : handlersMap.values()) {
            final Class<?> objClz = obj.getClass();
            for (final Method m : objClz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(MiddleHandler.class))
                    handlers.add(new MiddleHandlerHolder(obj, m));
            }
        }

        return handlers;
    }
}
