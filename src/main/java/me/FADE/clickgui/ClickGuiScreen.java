package me.FADE.clickgui;

import com.example.examplemod.Module.Module;
import me.FADE.clickgui.utilsSL.PanelState;
import me.FADE.clickgui.utilsSL.PanelStateManager;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ClickGuiScreen extends GuiScreen {
    public List<Panel> panels = new ArrayList<>();
    public Panel panel;

    public ClickGuiScreen() {
        String stateFilePath = mc.getMinecraft().mcDataDir.getAbsolutePath() + "/RPG Client/panel_states.json";
        List<PanelState> savedStates = PanelStateManager.loadPanelStates(stateFilePath);
        int y = 10;
        for (Module.Category value : Module.Category.values()) {
            PanelState stateForCategory = findStateForCategory(value, savedStates);
            int x = (stateForCategory != null) ? stateForCategory.x : 10;
            int yPosition = (stateForCategory != null) ? stateForCategory.y : y;
            boolean isExtended = (stateForCategory != null) && stateForCategory.isExtended;
            Panel newPanel = new Panel(x, yPosition, 110, 15, value, isExtended);
            panels.add(newPanel);
            y += 20;
        }
    }

    private PanelState findStateForCategory(Module.Category category, List<PanelState> states) {
        if (states != null) {
            for (PanelState state : states) {
                if (state != null && state.category != null && state.category.equals(category.name())) {
                    return state;
                }
            }
        }
        return null;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Panel panel : panels) {
            panel.drawScreen(mouseX, mouseY, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (Panel panel : panels) {
            panel.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Panel panel : panels) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panel panel : panels) {
            panel.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
}
