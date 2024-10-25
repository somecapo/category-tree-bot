package rustamscode.categorytreebot.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rustamscode.categorytreebot.model.Category;
import rustamscode.categorytreebot.repository.CategoryRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExcelService {

    final CategoryRepository categoryRepository;

    @Autowired
    public ExcelService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Метод для создания Excel-файла с деревом категорий
    public byte[] createCategoryTreeExcel() throws IOException {
        List<Category> categories = categoryRepository.findAll();

        // Создание новой рабочей книги
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Category Tree");

        // Строка заголовков
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Category Name");
        header.createCell(1).setCellValue("Parent Category");

        // Заполняем данные о категориях
        int rowNum = 1;
        for (Category category : categories) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(category.getName());
            row.createCell(1).setCellValue(category.getParent() != null ? category.getParent().getName() : "Root");
        }

        // Записываем данные в байтовый массив
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    public void uploadCategoriesFromExcel(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String categoryName = row.getCell(0).getStringCellValue();
            String parentName = row.getCell(1).getStringCellValue();

            Category parent = null;
            if (!parentName.equals("Root")) {
                parent = categoryRepository.findByName(parentName).orElse(null);
            }
            Category category = new Category(categoryName, parent);
            categoryRepository.save(category);
        }
        workbook.close();
    }
}

