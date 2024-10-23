package dev.piotrulla.crafthype.titles;

import dev.piotrulla.crafthype.titles.bridge.BridgeService;
import dev.piotrulla.crafthype.titles.config.ConfigService;
import dev.piotrulla.crafthype.titles.config.implementation.UserDataConfig;
import dev.piotrulla.crafthype.titles.config.implementation.UserDataRepository;
import dev.piotrulla.crafthype.titles.legacy.LegacyColorProcessor;
import dev.piotrulla.crafthype.titles.notification.NotificationAnnouncer;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class TitlesPlugin extends JavaPlugin {

    private ConfigService configService;
    private UserDataConfig userDataConfig;

    private BridgeService bridgeService;

    private UserDataRepository userRepository;

    private TitleInventory titleInventory;

    private TitleHistory titleHistory;

    private LuckPerms luckPerms;
    private NotificationAnnouncer notificationAnnouncer;

    private MiniMessage miniMessage;
    private AudienceProvider audienceProvider;

    @Override
    public void onEnable() {
        this.configService = new ConfigService(this.getDataFolder());
        this.userDataConfig = this.configService.load(new UserDataConfig());

        this.luckPerms = this.getServer().getServicesManager().load(LuckPerms.class);
        Server server = this.getServer();

        this.audienceProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();

        this.notificationAnnouncer = new NotificationAnnouncer(this.audienceProvider, this.miniMessage);

        this.bridgeService = new BridgeService(server.getServicesManager(), server.getPluginManager());
        this.bridgeService.init();

        this.userRepository = new UserDataRepository(this.configService, this.userDataConfig);

        this.titleInventory = new TitleInventory(this.userRepository, this.bridgeService.getMoneyResolver(), miniMessage, luckPerms, notificationAnnouncer);

        this.titleHistory = new TitleHistory(this.userRepository, miniMessage);

        new TitlePlaceholder(this.userRepository, miniMessage).register();

        this.getCommand("title").setExecutor(new TitleCommand(this.titleInventory, miniMessage));

        this.getCommand("titleshistory").setExecutor(new TitleHistoryCommand(this.titleHistory));
    }

    @Override
    public void onDisable() {

    }
}
