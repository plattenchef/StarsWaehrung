package de.mitoo.starswaehrung;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StarManager {
    private final DatabaseManager databaseManager;
    private final Map<UUID, Integer> playerStars;

    public StarManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.playerStars = new HashMap<>();
    }

    public void loadPlayerData(UUID playerUUID) {
        int stars = databaseManager.getPlayerStars(playerUUID);
        playerStars.put(playerUUID, stars);
    }

    public void savePlayerData(UUID playerUUID) {
        Integer stars = playerStars.get(playerUUID);
        if (stars != null) {
            databaseManager.savePlayerData(playerUUID, stars);
        }
    }

    public int getStars(UUID playerUUID) {
        return databaseManager.getPlayerStars(playerUUID);
    }

    public void setStars(UUID playerUUID, int stars) {
        playerStars.put(playerUUID, stars);
        databaseManager.savePlayerData(playerUUID, stars);
    }

    public void giveStars(UUID playerUUID, int amount) {
        int currentStars = getStars(playerUUID);
        setStars(playerUUID, currentStars + amount);
        databaseManager.getPlayerStars(playerUUID);
    }

    public void takeStars(UUID playerUUID, int amount) {
        int currentStars = getStars(playerUUID);
        setStars(playerUUID, Math.max(0, currentStars - amount));
        databaseManager.getPlayerStars(playerUUID);
    }
}
