package dev.piotrulla.crafthype.titles.command;

import dev.piotrulla.crafthype.titles.config.implementation.UserDataRepository;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemoveCommand implements CommandExecutor {

    private final MiniMessage miniMessage;
    private final UserDataRepository userDataRepository;

    public RemoveCommand(MiniMessage miniMessage, UserDataRepository userDataRepository) {
        this.miniMessage = miniMessage;
        this.userDataRepository = userDataRepository;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if (!commandSender.hasPermission("title.admin")) {
            commandSender.sendMessage(LegacyComponentSerializer.legacySection().serialize(this.miniMessage.deserialize("<red>Brak uprawnień do tej komendy!")));
            return false;
        }

        if (args.length != 2) {
            commandSender.sendMessage(LegacyComponentSerializer.legacySection().serialize(this.miniMessage.deserialize("<red>Użycie: /titleadmin <tytuł> <gracz>")));
            return false;
        }

        Player targetPlayer = player.getServer().getPlayer(args[1]);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage(LegacyComponentSerializer.legacySection().serialize(this.miniMessage.deserialize("<red>Podany gracz jest offline!")));
            return false;
        }

        this.userDataRepository.removeUser(targetPlayer.getUniqueId());
        player.sendMessage(LegacyComponentSerializer.legacySection().serialize(this.miniMessage.deserialize("<gray>Pomyślnie usunięto tytuł!")));
        return true;
    }
}
