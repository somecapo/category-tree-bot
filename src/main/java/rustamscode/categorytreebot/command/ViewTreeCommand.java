package rustamscode.categorytreebot.command;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rustamscode.categorytreebot.entity.Category;
import rustamscode.categorytreebot.repository.CategoryRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewTreeCommand implements BotCommand {

    final CategoryRepository categoryRepository;

    @Autowired
    public ViewTreeCommand(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder tree = new StringBuilder("Дерево категорий:\n");
        categoryRepository.findAll().stream()
                .filter(category -> category.getParent() == null)
                .forEach(category -> tree.append(printCategory(category, 0)));
        return tree.toString();
    }

    private String printCategory(Category category, int level) {
        StringBuilder sb = new StringBuilder(" ".repeat(level) + category.getName() + "\n");
        for (Category child : category.getChildren()) {
            sb.append(printCategory(child, level + 1));
        }
        return sb.toString();
    }
}