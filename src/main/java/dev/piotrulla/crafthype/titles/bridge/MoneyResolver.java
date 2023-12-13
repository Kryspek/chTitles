package dev.piotrulla.crafthype.titles.bridge;

import org.bukkit.OfflinePlayer;

public interface MoneyResolver {

    boolean has(OfflinePlayer player, double amount);

    void deposit(OfflinePlayer player, double amount);

    void withdrawl(OfflinePlayer player, double amount);

}
