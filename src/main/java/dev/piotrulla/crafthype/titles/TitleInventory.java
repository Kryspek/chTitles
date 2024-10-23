package dev.piotrulla.crafthype.titles;

import dev.piotrulla.crafthype.titles.bridge.MoneyResolver;
import dev.piotrulla.crafthype.titles.config.implementation.UserDataRepository;
import dev.piotrulla.crafthype.titles.notification.NotificationAnnouncer;
import dev.piotrulla.crafthype.titles.style.TitleStyle;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class TitleInventory {

    private final static Component RESET_ITEM = Component.text()
            .decoration(TextDecoration.ITALIC, false)
            .build();

    private final UserDataRepository userDataRepository;
    private final MoneyResolver moneyResolver;
    private final MiniMessage miniMessage;
    private final LuckPerms luckPerms;
    private final NotificationAnnouncer notificationAnnouncer;

    public TitleInventory(UserDataRepository userDataRepository, MoneyResolver moneyResolver, MiniMessage miniMessage, LuckPerms luckPerms, NotificationAnnouncer notificationAnnouncer) {
        this.userDataRepository = userDataRepository;
        this.moneyResolver = moneyResolver;
        this.miniMessage = miniMessage;
        this.luckPerms = luckPerms;
        this.notificationAnnouncer = notificationAnnouncer;
    }

    public void openInventory(Player player, String title) {
        Gui gui = Gui.gui()
                .rows(6)
                .title(this.resolveMiniMessage("Wybierz kolor dla tytułu " + title + "?"))
                .disableAllInteractions()
                .create();

        for (TitleStyle style : TitleStyle.values()) {
            String newTitle = style.getTextColor() + "[" + title +"]";

            CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
            String primaryGroup = metaData.getPrefix();

            ItemBuilder styledItem = ItemBuilder.from(Material.OAK_SIGN)
                    .name(this.resolveMiniMessage(style.getTextColor() + style.getName() + style.getTextColorEnd()))
                    .lore(this.resolveMiniMessage("<dark_gray>Tytuły"),
                            this.resolveMiniMessage(""),
                            this.resolveMiniMessage("<white>Cena: <yellow>"+ style.getPrice()),
                            this.resolveMiniMessage("<white>Podgląd:"),
                            this.resolveMiniMessage(style.getTextColor() + "[" + title + "]" + style.getTextColorEnd()
                                    + " " + primaryGroup + " " + player.getName()),
                            this.resolveMiniMessage(""),
                            this.resolveMiniMessage(this.generateFooter(player, style.getPrice(), newTitle))
                    );

            gui.setItem(style.getSlot(), new GuiItem(styledItem.build(), event -> {
                List<String> userTitle = userDataRepository.findPurchasedColors(player.getUniqueId());

                if (userTitle != null && userTitle.contains(newTitle)) {
                    this.userDataRepository.createWithTitle(player.getUniqueId(), newTitle);
                    player.closeInventory();
                    this.notificationAnnouncer.sendMessage(player,
                            "<gradient:dark_red:red:gold:yellow:green:aqua><b>Tytuł</b></gradient> <dark_gray>➤</dark_gray>" +
                                    " <white>Pomyślnie ustawiono tytuł " + style.getTextColor() + "[" + title + "]");
                    return;
                }

                if (!this.moneyResolver.has(player, style.getPrice())) {
                    return;
                }
                player.closeInventory();

                this.userDataRepository.createWithTitle(player.getUniqueId(), newTitle);

                this.moneyResolver.withdrawl(player, style.getPrice());
                this.notificationAnnouncer.sendMessage(player,
                        "<gradient:dark_red:red:gold:yellow:green:aqua><b>Tytuł</b></gradient> <dark_gray>➤</dark_gray>" +
                                " <white>Pomyślnie zakupiono tytuł " + style.getTextColor() + "[" + title + "]");
            }));

            ItemBuilder close = ItemBuilder.from(Material.BARRIER)
                    .name(this.resolveMiniMessage("<red>Wyjdź"));

            gui.setItem(49, close.asGuiItem(inventoryClickEvent -> {
                player.closeInventory();
            }));

            gui.open(player);
        }
    }

    String generateFooter(Player player, int price, String title) {
        List<String> userTitle = userDataRepository.findPurchasedColors(player.getUniqueId());
        String currentColor = userDataRepository.getCurrentColor(player.getUniqueId());


        if (userTitle != null && userTitle.contains(title)) {
            if (currentColor != null && currentColor.equals(title)) {
                return "<red>Wybrany!";
            }
            return "<white>Kliknij, aby ustawić tytuł!";
        }

        boolean hasMoney = this.moneyResolver.has(player, price);
        if (hasMoney) {
            return "<white>Kliknij, aby zakupić!";
        }
        else {
            return "<red>Nie stać cię!";
        }
    }

    Component resolveMiniMessage(String text) {
        return Component.text().decoration(TextDecoration.ITALIC, false).append(this.miniMessage.deserialize(text)).build();
    }
}
