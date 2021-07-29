package org.slyrack.telegrambots.flags;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.function.Function;

public enum TextTarget implements Function<Update, Optional<String>> {

    NONE(update -> Optional.empty()),

    MESSAGE_TEXT(update -> update.hasMessage() ?
            Optional.ofNullable(update.getMessage().getText()) : Optional.empty()),

    REPLY_MESSAGE_TEXT(update -> (update.hasMessage() && update.getMessage().isReply() &&
            update.getMessage().getReplyToMessage().hasText()) ?
            Optional.ofNullable(update.getMessage().getReplyToMessage().getText()) : Optional.empty()),

    EDITED_MESSAGE_TEXT(update -> update.hasEditedMessage() ?
            Optional.ofNullable(update.getEditedMessage().getText()) : Optional.empty()),

    CALLBACK_QUERY_DATA(update -> update.hasCallbackQuery() ?
            Optional.ofNullable(update.getCallbackQuery().getData()) : Optional.empty());


    private final Function<Update, Optional<String>> function;

    TextTarget(final Function<Update, Optional<String>> function) {
        this.function = function;
    }

    @Override
    public Optional<String> apply(final Update update) {
        return function.apply(update);
    }
}
