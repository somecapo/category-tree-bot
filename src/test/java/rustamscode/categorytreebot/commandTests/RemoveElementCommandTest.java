package rustamscode.categorytreebot.commandTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rustamscode.categorytreebot.command.RemoveElementCommand;
import rustamscode.categorytreebot.repository.CategoryRepository;
import rustamscode.categorytreebot.entity.Category;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RemoveElementCommandTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    RemoveElementCommand removeElementCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteInvalidFormat() {
        // Проверка некорректного количества аргументов
        String[] args = {"/removeElement"};

        String result = removeElementCommand.execute(args);

        assertEquals("Неверный формат команды. Используйте: /removeElement <название>", result);
    }

    @Test
    void testExecuteCategoryNotFound() {
        // Проверка удаления несуществующей категории
        String[] args = {"/removeElement", "NonExistingCategory"};

        when(categoryRepository.findByName("NonExistingCategory")).thenReturn(Optional.empty());

        String result = removeElementCommand.execute(args);

        assertEquals("Категория 'NonExistingCategory' не найдена.", result);
        verify(categoryRepository, never()).delete(any());
    }

    @Test
    void testExecuteCategoryDeletedSuccessfully() {
        // Проверка успешного удаления существующей категории
        String[] args = {"/removeElement", "ExistingCategory"};
        Category category = new Category("ExistingCategory", null);

        when(categoryRepository.findByName("ExistingCategory")).thenReturn(Optional.of(category));

        String result = removeElementCommand.execute(args);

        assertEquals("Категория 'ExistingCategory' удалена.", result);
        verify(categoryRepository).delete(category);
    }
}
