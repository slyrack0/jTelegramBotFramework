package org.slyrack.telegrambots.Middleware;

import org.slyrack.telegrambots.flags.UpdateType;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public class UpdateInfoExtractor {

    public static Optional<User> getUser(final Update update) {
        if (update == null) return Optional.empty();

        if (UpdateType.MESSAGE.test(update))
            return Optional.ofNullable(update.getMessage().getFrom());
        else if (UpdateType.CALLBACK_QUERY.test(update))
            return Optional.ofNullable(update.getCallbackQuery().getFrom());
        else if (UpdateType.INLINE_QUERY.test(update))
            return Optional.of(update.getInlineQuery().getFrom());
        else if (UpdateType.CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getChannelPost().getFrom());
        else if (UpdateType.EDITED_CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getEditedChannelPost().getFrom());
        else if (UpdateType.EDITED_MESSAGE.test(update))
            return Optional.ofNullable(update.getEditedMessage().getFrom());
        else if (UpdateType.CHOSEN_INLINE_RESULT.test(update))
            return Optional.of(update.getChosenInlineQuery().getFrom());
        else if (UpdateType.SHIPPING_QUERY.test(update))
            return Optional.ofNullable(update.getShippingQuery().getFrom());
        else if (UpdateType.PRE_CHECKOUT_QUERY.test(update))
            return Optional.ofNullable(update.getPreCheckoutQuery().getFrom());
        else if (UpdateType.POLL_ANSWER.test(update))
            return Optional.ofNullable(update.getPollAnswer().getUser());
        else
            return Optional.empty();
    }

    public static Optional<Long> getChatId(final Update update) {
        if (update == null) return Optional.empty();

        if (UpdateType.MESSAGE.test(update))
            return Optional.ofNullable(update.getMessage().getChatId());
        else if (UpdateType.CALLBACK_QUERY.test(update))
            return Optional.ofNullable(update.getCallbackQuery().getMessage().getChatId());
        else if (UpdateType.INLINE_QUERY.test(update))
            return Optional.of(update.getInlineQuery().getFrom().getId());
        else if (UpdateType.CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getChannelPost().getChatId());
        else if (UpdateType.EDITED_CHANNEL_POST.test(update))
            return Optional.ofNullable(update.getEditedChannelPost().getChatId());
        else if (UpdateType.EDITED_MESSAGE.test(update))
            return Optional.ofNullable(update.getEditedMessage().getChatId());
        else if (UpdateType.CHOSEN_INLINE_RESULT.test(update))
            return Optional.of(update.getChosenInlineQuery().getFrom().getId());
        else if (UpdateType.SHIPPING_QUERY.test(update))
            return Optional.of(update.getShippingQuery().getFrom().getId());
        else if (UpdateType.PRE_CHECKOUT_QUERY.test(update))
            return Optional.of(update.getPreCheckoutQuery().getFrom().getId());
        else if (UpdateType.POLL_ANSWER.test(update))
            return Optional.of(update.getPollAnswer().getUser().getId());
        else
            return Optional.empty();
    }

}
