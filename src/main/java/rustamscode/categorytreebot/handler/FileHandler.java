package rustamscode.categorytreebot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.commands.*;
import rustamscode.categorytreebot.service.ExcelService;
import rustamscode.categorytreebot.telegram.Bot;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileHandler implements Handler {
    final ExcelService excelService;

    @Autowired
    public FileHandler(ExcelService excelService) {
        this.excelService = excelService;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        UploadCommand botCommand = new UploadCommand(excelService);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(botCommand.handleFile(message, bot))
                .build();

    }

}
