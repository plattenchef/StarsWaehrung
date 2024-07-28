package de.mitoo.starswaehrung;

import de.mitoo.starswaehrung.StarManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.util.UUID;

public class StarsWaehrungAPI {
    private static StarManager starManager;

    public static void initialize(StarManager manager) {
        starManager = manager;
    }

    public static int getStars(UUID playerId) {
        checkInitialized();
        return starManager.getStars(playerId);
    }

    public static void setStars(UUID playerId, int amount) {
        checkInitialized();
        starManager.setStars(playerId, amount);
    }

    public static void giveStars(UUID playerId, int amount) {
        checkInitialized();
        starManager.giveStars(playerId, amount);
    }

    public static void takeStars(UUID playerId, int amount) {
        checkInitialized();
        starManager.takeStars(playerId, amount);
    }

    private static void checkInitialized() {
        if (starManager == null) {
            throw new IllegalStateException("StarsWaehrungAPI is not initialized. Please make sure the plugin is enabled.");
        }
    }
}

