package org.slyrack.telegrambots;

import org.slyrack.telegrambots.session.ChatIdSessionIdGenerator;
import org.slyrack.telegrambots.session.InMemorySessionManager;
import org.slyrack.telegrambots.session.SessionIdGenerator;
import org.slyrack.telegrambots.session.SessionManager;
import org.slyrack.telegrambots.state.InMemoryStateManager;
import org.slyrack.telegrambots.state.StateManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SlyrackProperties.class)
public class DefaultConfig {

    @Bean
    @ConditionalOnProperty(value = "slyrack.enableStateManagement", havingValue = "true")
    @ConditionalOnMissingBean(value = StateManager.class)
    public StateManager stateManager() {
        return new InMemoryStateManager();
    }

    @Bean
    @ConditionalOnProperty(value = "slyrack.enableSessionManagement", havingValue = "true")
    @ConditionalOnMissingBean(value = SessionIdGenerator.class)
    public SessionIdGenerator sessionIdGenerator() {
        return new ChatIdSessionIdGenerator();
    }

    @Bean
    @ConditionalOnProperty(value = "slyrack.enableSessionManagement", havingValue = "true")
    @ConditionalOnMissingBean(value = SessionManager.class)
    public SessionManager sessionManager(final SlyrackProperties properties) {
        return new InMemorySessionManager(properties.getSessionTtlMillis());
    }
}
