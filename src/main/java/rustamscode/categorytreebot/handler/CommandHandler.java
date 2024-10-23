package rustamscode.categorytreebot.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import rustamscode.categorytreebot.telegram.Bot;

@Service
public class CommandHandler {
    public BotApiMethod answer(Message message, Bot bot) {
        String command = message.getText();
        switch (command){
            case "/start" -> {
                return start(message);
            }
        }
        return null;
    }

    private BotApiMethod start(Message message){
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        ПРИВЕТ ЭТО БОТ 
                        welcooooooome
                        """)
                .build();
    }
}
