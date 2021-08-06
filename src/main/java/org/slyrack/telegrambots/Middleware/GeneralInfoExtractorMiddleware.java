package org.slyrack.telegrambots.Middleware;

import org.slyrack.telegrambots.annotations.MiddleHandler;
import org.slyrack.telegrambots.session.Session;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * extract:
 * <ul>
 *     <li>chat-id</li>
 *     <li>user</li>
 * </ul>
 * from telegram update
 */
public class GeneralInfoExtractorMiddleware {

    @MiddleHandler
    public void configureSession(final Update update, final Session session) {
        if (session == null)
            return;

        if (!session.containsAttribute(SupportAttribute.CHAT_ID.attributeName))
            UpdateInfoExtractor.getChatId(update)
                    .ifPresent(chatId -> session.setAttribute(SupportAttribute.CHAT_ID.attributeName, String.valueOf(chatId)));

        if (!session.containsAttribute(SupportAttribute.USER.attributeName))
            UpdateInfoExtractor.getUser(update)
                    .ifPresent(user -> session.setAttribute(SupportAttribute.USER.attributeName, user));
    }

    public enum SupportAttribute{
        CHAT_ID("chat-id"),
        USER("user");

        final String attributeName;

        SupportAttribute(String attributeName){
            this.attributeName = attributeName;
        }
    }

}
