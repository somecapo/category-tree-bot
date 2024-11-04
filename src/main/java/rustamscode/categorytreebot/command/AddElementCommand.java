package rustamscode.categorytreebot.command;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rustamscode.categorytreebot.entity.Category;
import rustamscode.categorytreebot.repository.CategoryRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddElementCommand implements BotCommand {

    final CategoryRepository categoryRepository;

    @Autowired
    public AddElementCommand(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String execute(String[] args) {
        if (args.length == 2) {
            // Добавляем корневой элемент
            if (categoryRepository.findByName(args[1]).isPresent()) {
                return "Корневая категория " + args[1] + " уже существует";
            }
            Category category = new Category(args[1], null);
            categoryRepository.save(category);
            return "Корневая категория '" + args[1] + "' добавлена.";
        } else if (args.length == 3) {
            // Добавляем дочерний элемент
            String parentName = args[1];
            String childName = args[2];
            return categoryRepository.findByName(parentName)
                    .map(parent -> {
                        if (categoryRepository.findByName(childName).isPresent()) {
                            return "Категория " + childName + " уже существует";
                        }
                        Category child = new Category(childName, parent);
                        categoryRepository.save(child);
                        return "Дочерняя категория '" + childName + "' добавлена к '" + parentName + "'.";
                    })
                    .orElse("Родительская категория '" + parentName + "' не найдена.");
        }
        return "Неверный формат команды. Используйте: /addElement <родитель> <дочерний элемент>";
    }
}