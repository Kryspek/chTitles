package dev.piotrulla.crafthype.titles.bridge.vault;

import dev.piotrulla.crafthype.titles.bridge.MoneyResolver;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;

public class VaultMoneyResolver implements MoneyResolver {

    private final Economy economy;

    public VaultMoneyResolver(Economy economy) {
        this.economy = economy;
    }

    @Override
    public boolean has(OfflinePlayer player, int amount) {
        return this.economy.has(player, amount);
    }

    @Override
    public void deposit(OfflinePlayer player, int amount) {
        this.economy.depositPlayer(player, amount);
    }

    @Override
    public void withdrawl(OfflinePlayer player, int amount) {
        this.economy.withdrawPlayer(player, amount);
    }
}
