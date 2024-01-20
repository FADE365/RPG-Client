package me.FADE.clickgui.utilsSL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelStateManager {
    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static final Type PANEL_STATE_LIST_TYPE = new TypeToken<List<PanelState>>() {}.getType();

    public static void savePanelStates(List<PanelState> states, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Удалите дублирующиеся состояния перед сохранением
            Map<String, PanelState> uniqueStates = new HashMap<>();
            for (PanelState state : states) {
                uniqueStates.put(state.category, state);
            }
            GSON.toJson(new ArrayList<>(uniqueStates.values()), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<PanelState> loadPanelStates(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return GSON.fromJson(reader, PANEL_STATE_LIST_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

