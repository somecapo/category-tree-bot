package rustamscode.categorytreebot.telegram;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rustamscode.categorytreebot.service.UpdateDispatcher;

import java.util.ArrayList;
import java.util.List;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Bot extends TelegramWebhookBot {

    final TelegramProperties telegramProperties;
    final UpdateDispatcher updateDispatcher;

    @Autowired
    public Bot(TelegramProperties telegramProperties, UpdateDispatcher updateDispatcher) {
        super(telegramProperties.getToken());
        this.telegramProperties = telegramProperties;
        this.updateDispatcher = updateDispatcher;
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "главное меню"));
        commandList.add(new BotCommand("/viewTree", "просмотреть дерево"));
        commandList.add(new BotCommand("/addElement", "добавить элемент"));
        commandList.add(new BotCommand("/removeElement", "удалить элемент"));
        commandList.add(new BotCommand("/help", "список команд"));

        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.info("Command menu error: " + e.getMessage());
        }
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return updateDispatcher.distribute(update, this);
    }

    @Override
    public String getBotPath() {
        return telegramProperties.getPath();
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getUsername();
    }
}
