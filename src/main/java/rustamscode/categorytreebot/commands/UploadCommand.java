package rustamscode.categorytreebot.commands;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.service.ExcelService;
import rustamscode.categorytreebot.telegram.Bot;

import java.io.InputStream;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadCommand implements BotCommand {

    final ExcelService excelService;
    final static String DOC_FORMAT = ".xlsx";

    @Autowired
    public UploadCommand(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Override
    public String execute(String[] args) {
        return "Отправьте Excel файл для загрузки категорий.";
    }

    // Метод для обработки загруженного файла
    public String handleFile(Message message, Bot bot) {
        if (!message.getDocument().getFileName().endsWith(DOC_FORMAT)) {
            return "Формат Excel документа должен быть: " + DOC_FORMAT;
        }
        try {
            String fileId = message.getDocument().getFileId();
            File file = bot.execute(GetFile.builder()
                    .fileId(fileId)
                    .build());
            InputStream inputStream = bot.downloadFileAsStream(file);
            excelService.uploadCategoriesFromExcel(inputStream);
            return "Категории успешно загружены из файла!";
        } catch (NonUniqueResultException | IncorrectResultSizeDataAccessException e) {
            log.info("File contains already existing categories: {}", e.getMessage());
            return "Ваш файл содержит уже сущетсвующие категории";
        } catch (Exception e) {
            log.info("File upload error: {}", e.getMessage());
            return "Ошибка при загрузке файла!";
        }
    }
}
