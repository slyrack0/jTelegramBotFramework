package org.slyrack.telegrambots;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("slyrack")
public class SlyrackProperties {
    private boolean enableStateManagement;
    private boolean enableSessionManagement;
    private long sessionTtlMillis = 600000;
}
