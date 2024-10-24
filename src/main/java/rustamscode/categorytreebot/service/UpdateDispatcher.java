package rustamscode.categorytreebot.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rustamscode.categorytreebot.handler.CallbackQueryHandler;
import rustamscode.categorytreebot.handler.CommandHandler;
import rustamscode.categorytreebot.handler.FileHandler;
import rustamscode.categorytreebot.handler.MessageHandler;
import rustamscode.categorytreebot.telegram.Bot;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UpdateDispatcher {
    final CallbackQueryHandler callbackQueryHandler;
    final CommandHandler commandHandler;
    final MessageHandler messageHandler;
    final FileHandler fileHandler;

    @Autowired
    public UpdateDispatcher(CallbackQueryHandler callbackQueryHandler,
                            CommandHandler commandHandler,
                            MessageHandler messageHandler, FileHandler fileHandler) {
        this.callbackQueryHandler = callbackQueryHandler;
        this.commandHandler = commandHandler;
        this.messageHandler = messageHandler;
        this.fileHandler = fileHandler;
    }


    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                if (message.getText().startsWith("/")) {
                    return commandHandler.answer(message);
                } else {
                    return messageHandler.answer(message);
                }
            } else if (message.hasDocument()) {
                return fileHandler.answer(message);
            }
        }
        log.info("Unsupported operation: " + update);
        return null;
    }
}
