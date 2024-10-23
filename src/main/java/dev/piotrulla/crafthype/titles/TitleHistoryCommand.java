package dev.piotrulla.crafthype.titles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TitleHistoryCommand implements CommandExecutor {

    private final TitleHistory titleInventory;

    public TitleHistoryCommand(TitleHistory titleInventory) {
        this.titleInventory = titleInventory;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return false;
        }

        titleInventory.openPurchaseHistory(player);
        return true;
    }
}
