package rustamscode.categorytreebot.command;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rustamscode.categorytreebot.service.ExcelService;
import rustamscode.categorytreebot.telegram.Bot;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DownloadCommand implements BotCommand {
    final ExcelService excelService;
    final Bot bot;

    @Autowired
    public DownloadCommand(ExcelService excelService, Bot bot) {
        this.excelService = excelService;
        this.bot = bot;
    }

    @Override
    public String execute(String[] args) {
        try {
            byte[] excelFile = excelService.createCategoryTreeExcel();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelFile);

            // Отправляем документ пользователю
            InputFile inputFile = new InputFile(inputStream, "categories.xlsx");
            SendDocument sendDocument = new SendDocument();
            sendDocument.setDocument(inputFile);
            sendDocument.setChatId(args[1]);  // Chat ID передается для отправки файла

            bot.execute(sendDocument);
            return "Файл с деревом категорий отправлен!";
        } catch (IOException | TelegramApiException e) {
            log.info("File download error: {}", e.getMessage());
            return "Ошибка при генерации или отправке файла!";
        }
    }
}
