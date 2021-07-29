package org.slyrack.telegrambots.flags;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Predicate;

public enum UpdateType implements Predicate<Update> {
    ALL(update -> true),
    MESSAGE(Update::hasMessage),
    EDITED_MESSAGE(Update::hasEditedMessage),
    CHANNEL_POST(Update::hasChannelPost),
    EDITED_CHANNEL_POST(Update::hasEditedChannelPost),
    INLINE_QUERY(Update::hasInlineQuery),
    CHOSEN_INLINE_RESULT(Update::hasChosenInlineQuery),
    CALLBACK_QUERY(Update::hasCallbackQuery),
    SHIPPING_QUERY(Update::hasShippingQuery),
    PRE_CHECKOUT_QUERY(Update::hasPreCheckoutQuery),
    POLL(Update::hasPoll),
    POLL_ANSWER(Update::hasPollAnswer),
    MY_CHAT_MEMBER(Update::hasMyChatMember),
    CHAT_MEMBER(Update::hasChatMember);


    private final Predicate<Update> predicate;

    UpdateType(final Predicate<Update> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(final Update update) {
        return this.predicate.test(update);
    }
}
