package rustamscode.categorytreebot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.commands.*;
import rustamscode.categorytreebot.repository.CategoryRepository;
import rustamscode.categorytreebot.telegram.Bot;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandHandler {
    final CategoryRepository categoryRepository;
    BotCommand botCommand;

    @Autowired
    public CommandHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public BotApiMethod<?> answer(Message message) {
        String[] args = getArgs(message);
        String command = args[0];
        switch (command) {
            case "/start" -> botCommand = new StartCommand();
            case "/viewTree" -> botCommand = new ViewTreeCommand(categoryRepository);
            case "/addElement" -> botCommand = new AddElementCommand(categoryRepository);
            case "/removeElement" -> botCommand = new RemoveElementCommand(categoryRepository);
            case "/help" -> botCommand = new HelpCommand();
            default -> {
                log.info("No command found");
                return SendMessage.builder()
                        .chatId(message.getChatId())
                        .text("Такой команды не сущетвует ❌")
                        .build();
            }
        }
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(botCommand.execute(args))
                .build();
    }

    private String[] getArgs(Message message) {
        return message.getText().split(" ");
    }

}
