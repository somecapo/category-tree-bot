package rustamscode.categorytreebot.commands;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rustamscode.categorytreebot.repository.CategoryRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoveElementCommand implements BotCommand {

    final CategoryRepository categoryRepository;

    @Autowired
    public RemoveElementCommand(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2) {
            return "Неверный формат команды. Используйте: /removeElement <название>";
        }

        String elementName = args[1];
        return categoryRepository.findByName(elementName)
                .map(category -> {
                    categoryRepository.delete(category);
                    return "Категория '" + elementName + "' удалена.";
                })
                .orElse("Категория '" + elementName + "' не найдена.");
    }
}
