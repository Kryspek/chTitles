package dev.piotrulla.crafthype.titles.bridge;

import org.bukkit.OfflinePlayer;

public interface MoneyResolver {

    boolean has(OfflinePlayer player, int amount);

    void deposit(OfflinePlayer player, int amount);

    void withdrawl(OfflinePlayer player, int amount);

}
