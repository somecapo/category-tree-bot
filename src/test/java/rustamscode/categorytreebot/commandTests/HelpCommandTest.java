package rustamscode.categorytreebot.commandTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rustamscode.categorytreebot.command.HelpCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpCommandTest {

    private HelpCommand helpCommand;

    @BeforeEach
    void setUp() {
        helpCommand = new HelpCommand();
    }

    @Test
    void testExecuteReturnsHelpMessage() {
        // Ожидаемое сообщение
        String expectedMessage = """
                Вот что я могу сделать для тебя:
                
                /viewTree — Показать дерево категорий в структурированном виде.
                
                /addElement <название элемента> — Добавить корневой элемент в дерево, если он не имеет родителя.
                
                /addElement <родительский элемент> <дочерний элемент> — Добавить дочерний элемент к существующему родительскому элементу. Если родительский элемент не найден, я сообщу об этом.
                
                /removeElement <название элемента> — Удалить элемент и всех его потомков. Если элемент не найден, я также сообщу об этом.
                
                /help — Показать список всех доступных команд и краткое их описание.
                
                Используй команды, чтобы управлять деревом категорий. Начни с добавления корневого элемента!
                """;

        // Выполняем команду
        String result = helpCommand.execute(new String[]{});

        // Проверяем, что результат соответствует ожидаемому сообщению
        assertEquals(expectedMessage, result);
    }
}
