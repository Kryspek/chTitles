package dev.piotrulla.crafthype.titles.style;

public enum TitleStyle {

    RED("Ciemnoczerwony", 10, 10, "gradient:dark_red:dark_red", "</gradient>"),
    BLUE("Niebieski", 10, 11, "gradient:blue:blue", "</gradient>"),
    RAINBOW("Kolorowy", 1000, 12, "gradient:dark_red:red:gold:yellow:green:aqua", "</gradient>");

    private final String name;
    private final double price;
    private final int slot;
    private final String textColor;
    private final String textColorEnd;

    TitleStyle(String name, double price, int slot, String textColor, String textColorEnd) {
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.textColor = textColor;
        this.textColorEnd = textColorEnd;
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

    public String getTextColorEnd() {
        return this.textColorEnd;
    }


}
