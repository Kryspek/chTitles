package dev.piotrulla.crafthype.titles.config.implementation;

import dev.piotrulla.crafthype.titles.config.ConfigService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataRepository {

    private final ConfigService configService;
    private final UserDataConfig dataConfig;

    public UserDataRepository(ConfigService configService, UserDataConfig dataConfig) {
        this.configService = configService;
        this.dataConfig = dataConfig;
    }

    public String find(UUID uniqueId) {
        return this.dataConfig.titlesByUuids.get(uniqueId);
    }

    public String createWithTitle(UUID uniqueId, String title) {
        Map<UUID, String> users = this.removeUser(uniqueId);

        users.put(uniqueId, title);
        this.saveConfig(users);

        return title;
    }

    Map<UUID, String> removeUser(UUID uniqueId) {
        Map<UUID, String> users = new ConcurrentHashMap<>(this.dataConfig.titlesByUuids);

        users.remove(uniqueId);

        return users;
    }

    void saveConfig(Map<UUID, String> users) {
        this.dataConfig.titlesByUuids = users;

        this.configService.save(this.dataConfig);

    }
}
