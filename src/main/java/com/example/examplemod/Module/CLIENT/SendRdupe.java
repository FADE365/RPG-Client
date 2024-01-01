package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SendRdupe extends Module {
// Сообщение, которое будет отправлено в чат
//    private static final String AHK_SCRIPT_PATH = "C:\\Users\\user\\Desktop\\MineMods\\RPGClient\\src\\main\\resources\\assets\\minecraft\\rdupe.ahk"; // Замените на путь к вашему AHK скрипту

    public SendRdupe() {
        super("SendRdupe", Keyboard.KEY_NONE, Category.CLIENT);
    }

    @Override
    public void onEnable() {
        if (!Panic.isPanic) {
            // Вызываем AHK скрипт
            try {
                // Создаем объект Robot
                Robot robot = new Robot();

                // Задаем задержку между действиями (в миллисекундах)
                int delay = 1000;

                // Нажимаем клавишу T (открыть чат)
                robot.keyPress(KeyEvent.VK_T);
                robot.keyRelease(KeyEvent.VK_T);

                // Задержка
                robot.delay(delay);

                // Вводим текст ".rdupe"
                typeText(robot, ".rdupe");

            } catch (Exception e) {
                e.printStackTrace();
            }


            super.onEnable();
            toggle();
        }
    }

    private static void typeText(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                throw new RuntimeException("Невозможно найти код клавиши для символа: " + c);
            }
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }
}
