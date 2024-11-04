package rustamscode.categorytreebot.serviceTests;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rustamscode.categorytreebot.entity.Category;
import rustamscode.categorytreebot.repository.CategoryRepository;
import rustamscode.categorytreebot.service.ExcelService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcelServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ExcelService excelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategoryTreeExcel() throws IOException {
        // Подготавливаем тестовые данные
        Category rootCategory = new Category("Electronics", null);
        Category subCategory = new Category("Laptops", rootCategory);
        when(categoryRepository.findAll()).thenReturn(List.of(rootCategory, subCategory));

        // Выполняем метод
        byte[] excelData = excelService.createCategoryTreeExcel();

        // Проверяем созданный Excel-файл
        ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Category Tree");

        // Проверяем заголовки
        Row headerRow = sheet.getRow(0);
        assertEquals("Category Name", headerRow.getCell(0).getStringCellValue());
        assertEquals("Parent Category", headerRow.getCell(1).getStringCellValue());

        // Проверяем данные категорий
        Row row1 = sheet.getRow(1);
        assertEquals("Electronics", row1.getCell(0).getStringCellValue());
        assertEquals("Root", row1.getCell(1).getStringCellValue());

        Row row2 = sheet.getRow(2);
        assertEquals("Laptops", row2.getCell(0).getStringCellValue());
        assertEquals("Electronics", row2.getCell(1).getStringCellValue());

        workbook.close();
    }
}
