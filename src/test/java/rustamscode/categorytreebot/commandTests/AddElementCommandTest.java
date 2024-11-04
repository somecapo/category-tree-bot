package rustamscode.categorytreebot.commandTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rustamscode.categorytreebot.command.AddElementCommand;
import rustamscode.categorytreebot.entity.Category;
import rustamscode.categorytreebot.repository.CategoryRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddElementCommandTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    AddElementCommand addElementCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRootCategorySuccessfully() {
        String[] args = {"/addElement", "Electronics"};

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());

        String result = addElementCommand.execute(args);

        assertEquals("Корневая категория 'Electronics' добавлена.", result);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void testAddRootCategoryAlreadyExists() {
        String[] args = {"/addElement", "Electronics"};

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(new Category("Electronics", null)));

        String result = addElementCommand.execute(args);

        assertEquals("Корневая категория Electronics уже существует", result);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testAddChildCategorySuccessfully() {
        String[] args = {"/addElement", "Electronics", "Laptop"};
        Category parentCategory = new Category("Electronics", null);

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.findByName("Laptop")).thenReturn(Optional.empty());

        String result = addElementCommand.execute(args);

        assertEquals("Дочерняя категория 'Laptop' добавлена к 'Electronics'.", result);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void testAddChildCategoryParentNotFound() {
        String[] args = {"/addElement", "NonExistingParent", "Laptop"};

        when(categoryRepository.findByName("NonExistingParent")).thenReturn(Optional.empty());

        String result = addElementCommand.execute(args);

        assertEquals("Родительская категория 'NonExistingParent' не найдена.", result);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testInvalidArguments() {
        String[] args = {"/addElement"};

        String result = addElementCommand.execute(args);

        assertEquals("Неверный формат команды. Используйте: /addElement <родитель> <дочерний элемент>", result);
        verify(categoryRepository, never()).save(any(Category.class));
    }


}
