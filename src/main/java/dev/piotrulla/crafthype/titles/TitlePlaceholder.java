package dev.piotrulla.crafthype.titles;

import dev.piotrulla.crafthype.titles.config.implementation.UserDataRepository;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TitlePlaceholder extends PlaceholderExpansion {

    private final UserDataRepository userRepository;
    private final MiniMessage miniMessage;

    public TitlePlaceholder(UserDataRepository userRepository, MiniMessage miniMessage) {
        this.userRepository = userRepository;
        this.miniMessage = miniMessage;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "gmTitles";
    }

    @Override
    public @NotNull String getAuthor() {
        return "P1otrulla";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public String onPlaceholderRequest(final Player player, @NotNull final String params) {
        String currentTitle = this.userRepository.getCurrentColor(player.getUniqueId());


        if (currentTitle == null || currentTitle.isEmpty()) {
            return "";
        }

        // %gmTitles_title%
        if (params.equalsIgnoreCase("title")) {
            return LegacyComponentSerializer.legacySection().serialize(this.miniMessage.deserialize(currentTitle));
        }

        return "";
    }
}
