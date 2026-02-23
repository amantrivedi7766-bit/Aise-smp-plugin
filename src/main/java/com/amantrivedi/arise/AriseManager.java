package com.amantrivedi.arise;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import java.util.*;

public class AriseManager {
    private ArisePlugin plugin;
    private Map<UUID, BossBar> playerTasks = new HashMap<>();
    private Map<UUID, Boolean> hasArisePower = new HashMap<>();
    private Map<UUID, List<String>> playerSouls = new HashMap<>();

    public AriseManager(ArisePlugin plugin) {
        this.plugin = plugin;
        // Yaha par database ya file load logic lagana hoga
    }

    public void assignTask(Player player) {
        if (hasArisePower.getOrDefault(player.getUniqueId(), false)) return;
        
        String[] tasks = {"Kill 50 Zombies", "Mine 100 Diamonds", "Defeat a Wither"};
        String task = tasks[new Random().nextInt(tasks.length)];
        
        BossBar bar = Bukkit.createBossBar("Task: " + task + " (0%)", BarColor.PURPLE, BarStyle.SOLID);
        bar.addPlayer(player);
        playerTasks.put(player.getUniqueId(), bar);
    }

    public void completeTask(Player player) {
        BossBar bar = playerTasks.get(player.getUniqueId());
        if (bar != null) {
            bar.setTitle("Task Completed! Type /arise to claim power.");
            bar.setProgress(1.0);
        }
    }

    public void unlockPower(Player player) {
        hasArisePower.put(player.getUniqueId(), true);
        BossBar bar = playerTasks.remove(player.getUniqueId());
        if (bar != null) bar.removeAll();
        player.sendMessage("§5[System] You have unlocked the Arise power! Kill mobs and say 'arise' to capture their souls.");
    }

    public boolean hasPower(Player player) {
        return hasArisePower.getOrDefault(player.getUniqueId(), false);
    }

    public void captureSoul(Player player, String mobName) {
        List<String> souls = playerSouls.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>());
        if(souls.size() < plugin.getConfig().getInt("max-soul-storage")) {
            souls.add(mobName);
            player.sendMessage("§5[System] You captured the soul of " + mobName + "!");
            plugin.getScoreboardManager().updateScoreboard(player);
        } else {
            player.sendMessage("§c[System] Soul storage full (10000 limit)!");
        }
    }

    public List<String> getSouls(Player player) {
        return playerSouls.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public void saveData() {
        // Logic to save data locally in json/yml
    }
}