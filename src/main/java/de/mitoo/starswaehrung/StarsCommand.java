package de.mitoo.starswaehrung;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

import java.util.UUID;

public class StarsCommand implements CommandExecutor {

    private final StarsWaehrung plugin;

    public StarsCommand(StarsWaehrung plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length < 1) {
            sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Verwendung: /stars <set|give|see|take|reset> <player> [amount]");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Verwendung: /stars <set|give|see|take|reset> <player> [amount]");
            return true;
        }

        String action = args[0];
        Player player = sender instanceof Player ? (Player) sender : null;

        // Check permissions
        switch (action.toLowerCase()) {
            case "set":
                if (player != null && !player.hasPermission("starswaehrung.set")) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl auszuführen.");
                    return true;
                }
                break;

            case "give":
                if (player != null && !player.hasPermission("starswaehrung.give")) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl auszuführen.");
                    return true;
                }
                break;

            case "take":
                if (player != null && !player.hasPermission("starswaehrung.take")) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl auszuführen.");
                    return true;
                }
                break;

            case "reset":
                if (player != null && !player.hasPermission("starswaehrung.reset")) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl auszuführen.");
                    return true;
                }
                break;

            case "see":
                if (player != null && !player.hasPermission("starswaehrung.see")) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl auszuführen.");
                    return true;
                }
                break;

            default:
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Unbekannter Befehl: " + action);
                return true;
        }

        if ("see".equalsIgnoreCase(action)) {
            if (args.length == 1 && sender instanceof Player) {
                Player senderPlayer = (Player) sender;
                UUID playerId = senderPlayer.getUniqueId();
                int currentAmount = plugin.getStars(playerId);
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Du hast " + currentAmount + " ★");
            } else if (args.length == 2) {
                String targetPlayerName = args[1];
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer == null) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Spieler nicht gefunden.");
                    return true;
                }

                UUID playerId = targetPlayer.getUniqueId();
                int currentAmount = plugin.getStars(playerId);
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + targetPlayer.getName() + " hat " + currentAmount + " ★");
            } else {
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Verwendung: /stars see [player]");
            }
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Verwendung: /stars <set|give|take|reset> <player> [amount]");
            return true;
        }

        String targetPlayerName = args[1];
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Spieler nicht gefunden.");
            return true;
        }

        UUID targetPlayerId = targetPlayer.getUniqueId();

        switch (action.toLowerCase()) {
            case "set":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Verwendung: /stars set <player> <amount>");
                    return true;
                }
                int setAmount;
                try {
                    setAmount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Ungültiger Betrag: " + args[2]);
                    return true;
                }
                plugin.setStars(targetPlayerId, setAmount);
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Die ★ von " + targetPlayer.getName() + " wurden auf " + setAmount + " ★ gesetzt");
                break;

            case "give":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Verwendung: /stars give <player> <amount>");
                    return true;
                }
                int giveAmount;
                try {
                    giveAmount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Ungültiger Betrag: " + args[2]);
                    return true;
                }
                plugin.giveStars(targetPlayerId, giveAmount);
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + targetPlayer.getName() + " hat " + giveAmount + " ★ erhalten");
                break;

            case "take":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Verwendung: /stars take <player> <amount>");
                    return true;
                }
                int takeAmount;
                try {
                    takeAmount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Ungültiger Betrag: " + args[2]);
                    return true;
                }
                int currentStars = plugin.getStars(targetPlayerId);

                if (currentStars == 0) {
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Der Spieler hat keine ★, daher ist es nicht möglich etwas abzuziehen.");
                } else if (takeAmount > currentStars) {
                    plugin.takeStars(targetPlayerId, currentStars);
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Es wurden nur " + currentStars + " ★ abgezogen, da der Spieler nur so viel hat.");
                } else {
                    plugin.takeStars(targetPlayerId, takeAmount);
                    sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Von " + targetPlayer.getName() + " wurden " + takeAmount + " ★ abgezogen.");
                }
                break;

            case "reset":
                plugin.setStars(targetPlayerId, 0);
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + targetPlayer.getName() + " wurden alle ★ entfernt");
                break;

            default:
                sender.sendMessage(ChatColor.AQUA + "LocoBroko " + ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Unbekannter Befehl: " + action);
                break;
        }

        return true;
    }
}
