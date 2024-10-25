package rustamscode.categorytreebot.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rustamscode.categorytreebot.handler.CommandHandler;
import rustamscode.categorytreebot.handler.FileHandler;
import rustamscode.categorytreebot.telegram.Bot;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UpdateDispatcher {
    final CommandHandler commandHandler;
    final FileHandler fileHandler;

    @Autowired
    public UpdateDispatcher(CommandHandler commandHandler,
                            FileHandler fileHandler) {
        this.commandHandler = commandHandler;
        this.fileHandler = fileHandler;
    }


    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                if (message.getText().startsWith("/")) {
                    return commandHandler.answer(message, bot);
                }
            } else if (message.hasDocument()) {
                return fileHandler.answer(message, bot);
            }
        }
        log.info("Unsupported operation: {}", update);
        return null;
    }
}
