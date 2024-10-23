package rustamscode.categorytreebot.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.telegram.Bot;

@Service
public class MessageHandler {
    public BotApiMethod answer(Message message, Bot bot){
        return null;
    }
}
