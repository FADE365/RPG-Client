package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JoinLeaveRecorder extends Module {
    private final File logFile;
    private final Map<String, Long> playerJoinTimes;

    public JoinLeaveRecorder() {
        super("JoinLeaveRecorder", Keyboard.KEY_NONE, Category.MISC);
        logFile = new File("LOG_JOIN_LEAVE.txt");
        playerJoinTimes = new HashMap<>();
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ITextComponent playerNameComponent = event.player.getDisplayName();
        String playerName = playerNameComponent.getUnformattedText();
        playerJoinTimes.put(playerName, System.currentTimeMillis());
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        ITextComponent playerNameComponent = event.player.getDisplayName();
        String playerName = playerNameComponent.getUnformattedText();
        Long joinTime = playerJoinTimes.remove(playerName);

        if (joinTime != null) {
            long duration = System.currentTimeMillis() - joinTime;
            saveLog(playerName, "left", duration);
        }
    }

    private void saveLog(String playerName, String action, long duration) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            String logEntry = String.format("%s [%s] %s - Duration: %dms\n",
                    getCurrentTimestamp(), playerName, action, duration);
            writer.write(logEntry);
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
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
