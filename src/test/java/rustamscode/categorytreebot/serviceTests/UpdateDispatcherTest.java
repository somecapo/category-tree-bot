package rustamscode.categorytreebot.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rustamscode.categorytreebot.handler.CommandHandler;
import rustamscode.categorytreebot.handler.FileHandler;
import rustamscode.categorytreebot.service.UpdateDispatcher;
import rustamscode.categorytreebot.telegram.Bot;

public class UpdateDispatcherTest {

    @Mock
    private CommandHandler commandHandler;

    @Mock
    private FileHandler fileHandler;

    @InjectMocks
    private UpdateDispatcher updateDispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDistribute_WithUnsupportedOperation() {
        // Arrange
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Bot bot = mock(Bot.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(false);
        when(message.hasDocument()).thenReturn(false);

        // Act
        BotApiMethod<?> response = updateDispatcher.distribute(update, bot);

        // Assert
        assertNull(response);
        verify(commandHandler, never()).answer(any(), any());
        verify(fileHandler, never()).answer(any(), any());
    }

    @Test
    public void testDistribute_NoMessage() {
        // Arrange
        Update update = mock(Update.class);
        Bot bot = mock(Bot.class);
        when(update.hasMessage()).thenReturn(false);

        // Act
        BotApiMethod<?> response = updateDispatcher.distribute(update, bot);

        // Assert
        assertNull(response);
        verify(commandHandler, never()).answer(any(), any());
        verify(fileHandler, never()).answer(any(), any());
    }
}
