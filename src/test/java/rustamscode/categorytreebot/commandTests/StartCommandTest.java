package rustamscode.categorytreebot.commandTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rustamscode.categorytreebot.command.StartCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartCommandTest {
    private StartCommand startCommand;

    @BeforeEach
    void setUp() {
        startCommand = new StartCommand();
    }

    @Test
    void testExecuteReturnsWelcomeMessage() {
        // Ожидаемое сообщение
        String expectedMessage = """
                Привет! Я бот для управления деревом категорий 🌳
                
                Вот что я могу сделать для тебя:
                
                /viewTree — Показать дерево категорий в структурированном виде.
                
                /addElement <название элемента> — Добавить корневой элемент в дерево, если он не имеет родителя.
                
                /addElement <родительский элемент> <дочерний элемент> — Добавить дочерний элемент к существующему родительскому элементу. Если родительский элемент не найден, я сообщу об этом.
                
                /removeElement <название элемента> — Удалить элемент и всех его потомков. Если элемент не найден, я также сообщу об этом.
                
                /help — Показать список всех доступных команд и краткое их описание.
                
                /download — Вернуть Excel документ с деревом категорий.
                
                /upload — Принять ваш Excel документ с категориями и сохранить их в дереве.
                
                Используй команды, чтобы управлять деревом категорий. Начни с добавления корневого элемента!
                """;

        // Выполнение команды
        String result = startCommand.execute(new String[]{});

        // Проверка результата
        assertEquals(expectedMessage, result);
    }
}
