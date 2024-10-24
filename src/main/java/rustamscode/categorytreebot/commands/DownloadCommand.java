/*
package rustamscode.categorytreebot.commands;

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
public class DownloadCommand implements BotCommand{

    private final ExcelService excelService;
    private final Bot categoryBot;

    @Autowired
    public DownloadCommand(ExcelService excelService, Bot categoryBot) {
        this.excelService = excelService;
        this.categoryBot = categoryBot;
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
            sendDocument.setChatId(args[0]);  // Chat ID передается для отправки файла

            categoryBot.execute(sendDocument);
            return "Файл с деревом категорий отправлен!";
        } catch (IOException | TelegramApiException e) {
            e.printStackTrace();
            return "Ошибка при генерации или отправке файла!";
        }
    }
}
*/
