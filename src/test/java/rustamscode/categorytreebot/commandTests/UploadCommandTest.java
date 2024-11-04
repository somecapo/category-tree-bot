package rustamscode.categorytreebot.commandTests;

import jakarta.persistence.NonUniqueResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.File;
import rustamscode.categorytreebot.service.ExcelService;
import rustamscode.categorytreebot.telegram.Bot;
import rustamscode.categorytreebot.command.UploadCommand;


import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UploadCommandTest {

    @Mock
    ExcelService excelService;

    @Mock
    Bot bot;

    @InjectMocks
    UploadCommand uploadCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteReturnsInstructionMessage() {
        // Проверка, что метод execute возвращает инструкцию
        String result = uploadCommand.execute(new String[]{});
        assertEquals("Отправьте Excel файл для загрузки категорий.", result);
    }

    @Test
    void testHandleFileIncorrectFormat() {
        // Мокаем сообщение с документом неправильного формата
        Message message = mock(Message.class);
        Document document = mock(Document.class);

        when(message.getDocument()).thenReturn(document);
        when(document.getFileName()).thenReturn("file.pdf"); // неправильный формат

        String result = uploadCommand.handleFile(message, bot);

        assertEquals("Формат Excel документа должен быть: .xlsx", result);
        verifyNoInteractions(excelService); // Убеждаемся, что excelService не вызывается
    }

    @Test
    void testHandleFileUploadSuccess() throws Exception {
        // Мокаем сообщение с документом правильного формата
        Message message = mock(Message.class);
        Document document = mock(Document.class);
        File telegramFile = mock(File.class);
        InputStream inputStream = mock(InputStream.class);

        when(message.getDocument()).thenReturn(document);
        when(document.getFileName()).thenReturn("categories.xlsx"); // правильный формат
        when(document.getFileId()).thenReturn("fileId123");

        when(bot.execute(any(GetFile.class))).thenReturn(telegramFile);
        when(bot.downloadFileAsStream(telegramFile)).thenReturn(inputStream);

        String result = uploadCommand.handleFile(message, bot);

        assertEquals("Категории успешно загружены из файла!", result);
        verify(excelService).uploadCategoriesFromExcel(inputStream);
    }

    @Test
    void testHandleFileGeneralException() throws Exception {
        // Мокаем сообщение с документом правильного формата
        Message message = mock(Message.class);
        Document document = mock(Document.class);
        File telegramFile = mock(File.class);
        InputStream inputStream = mock(InputStream.class);

        when(message.getDocument()).thenReturn(document);
        when(document.getFileName()).thenReturn("categories.xlsx");
        when(document.getFileId()).thenReturn("fileId123");

        when(bot.execute(any(GetFile.class))).thenReturn(telegramFile);
        when(bot.downloadFileAsStream(telegramFile)).thenReturn(inputStream);

        // Мокаем общее исключение при вызове uploadCategoriesFromExcel
        doThrow(new RuntimeException("Unexpected error")).when(excelService).uploadCategoriesFromExcel(inputStream);

        String result = uploadCommand.handleFile(message, bot);

        assertEquals("Ошибка при загрузке файла!", result);
    }
}
