package de.mitoo.starswaehrung;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class StarsWaehrung extends JavaPlugin implements Listener {

    private StarManager starManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        this.getCommand("stars").setExecutor(new StarsCommand(this));
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
        String url = getConfig().getString("datenbank.url");
        String user = getConfig().getString("datenbank.user");
        String password = getConfig().getString("datenbank.password");

        if (url == null || user == null || password == null) {
            getLogger().severe("Datenbankkonfiguration ist unvollständig. Bitte überprüfen Sie die Konfigurationsdatei.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            databaseManager = new DatabaseManager(url, user, password);
            databaseManager.connect();
            starManager = new StarManager(databaseManager);
        } catch (Exception e) {
            getLogger().severe("Fehler beim Initialisieren der Datenbankverbindung: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }


        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerId = player.getUniqueId();
            starManager.loadPlayerData(playerId);
        }


        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        if (starManager != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                starManager.savePlayerData(playerId);
            }
        }
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        starManager.loadPlayerData(playerId);
    }

    public void setStars(UUID playerId, int amount) {
        starManager.setStars(playerId, amount);
        databaseManager.savePlayerData(playerId, amount);
    }

    public int getStars(UUID playerId) {
        return starManager.getStars(playerId);
    }

    public void giveStars(UUID playerId, int amount) {
        starManager.giveStars(playerId, amount);
        databaseManager.savePlayerData(playerId, amount);
    }

    public void takeStars(UUID playerId, int amount) {
        starManager.takeStars(playerId, amount);
        databaseManager.savePlayerData(playerId, amount);
    }
}
