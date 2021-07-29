package org.slyrack.telegrambots;

import org.slyrack.telegrambots.core.UpdateHandler;
import org.slyrack.telegrambots.core.holders.CommandHolder;
import org.slyrack.telegrambots.core.holders.MiddleHandlerHolder;
import org.slyrack.telegrambots.core.holders.ViewHolder;
import org.slyrack.telegrambots.session.SessionIdGenerator;
import org.slyrack.telegrambots.session.SessionManager;
import org.slyrack.telegrambots.state.StateManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class UpdateHandlerConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public UpdateHandler updateHandler(final List<CommandHolder> commands,
                                       final List<ViewHolder> views,
                                       final List<MiddleHandlerHolder> middleHandlers,
                                       final SlyrackProperties properties,
                                       final ObjectProvider<StateManager> stateManagerProvider,
                                       final ObjectProvider<SessionIdGenerator> sessionIdGeneratorProvider,
                                       final ObjectProvider<SessionManager> sessionManagerProvider) {

        return new UpdateHandler(
                commands,
                views.stream()
                        .collect(Collectors.toMap(ViewHolder::getViewName, Function.identity())),
                middleHandlers,
                properties.isEnableStateManagement(),
                properties.isEnableSessionManagement(),
                stateManagerProvider.getIfAvailable(),
                sessionIdGeneratorProvider.getIfAvailable(),
                sessionManagerProvider.getIfAvailable()
        );
    }
}
