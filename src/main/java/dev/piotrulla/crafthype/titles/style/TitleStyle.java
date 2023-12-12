package dev.piotrulla.crafthype.titles.style;

public enum TitleStyle {

    RED("Ciemnoczerwony", 10, 10, "DARK_RED"),
    BLUE("Niebieski", 10, 11, "BLUE"),
    RAINBOW("Kolorowy", 1000, 12, "RAINBOW");

    private final String name;
    private final double price;
    private final int slot;
    private final String textColor;

    TitleStyle(String name, double price, int slot, String textColor) {
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.textColor = textColor;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getSlot() {
        return this.slot;
    }

    public String getTextColor() {
        return this.textColor;
    }


}
