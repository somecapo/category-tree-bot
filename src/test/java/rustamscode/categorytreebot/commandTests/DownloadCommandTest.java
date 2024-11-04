package rustamscode.categorytreebot.commandTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rustamscode.categorytreebot.command.DownloadCommand;
import rustamscode.categorytreebot.service.ExcelService;
import rustamscode.categorytreebot.telegram.Bot;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DownloadCommandTest {
    @Mock
    ExcelService excelService;

    @Mock
    Bot bot;

    @InjectMocks
    DownloadCommand downloadCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteSuccess() throws IOException, TelegramApiException {
        // Мокируем создание Excel-файла
        byte[] excelFile = {1, 2, 3, 4};  // Пример содержимого файла
        when(excelService.createCategoryTreeExcel()).thenReturn(excelFile);

        // Аргументы: args[0] - команда, args[1] - chatId
        String[] args = {"/download", "12345"};

        // Выполняем команду
        String result = downloadCommand.execute(args);

        // Проверка вызова excelService и bot.execute
        verify(excelService).createCategoryTreeExcel();
        verify(bot).execute(any(SendDocument.class));

        // Проверка результата
        assertEquals("Файл с деревом категорий отправлен!", result);
    }

    @Test
    void testExecuteIOException() throws IOException, TelegramApiException {
        // Мокируем IOException при создании Excel-файла
        when(excelService.createCategoryTreeExcel()).thenThrow(new IOException("Ошибка создания файла"));

        // Аргументы: args[0] - команда, args[1] - chatId
        String[] args = {"/download", "12345"};

        // Выполняем команду
        String result = downloadCommand.execute(args);

        // Проверка, что бот не пытался отправить файл
        verify(bot, never()).execute(any(SendDocument.class));

        // Проверка результата
        assertEquals("Ошибка при генерации или отправке файла!", result);
    }

    @Test
    void testExecuteTelegramApiException() throws IOException, TelegramApiException {
        // Мокируем успешное создание Excel-файла
        byte[] excelFile = {1, 2, 3, 4};
        when(excelService.createCategoryTreeExcel()).thenReturn(excelFile);

        // Мокируем исключение TelegramApiException при отправке документа
        doThrow(new TelegramApiException("Ошибка отправки")).when(bot).execute(any(SendDocument.class));

        // Аргументы: args[0] - команда, args[1] - chatId
        String[] args = {"/download", "12345"};

        // Выполняем команду
        String result = downloadCommand.execute(args);

        // Проверка, что excelService.createCategoryTreeExcel() был вызван
        verify(excelService).createCategoryTreeExcel();

        // Проверка результата
        assertEquals("Ошибка при генерации или отправке файла!", result);
    }


}
