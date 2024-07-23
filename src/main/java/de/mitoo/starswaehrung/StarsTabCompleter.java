package de.mitoo.starswaehrung;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StarsTabCompleter implements TabCompleter {

    private static final List<String> MAIN_ACTIONS = List.of("set", "give", "take", "reset", "see");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Hauptaktionen (set, give, take, reset, see) Vervollständigen
            for (String action : MAIN_ACTIONS) {
                if (action.startsWith(args[0].toLowerCase())) {
                    completions.add(action);
                }
            }
        } else if (args.length == 2) {
            if (MAIN_ACTIONS.contains(args[0].toLowerCase())) {
                // Spieler-Namen für Aktionen, die einen Spieler erfordern
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(player.getName());
                    }
                }
            }
        } else if (args.length == 3) {
            if (MAIN_ACTIONS.subList(0, 4).contains(args[0].toLowerCase())) {
                // Zahlen für set, give, take
                if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take")) {
                    completions.add("<amount>");
                }
            }
        }

        return completions;
    }
}
