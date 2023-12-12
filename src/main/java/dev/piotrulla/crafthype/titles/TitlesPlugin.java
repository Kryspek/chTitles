package dev.piotrulla.crafthype.titles;

import dev.piotrulla.crafthype.titles.bridge.BridgeService;
import dev.piotrulla.crafthype.titles.config.ConfigService;
import dev.piotrulla.crafthype.titles.config.implementation.UserDataConfig;
import dev.piotrulla.crafthype.titles.config.implementation.UserDataRepository;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class TitlesPlugin extends JavaPlugin {

    private ConfigService configService;
    private UserDataConfig userDataConfig;

    private BridgeService bridgeService;

    private UserDataRepository userRepository;

    private TitleInventory titleInventory;

    @Override
    public void onEnable() {
        this.configService = new ConfigService(this.getDataFolder());
        this.userDataConfig = this.configService.load(new UserDataConfig());

        Server server = this.getServer();

        MiniMessage miniMessage = MiniMessage.miniMessage();

        this.bridgeService = new BridgeService(server.getServicesManager(), server.getPluginManager());
        this.bridgeService.init();

        this.userRepository = new UserDataRepository(this.configService, this.userDataConfig);

        this.titleInventory = new TitleInventory(this.userRepository, this.bridgeService.getMoneyResolver(), miniMessage);

        new TitlePlaceholder(this.userRepository, miniMessage).register();

        this.getCommand("title").setExecutor(new TitleCommand(this.titleInventory, miniMessage));
    }

    @Override
    public void onDisable() {

    }
}
