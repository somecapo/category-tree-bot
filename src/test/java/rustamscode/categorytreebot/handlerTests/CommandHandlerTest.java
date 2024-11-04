package rustamscode.categorytreebot.handlerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.entity.Category;
import rustamscode.categorytreebot.handler.CommandHandler;
import rustamscode.categorytreebot.repository.CategoryRepository;
import rustamscode.categorytreebot.service.ExcelService;
import rustamscode.categorytreebot.telegram.Bot;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommandHandlerTest {
    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ExcelService excelService;

    @Mock
    Bot bot;

    @InjectMocks
    CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewTreeCommand() {
        Message message = mock(Message.class);
        when(message.getText()).thenReturn("/viewTree");
        when(message.getChatId()).thenReturn(12345L);
        when(categoryRepository.findAll()).thenReturn(List.of()); // Пустое дерево

        SendMessage response = (SendMessage) commandHandler.answer(message, bot);

        assertEquals("Дерево категорий:\n", response.getText());
        assertEquals("12345", response.getChatId());
    }

    @Test
    void testAddElementCommand() {
        Message message = mock(Message.class);
        when(message.getText()).thenReturn("/addElement Electronics");
        when(message.getChatId()).thenReturn(12345L);
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());

        SendMessage response = (SendMessage) commandHandler.answer(message, bot);

        assertEquals("Корневая категория 'Electronics' добавлена.", response.getText());
        assertEquals("12345", response.getChatId());
    }

    @Test
    void testRemoveElementCommand() {
        Message message = mock(Message.class);
        when(message.getText()).thenReturn("/removeElement Electronics");
        when(message.getChatId()).thenReturn(12345L);
        Category category = new Category("Electronics", null);
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(category));

        SendMessage response = (SendMessage) commandHandler.answer(message, bot);

        assertEquals("Категория 'Electronics' удалена.", response.getText());
        assertEquals("12345", response.getChatId());
    }

    @Test
    void testUnknownCommand() {
        Message message = mock(Message.class);
        when(message.getText()).thenReturn("/unknownCommand");
        when(message.getChatId()).thenReturn(12345L);

        SendMessage response = (SendMessage) commandHandler.answer(message, bot);

        assertEquals("Такой команды не сущетвует ❌", response.getText());
        assertEquals("12345", response.getChatId());
    }
}
