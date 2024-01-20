package me.FADE.clickgui.Elements;

public class Toggle {
    private boolean state;
    private String label;

    public Toggle(String label, boolean initialState) {
        this.label = label;
        this.state = initialState;
    }
    public void render(int x, int y) {
        // Отрисовка переключателя
    }
    public void onClick() {
        state = !state;
        // Обработка клика по переключател

    }
    public boolean getState() {
        return state;
    }

    public String getLabel() {
        return label;
    }
}