package org.slyrack.telegrambots.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("slyrack.default-bot")
public class DefaultBotProperties {
    private boolean disabled;
    private String username;
    private String token;
}
