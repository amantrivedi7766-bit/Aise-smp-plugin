package com.amantrivedi.arise;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private ArisePlugin plugin;

    public Commands(ArisePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("arise")) {
            if (!plugin.getManager().hasPower(p)) {
                // Fake check for task completion
                plugin.getManager().unlockPower(p);
                return true;
            }
        }

        if (!plugin.getManager().hasPower(p)) {
            p.sendMessage("§cYou do not have Arise power yet!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("arisework")) {
            if (args.length < 2) {
                p.sendMessage("§cUsage: /arisework <mob> <order>");
                return true;
            }
            p.sendMessage("§5[Shadow] Your " + args[0] + " is now executing order: " + args[1]);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("soul")) {
            if (args.length < 2) return false;
            String action = args[0].toLowerCase();
            String target = args[1];

            switch (action) {
                case "release":
                    plugin.getManager().getSouls(p).remove(target);
                    p.sendMessage("§aReleased soul: " + target);
                    plugin.getScoreboardManager().updateScoreboard(p);
                    break;
                case "attack":
                    p.sendMessage("§cSouls are now attacking " + target);
                    break;
                case "task":
                    p.sendMessage("§5Assigned task to " + target);
                    break;
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("soulcome")) {
            p.sendMessage("§8All souls have returned to your shadow.");
            return true;
        }
        return false;
    }
}