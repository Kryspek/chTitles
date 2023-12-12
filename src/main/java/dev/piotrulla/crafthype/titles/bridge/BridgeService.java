package dev.piotrulla.crafthype.titles.bridge;

import dev.piotrulla.crafthype.titles.TitlesPlugin;
import dev.piotrulla.crafthype.titles.bridge.vault.VaultMoneyResolver;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BridgeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BridgeService.class);

    private final ServicesManager servicesManager;
    private final PluginManager pluginManager;
    private MoneyResolver moneyResolver;

    public BridgeService(ServicesManager servicesManager, PluginManager pluginManager) {
        this.servicesManager = servicesManager;
        this.pluginManager = pluginManager;
    }

    public void init() {
        this.init("Vault", () -> {
            RegisteredServiceProvider<Economy> economyRegisteredServiceProvider = this.servicesManager.getRegistration(Economy.class);

            if (economyRegisteredServiceProvider == null) {
                LOGGER.warn("Found Vault plugin but can't hadnle any providers!");

                this.pluginManager.disablePlugin(TitlesPlugin.getProvidingPlugin(TitlesPlugin.class));

                return;
            }

            this.moneyResolver = new VaultMoneyResolver(economyRegisteredServiceProvider.getProvider());
        });
    }

    void init(String pluginName, BridgeInitalizer initalizer) {
        if (this.pluginManager.isPluginEnabled(pluginName)) {
            initalizer.initialize();

            LOGGER.info("Created bridge with "+ pluginName + " plugin!");
        }
    }

    public MoneyResolver getMoneyResolver() {
        return this.moneyResolver;
    }
}
