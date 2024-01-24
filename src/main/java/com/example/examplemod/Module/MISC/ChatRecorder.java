package com.example.examplemod.Module.MISC;

import com.example.examplemod.Module.Module;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.examplemod.Client.DIRECTORY_PATH;

public class ChatRecorder extends Module {
    private boolean isEnabled = false;
    private File logFile;

    // Директория для сохранения файлов


    public ChatRecorder() {
        super("ChatRecorder", Keyboard.KEY_NONE, Category.MISC);
        logFile = new File(DIRECTORY_PATH, "chat_log.txt");
        // Убедитесь, что директория существует
        new File(DIRECTORY_PATH).mkdirs();
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (isEnabled) {
            ITextComponent component = event.getMessage();

            // Проверяем, является ли сообщение игровым чатом (от других игроков)
            if (component instanceof TextComponentString) {
                String message = component.getUnformattedText();
                String sender = component.getSiblings().get(0).getFormattedText(); // Получаем отправителя из первого sibling

                // Проверяем, не является ли отправитель игроком
                if (!sender.equals(mc.player.getDisplayName().getFormattedText())) {
                    // Проверяем, не содержит ли сообщение запрещенные строки
                    if (!message.startsWith("Your bed is located at") &&
                        !message.contains("Want to support 9b9t? Donations greatly appreciated.") &&
                        !message.contains("Want a custom MOTD? Check out 9b9t.com") &&
                        !message.contains("Did you know you can lock down your 9b9t account with 2FA?") &&
                        !message.contains("Check out the 9b9t subreddit")) {
                        saveMessageToLogFile(message);
                    }
                }
            }
        }
    }


    private void saveMessageToLogFile(String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(getCurrentTimestamp() + " " + message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "[" + dateFormat.format(new Date()) + "]";
    }

    @Override
    public void onEnable() {
        super.onEnable();
        isEnabled = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        isEnabled = false;
    }
}
