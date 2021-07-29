package org.slyrack.telegrambots.core.config;

import org.slyrack.telegrambots.annotations.View;
import org.slyrack.telegrambots.annotations.ViewController;
import org.slyrack.telegrambots.core.holders.ViewHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class ViewConfig {

    @Bean
    public List<ViewHolder> views(final ApplicationContext applicationContext) {
        final Map<String, Object> viewsMap = applicationContext.getBeansWithAnnotation(ViewController.class);
        
        final List<ViewHolder> views = new ArrayList<>();
        for (final Object obj : viewsMap.values()) {
            final Class<?> objClz = obj.getClass();
            for (final Method m : objClz.getDeclaredMethods()) {
                if(m.isAnnotationPresent(View.class)) {
                    final String viewName = m.getAnnotation(View.class).value();

                    views.add(new ViewHolder(viewName, obj, m));
                }
            }
        }

        return views;
    }
}
