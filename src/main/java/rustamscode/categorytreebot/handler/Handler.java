package rustamscode.categorytreebot.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.telegram.Bot;

public interface Handler {
    BotApiMethod<?> answer(Message message, Bot bot);
}
