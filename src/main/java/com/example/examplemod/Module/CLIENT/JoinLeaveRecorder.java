package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.lwjgl.input.Keyboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JoinLeaveRecorder extends Module {
    private final File logFile;
    private final Map<String, Long> joinTimes; // Время входа игроков
    private final Set<String> currentPlayers;  // Текущие игроки на сервере
    private Map<String, List<String>> playerLogEntries;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public JoinLeaveRecorder() {
        super("JoinLeaveRecorder", Keyboard.KEY_NONE, Category.MISC);
        logFile = new File("LOG_JOIN_LEAVE.txt");
        joinTimes = new HashMap<>();
        currentPlayers = new HashSet<>();
        playerLogEntries = new HashMap<>();
    }

    public void fetchPlayerListAndLog() {
        Minecraft mc = Minecraft.getMinecraft();
        NetHandlerPlayClient connection = mc.getConnection();
        if (connection != null) {
            Collection<NetworkPlayerInfo> playerInfoMap = connection.getPlayerInfoMap();
            Set<String> newPlayers = new HashSet<>();

            for (NetworkPlayerInfo info : playerInfoMap) {
                String name = info.getGameProfile().getName();
                newPlayers.add(name);

                // Если игрок появился впервые, записываем время входа и логируем
                if (!currentPlayers.contains(name)) {
                    joinTimes.put(name, System.currentTimeMillis());
                    saveLog(name, "joined", 0); // Логируем появление игрока
                }
            }

            // Проверяем каждого игрока, который был в последнем списке, но отсутствует теперь
            for (String oldPlayer : currentPlayers) {
                if (!newPlayers.contains(oldPlayer)) {
                    // Записываем время выхода и общее время пребывания
                    Long joinTime = joinTimes.remove(oldPlayer);
                    if (joinTime != null) {
                        long duration = System.currentTimeMillis() - joinTime;
                        saveLog(oldPlayer, "left", duration); // Логируем уход игрока
                    }
                }
            }

            // Обновляем текущий список игроков
            currentPlayers.clear();
            currentPlayers.addAll(newPlayers);
        }
    }


    private void saveLog(String playerName, String action, long duration) {
        String durationFormatted = formatDuration(duration);
        // Формирование записи лога
        String logEntry = String.format("[%s] %s - Duration: %s\n",
                getCurrentTimestamp(), action, durationFormatted);
        // Добавление записи в лог игрока
        List<String> entries = playerLogEntries.computeIfAbsent(playerName, k -> new ArrayList<>());
        entries.add(logEntry);

        // Запись всего лога игрока в файл при каждом действии (можно оптимизировать, если необходимо)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
            for (Map.Entry<String, List<String>> entry : playerLogEntries.entrySet()) {
                writer.write(entry.getKey() + " {\n");
                for (String playerEntry : entry.getValue()) {
                    writer.write("   " + playerEntry); // Записи о заходах и выходах
                }
                writer.write("}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Вспомогательный метод для форматирования длительности
    private String formatDuration(long durationMs) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(durationMs) % 24;
        long days = TimeUnit.MILLISECONDS.toDays(durationMs);
        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "[" + dateFormat.format(new Date()) + "]";
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (scheduler.isShutdown() || scheduler.isTerminated()) {
            // Если планировщик был завершен, создаем новый
            scheduler = Executors.newScheduledThreadPool(1);
        }
        // Запуск периодического обновления каждые 1 секунд
        scheduler.scheduleAtFixedRate(() -> fetchPlayerListAndLog(), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        try {
            scheduler.shutdownNow(); // Остановить все активные задачи
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) { // Ожидать завершения всех задач
                System.out.println("Scheduler did not terminate in the specified time.");
            }
        } catch (InterruptedException e) {
            System.out.println("Scheduler shutdown interrupted");
        } finally {
            if (!scheduler.isTerminated()) {
                System.out.println("Cancelling non-finished tasks");
            }
            scheduler.shutdownNow(); // Попытаться остановить все активно выполняющиеся задачи
        }
        System.out.println("Scheduler shutdown finished");
    }

}
