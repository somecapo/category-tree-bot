package rustamscode.categorytreebot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.telegram.Bot;

@Service
@Slf4j
public class CommandHandler {

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();
        switch (command) {
            case "/start" -> {
                return start(message);
            }
            case "/viewTree" -> {
                return viewTree(message);
            }
            case "/addElement" -> {
                return addElement(message);
            }
            case "/removeElement" -> {
                return removeElement(message);
            }
            case "/help" -> {
                return help(message);
            }
            default -> {
                log.info("No command found");
                return null;
            }
        }
    }

    private BotApiMethod<?> start(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        Привет! Я бот для управления деревом категорий 🌳
                        
                        Вот что я могу сделать для тебя:
                        
                        /viewTree — Показать дерево категорий в структурированном виде.
                        
                        /addElement <название элемента> — Добавить корневой элемент в дерево, если он не имеет родителя.
                        
                        /addElement <родительский элемент> <дочерний элемент> — Добавить дочерний элемент к существующему родительскому элементу. Если родительский элемент не найден, я сообщу об этом.
                        
                        /removeElement <название элемента> — Удалить элемент и всех его потомков. Если элемент не найден, я также сообщу об этом.
                        
                        /help — Показать список всех доступных команд и краткое их описание.
                        
                        Используй команды, чтобы управлять деревом категорий. Начни с добавления корневого элемента!
                        """)
                .build();
    }

    private BotApiMethod<?> viewTree(Message message) {
        return null;
    }

    private BotApiMethod<?> addElement(Message message) {
        return null;
    }

    private BotApiMethod<?> removeElement(Message message) {
        return null;
    }

    private BotApiMethod<?> help(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        Вот что я могу сделать для тебя:
                        
                        /viewTree — Показать дерево категорий в структурированном виде.
                        
                        /addElement <название элемента> — Добавить корневой элемент в дерево, если он не имеет родителя.
                        
                        /addElement <родительский элемент> <дочерний элемент> — Добавить дочерний элемент к существующему родительскому элементу. Если родительский элемент не найден, я сообщу об этом.
                        
                        /removeElement <название элемента> — Удалить элемент и всех его потомков. Если элемент не найден, я также сообщу об этом.
                        
                        /help — Показать список всех доступных команд и краткое их описание.
                        
                        Используй команды, чтобы управлять деревом категорий. Начни с добавления корневого элемента!
                        """)
                .build();
    }
}
