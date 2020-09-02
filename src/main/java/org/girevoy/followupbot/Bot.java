package org.girevoy.followupbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {
    Properties properties;

    public Bot() {
        super();
        loadProperties();
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getFrom().getFirstName() + ": " +
                update.getMessage().getText());

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        sendMessage.setText(String.format("Hello %s %s%n%s", update.getMessage().getFrom().getFirstName(),
                                                             update.getMessage().getFrom().getLastName(),
                                                             update.getMessage().getText()));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getProperty("username");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("token");
    }

    public void loadProperties() {
        properties = new Properties();
        try (InputStream input = Bot.class.getClassLoader().getResourceAsStream("telegram.properties")) {

            if (input == null) {
                throw new NoSuchFileException("No such file");
            }

            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
