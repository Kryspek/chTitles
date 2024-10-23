package dev.piotrulla.crafthype.titles.config.implementation;

import dev.piotrulla.crafthype.titles.config.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataConfig implements ReloadableConfig {

    public Map<UUID, List<String>> purchasedColorsByUuids = new ConcurrentHashMap<>();
    public Map<UUID, String> currentColorByUuids = new ConcurrentHashMap<>();
    public Map<UUID, Map<String, String>> purchaseDatesByUuids = new HashMap<>(); // New field for purchase dates


    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "data/users.yml");
    }
}