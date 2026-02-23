package com.amantrivedi.arise;

import org.bukkit.plugin.java.JavaPlugin;

public class ArisePlugin extends JavaPlugin {
    private static ArisePlugin instance;
    private AriseManager ariseManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        this.ariseManager = new AriseManager(this);
        this.scoreboardManager = new ScoreboardManager(this);

        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        
        Commands cmds = new Commands(this);
        getCommand("arise").setExecutor(cmds);
        getCommand("arisework").setExecutor(cmds);
        getCommand("soul").setExecutor(cmds);
        getCommand("soulcome").setExecutor(cmds);

        getLogger().info("AisePlugin based on Solo Leveling is Enabled! (v1.20-1.20.11 Support)");
    }

    @Override
    public void onDisable() {
        ariseManager.saveData();
        getLogger().info("AisePlugin Disabled!");
    }

    public static ArisePlugin getInstance() { return instance; }
    public AriseManager getManager() { return ariseManager; }
    public ScoreboardManager getScoreboardManager() { return scoreboardManager; }
}