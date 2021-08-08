package org.slyrack.telegrambots.bot;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slyrack.telegrambots.core.UpdateHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class DefaultBot extends TelegramLongPollingBot {
    private final UpdateHandler updateHandler;
    private final DefaultBotProperties properties;

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(final Update update) {
        updateHandler.handleUpdate(update,this);
    }
}
