package org.slyrack.telegrambots.bot;

import org.slyrack.telegrambots.core.UpdateHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DefaultBotProperties.class)
public class DefaultBotConfig {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnProperty(
            prefix = "slyrack.default-bot",
            value = "disabled",
            havingValue = "false",
            matchIfMissing = true
    )
    public DefaultBot defaultBot(final UpdateHandler updateHandler, final DefaultBotProperties properties) {
        return new DefaultBot(updateHandler, properties);
    }
}
