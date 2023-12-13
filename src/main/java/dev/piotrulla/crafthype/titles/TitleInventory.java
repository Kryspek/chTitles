package dev.piotrulla.crafthype.titles;

import dev.piotrulla.crafthype.titles.bridge.MoneyResolver;
import dev.piotrulla.crafthype.titles.config.implementation.UserDataRepository;
import dev.piotrulla.crafthype.titles.style.TitleStyle;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;

public class TitleInventory {

    private final UserDataRepository userDataRepository;
    private final MoneyResolver moneyResolver;
    private final MiniMessage miniMessage;

    public TitleInventory(UserDataRepository userDataRepository, MoneyResolver moneyResolver, MiniMessage miniMessage) {
        this.userDataRepository = userDataRepository;
        this.moneyResolver = moneyResolver;
        this.miniMessage = miniMessage;
    }

    public void openInventory(Player player, String title) {
        Gui gui = Gui.gui()
                .rows(5)
                .title(this.miniMessage.deserialize("Wybierz kolor dla tytułu " + title + "?"))
                .disableAllInteractions()
                .create();

        for (TitleStyle style : TitleStyle.values()) {
            boolean hasPrice = this.moneyResolver.has(player, style.getPrice());

            ItemBuilder styledItem = ItemBuilder.from(Material.OAK_SIGN)
                    .name(this.miniMessage.deserialize("<" +style.getTextColor()+"> "+style.getName() + style.getTextColorEnd()))
                    .lore(this.miniMessage.deserialize("<dark_gray> Tytuły"),
                            this.miniMessage.deserialize(""),
                            this.miniMessage.deserialize("<gold> Cena: "+ style.getPrice() + "zl"),
                            this.miniMessage.deserialize("<dark_gray> Podgląd:"),
                            this.miniMessage.deserialize("<" + style.getTextColor() + ">" + "[" + title + "]" + style.getTextColorEnd() + " <gray>" + player.getName()),
                            this.miniMessage.deserialize(""),
                            this.miniMessage.deserialize(hasPrice ? "<green>Kliknij aby zakupić!" : "<red>Nie masz wystarczająco kasy!")
                    );

            gui.setItem(style.getSlot(), new GuiItem(styledItem.build(), event -> {
                String userTitle = userDataRepository.find(player.getUniqueId());

                if (userTitle != null && userTitle.equals(title)) {
                    return;
                }

                if (!this.moneyResolver.has(player, style.getPrice())) {
                    return;
                }
                player.closeInventory();

                this.userDataRepository.createWithTitle(player.getUniqueId(), newTitle);

                this.moneyResolver.withdrawl(player, style.getPrice());
                player.sendMessage(legacySection().serialize(
                        this.miniMessage.deserialize("<gray>Pomyślnie zakupiono tytuł <"+style.getTextColor()+"> "+ title))
                );
            }));

            gui.open(player);
        }
    }

    String generateFooter(Player player, double price, String title) {
        String userTitle = userDataRepository.find(player.getUniqueId());

        if (userTitle != null && userTitle.equals(title)) {
            return "<red>Posiadasz już ten kolor!";
        }

        boolean hasMoney = this.moneyResolver.has(player, price);
        if (hasMoney) {
            return "<yellow>Kliknij, aby zakupić!";
        }
        else {
            return "<red>Nie stać cię!";
        }
    }
}
