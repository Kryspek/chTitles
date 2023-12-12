package dev.piotrulla.crafthype.titles.config.implementation;


import dev.piotrulla.crafthype.titles.config.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataConfig implements ReloadableConfig {

    public Map<UUID, String> titlesByUuids = new ConcurrentHashMap<>();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "data/users.yml");
    }
}
