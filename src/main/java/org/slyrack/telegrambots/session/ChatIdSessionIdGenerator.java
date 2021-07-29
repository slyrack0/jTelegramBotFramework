package org.slyrack.telegrambots.session;

import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class ChatIdSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Optional<String> generateId(@NonNull final Update update) {
        return getMessage(update)
                .map(ChatIdSessionIdGenerator::idFromMessage);
    }

    private static Optional<Message> getMessage(final Update update) {
        final Message message;
        if (update.hasMessage())
            message = update.getMessage();
        else if(update.hasEditedMessage())
            message = update.getEditedMessage();
        else if (update.hasCallbackQuery())
            message = update.getCallbackQuery().getMessage();
        else
            message = null;

        return Optional.ofNullable(message);
    }

    private static String idFromMessage(final Message message) {
        return String.valueOf(message.getChatId());
    }
}
