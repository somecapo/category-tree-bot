package rustamscode.categorytreebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rustamscode.categorytreebot.telegram.TelegramProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramProperties.class)
public class CategoryTreeBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryTreeBotApplication.class, args);
    }

}
