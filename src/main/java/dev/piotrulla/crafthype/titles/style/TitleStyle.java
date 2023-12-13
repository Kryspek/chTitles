package dev.piotrulla.crafthype.titles.style;

public enum TitleStyle {

    DARK_RED("Ciemno Czerwony", 10000, 10, "<gradient:dark_red:dark_red>", "</gradient>"),
    GOLD("Złoty", 10000, 11, "<gradient:gold:gold>", "</gradient>"),
    YELLOW("Żółty", 10000, 12, "<gradient:yellow:yellow>", "</gradient>"),
    DARK_GREEN("Ciemno Zielony", 10000, 13, "<gradient:dark_green:dark_green>", "</gradient>"),
    GREEN("Zielony", 10000, 14, "<gradient:green:green>", "</gradient>"),
    DARK_AQUA("Ciemno Morski", 10000, 15, "<gradient:dark_aqua:dark_aqua>", "</gradient>"),
    AQUA("Morski", 10000, 16, "<gradient:aqua:aqua>", "</gradient>"),
    DARK_BLUE("Ciemno Niebieski", 10000, 19, "<gradient:dark_blue:dark_blue>", "</gradient>"),
    BLUE("Niebieski", 10000, 20, "<gradient:blue:blue>", "</gradient>"),
    DARK_PURPLE("Ciemno Fieletowy", 10000, 21, "<gradient:dark_purple:dark_purple>", "</gradient>"),
    PURPLE("Fieletowy", 10000, 22, "<gradient:light_purple:light_purple>", "</gradient>"),
    WHITE("Biały", 10000, 23, "<gradient:white:white>", "</gradient>"),
    GRAY("Szary", 10000, 24, "<gradient:gray:gray>", "</gradient>"),
    DARK_GRAY("Ciemno Szary", 10000, 25, "<gradient:dark_gray:dark_gray>", "</gradient>"),
    BLACK("Czarny", 10000, 28, "<gradient:black:black>", "</gradient>"),
    RAINBOW("Rainbow", 10000, 29, "<gradient:dark_red:red:gold:yellow:green:aqua>", "</gradient>");

    private final String name;
    private final int price;
    private final int slot;
    private final String textColor;
    private final String textColorEnd;

    TitleStyle(String name, int price, int slot, String textColor, String textColorEnd) {
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.textColor = textColor;
        this.textColorEnd = textColorEnd;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
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
