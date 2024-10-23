package dev.piotrulla.crafthype.titles;

import dev.piotrulla.crafthype.titles.config.implementation.UserDataRepository;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class TitleHistory {

    private final UserDataRepository userDataRepository;
    private final MiniMessage miniMessage;
    private final int[] slots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public TitleHistory(UserDataRepository userDataRepository, MiniMessage miniMessage) {
        this.userDataRepository = userDataRepository;
        this.miniMessage = miniMessage;
    }

    public void openPurchaseHistory(Player player) {
        openPurchaseHistory(player, 0);
    }

    public void openPurchaseHistory(Player player, int page) {
        Gui historyGui = Gui.gui()
                .rows(6)
                .title(this.resolveMiniMessage("Historia zakupionych tytułów"))
                .disableAllInteractions()
                .create();

        List<Map.Entry<String, String>> purchasedTitles = userDataRepository.findPurchasedTitlesWithDates(player.getUniqueId());
        int itemsPerPage = slots.length;
        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, purchasedTitles.size());

        for (int i = startIndex; i < endIndex; i++) {
            Map.Entry<String, String> entry = purchasedTitles.get(i);
            String title = entry.getKey();
            String date = entry.getValue();
            ItemBuilder titleItem = ItemBuilder.from(Material.PAPER)
                    .name(this.resolveMiniMessage(title))
                    .lore(this.resolveMiniMessage("<white>" + "<white>Zakupiono:</white> <dark_gray>" + date));

            historyGui.setItem(slots[i - startIndex], titleItem.asGuiItem());
        }

        ItemBuilder close = ItemBuilder.from(Material.BARRIER)
                .name(this.resolveMiniMessage("<red>Wyjdź"));

        historyGui.setItem(49, close.asGuiItem(inventoryClickEvent -> {
            player.closeInventory();
        }));

        if (startIndex > 0) {
            ItemBuilder previousPage = ItemBuilder.from(Material.ARROW)
                    .name(this.resolveMiniMessage("<red>Poprzednia strona"));

            historyGui.setItem(47, previousPage.asGuiItem(inventoryClickEvent -> {
                openPurchaseHistory(player, page - 1);
            }));
        }

        if (endIndex < purchasedTitles.size()) {
            ItemBuilder nextPage = ItemBuilder.from(Material.ARROW)
                    .name(this.resolveMiniMessage("<red>Następna strona"));

            historyGui.setItem(51, nextPage.asGuiItem(inventoryClickEvent -> {
                openPurchaseHistory(player, page + 1);
            }));
        }

        historyGui.open(player);
    }

    Component resolveMiniMessage(String text) {
        return Component.text().decoration(TextDecoration.ITALIC, false).append(this.miniMessage.deserialize(text)).build();
    }
}