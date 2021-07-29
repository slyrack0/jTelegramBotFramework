package org.slyrack.telegrambots.session;

import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface SessionIdGenerator {
    Optional<String> generateId(@NonNull final Update update);
}
