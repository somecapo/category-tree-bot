package rustamscode.categorytreebot.commandTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rustamscode.categorytreebot.command.ViewTreeCommand;
import rustamscode.categorytreebot.entity.Category;
import rustamscode.categorytreebot.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ViewTreeCommandTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ViewTreeCommand viewTreeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteWithEmptyTree() {
        // Пустое дерево категорий
        when(categoryRepository.findAll()).thenReturn(List.of());

        String result = viewTreeCommand.execute(new String[]{});

        assertEquals("Дерево категорий:\n", result);
    }

    @Test
    void testExecuteWithSingleCategory() {
        // Дерево с одной корневой категорией
        Category root = new Category("Electronics", null);

        when(categoryRepository.findAll()).thenReturn(List.of(root));

        String result = viewTreeCommand.execute(new String[]{});

        assertEquals("Дерево категорий:\nElectronics\n", result);
    }

    @Test
    void testExecuteWithNestedCategories() {
        // Дерево с корневой категорией и несколькими уровнями вложенности
        Category root = new Category("Electronics", null);
        Category laptops = new Category("Laptops", root);
        Category phones = new Category("Phones", root);
        Category apple = new Category("Apple", phones);
        Category samsung = new Category("Samsung", phones);

        // Устанавливаем дочерние категории
        root.setChildren(List.of(laptops, phones));
        phones.setChildren(List.of(apple, samsung));

        when(categoryRepository.findAll()).thenReturn(List.of(root));

        String result = viewTreeCommand.execute(new String[]{});

        String expectedOutput = """
                Дерево категорий:
                Electronics
                 Laptops
                 Phones
                  Apple
                  Samsung
                """;

        assertEquals(expectedOutput, result);
    }
}
