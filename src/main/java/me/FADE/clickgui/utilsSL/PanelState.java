package me.FADE.clickgui.utilsSL;
import com.google.gson.annotations.Expose;

public class PanelState {
    @Expose
    public int x, y = 0;
    @Expose
    public boolean isExtended;
    @Expose
    public String category;

    public PanelState(int x, int y, boolean isExtended, String category) {
        this.x = x;
        this.y = y;
        this.isExtended = isExtended;
        this.category = category;
    }
}

