package org.slyrack.telegrambots.session;

import org.slyrack.telegrambots.AttributeMap;
import org.springframework.lang.NonNull;

public interface Session extends AttributeMap {
    @NonNull
    String id();
    boolean isAlive();
    void stop();
    void touch();
}
