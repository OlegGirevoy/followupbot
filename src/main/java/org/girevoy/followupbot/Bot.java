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
        this.properties = loadProperties();
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getFrom().getFirstName() + ": " +
                update.getMessage().getText());

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        sendMessage.setText("Hello " + update.getMessage().getFrom().getFirstName() + "\n" +
                update.getMessage().getText());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        properties = loadProperties();
        return properties.getProperty("username");
    }

    @Override
    public String getBotToken() {
        properties = loadProperties();
        return properties.getProperty("token");
    }

    public Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream input = Bot.class.getClassLoader().getResourceAsStream("telegram.properties")) {

            if (input == null) {
                throw new NoSuchFileException("No such file");
            }

            prop = new Properties();

            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }
}
