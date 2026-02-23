package com.amantrivedi.arise;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Listeners implements Listener {
    private ArisePlugin plugin;
    private Map<Entity, Integer> ariseAttempts = new HashMap<>();
    private Map<UUID, Entity> lastKilled = new HashMap<>();

    public Listeners(ArisePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!plugin.getManager().hasPower(p)) {
            plugin.getManager().assignTask(p);
        }
        plugin.getScoreboardManager().updateScoreboard(p);
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            if (plugin.getManager().hasPower(killer)) {
                lastKilled.put(killer.getUniqueId(), e.getEntity());
                ariseAttempts.put(e.getEntity(), 0);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("arise") && plugin.getManager().hasPower(p)) {
            Entity target = lastKilled.get(p.getUniqueId());
            if (target != null && p.getLocation().distance(target.getLocation()) < 10) {
                int attempts = ariseAttempts.getOrDefault(target, 0);
                if (attempts < 3) {
                    if (Math.random() > 0.5) { // 50% chance to capture
                        plugin.getManager().captureSoul(p, target.getType().name());
                        lastKilled.remove(p.getUniqueId());
                    } else {
                        ariseAttempts.put(target, attempts + 1);
                        p.sendMessage("§cFailed to arise soul. Attempts left: " + (2 - attempts));
                    }
                } else {
                    p.sendMessage("§cYou failed 3 times. You cannot arise this specific mob anymore.");
                }
            }
        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        // Recipe Check for Soul Healer
        // Center = Soul, Surround = Diamond
        // (Placeholder logic for optimized structure)
    }
}