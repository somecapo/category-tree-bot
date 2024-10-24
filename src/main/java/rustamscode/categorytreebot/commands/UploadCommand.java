/*
package rustamscode.categorytreebot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rustamscode.categorytreebot.service.ExcelService;

import java.io.InputStream;

@Component
public class UploadCommand implements BotCommand {

    private final ExcelService excelService;

    @Autowired
    public UploadCommand(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Override
    public String execute(String[] args) {
        return "Отправьте Excel файл для загрузки категорий.";
    }

    // Метод для обработки загруженного файла
    public String handleFile(Message message) {
        try {
            InputFile document = message.getDocument();
            InputStream inputStream = message.getDocument().getFileId();

            // Чтение и загрузка категорий из Excel
            excelService.uploadCategoriesFromExcel(inputStream);
            return "Категории успешно загружены из файла!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при загрузке файла!";
        }
    }
}*/
