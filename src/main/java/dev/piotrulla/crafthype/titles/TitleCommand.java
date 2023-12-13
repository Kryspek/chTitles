package dev.piotrulla.crafthype.titles;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;

public class TitleCommand implements CommandExecutor {

    private final TitleInventory titleInventory;
    private final MiniMessage miniMessage;

    public TitleCommand(TitleInventory titleInventory, MiniMessage miniMessage) {
        this.titleInventory = titleInventory;
        this.miniMessage = miniMessage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(legacySection().serialize(this.miniMessage.deserialize("<red>Użycie: /title <nazwa>")));
            return false;
        }

        String title = args[0];

        if (title.length() > 6) {
            player.sendMessage(legacySection().serialize(this.miniMessage.deserialize("<red>Tytuł nie może być dłuższy niż 6 znaków!")));
            return false;
        }

        this.titleInventory.openInventory(player, title);

        return true;
    }

}
