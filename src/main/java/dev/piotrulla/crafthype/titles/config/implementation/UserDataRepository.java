package dev.piotrulla.crafthype.titles.config.implementation;

import dev.piotrulla.crafthype.titles.config.ConfigService;

import java.time.LocalDate;
import java.util.*;

public class UserDataRepository {

    private final ConfigService configService;
    private final UserDataConfig dataConfig;

    public UserDataRepository(ConfigService configService, UserDataConfig dataConfig) {
        this.configService = configService;
        this.dataConfig = dataConfig;
    }

    public List<String> findPurchasedColors(UUID uniqueId) {
        return this.dataConfig.purchasedColorsByUuids.get(uniqueId);
    }

    public String getCurrentColor(UUID uniqueId) {
        return this.dataConfig.currentColorByUuids.get(uniqueId);
    }

    public void createWithTitle(UUID uniqueId, String title) {
        Map<UUID, List<String>> users = this.dataConfig.purchasedColorsByUuids;
        List<String> purchasedColors = users.computeIfAbsent(uniqueId, k -> new ArrayList<>());

        if (!purchasedColors.contains(title)) {
            purchasedColors.add(title);
        }

        this.dataConfig.currentColorByUuids.put(uniqueId, title);

        // Save the purchase date
        Map<UUID, Map<String, String>> purchaseDates = this.dataConfig.purchaseDatesByUuids;
        Map<String, String> userPurchaseDates = purchaseDates.computeIfAbsent(uniqueId, k -> new HashMap<>());
        userPurchaseDates.put(title, LocalDate.now().toString());

        this.saveConfig(users, this.dataConfig.currentColorByUuids, purchaseDates);
    }

    public List<Map.Entry<String, String>> findPurchasedTitlesWithDates(UUID playerId) {
        Map<String, String> titlesWithDates = this.dataConfig.purchaseDatesByUuids.get(playerId);
        if (titlesWithDates == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(titlesWithDates.entrySet());
    }

    void saveConfig(Map<UUID, List<String>> users, Map<UUID, String> currentColors, Map<UUID, Map<String, String>> purchaseDates) {
        this.dataConfig.purchasedColorsByUuids = users;
        this.dataConfig.currentColorByUuids = currentColors;
        this.dataConfig.purchaseDatesByUuids = purchaseDates;
        this.configService.save(this.dataConfig);
    }
}