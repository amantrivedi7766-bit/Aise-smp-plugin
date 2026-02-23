package com.amantrivedi.arise;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
    private ArisePlugin plugin;

    public ScoreboardManager(ArisePlugin plugin) {
        this.plugin = plugin;
    }

    public void updateScoreboard(Player p) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("AriseStats", "dummy", "§5§lArise System");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int count = plugin.getManager().getSouls(p).size();
        Score s1 = obj.getScore("§7Captured Souls: §a" + count + "/10000");
        s1.setScore(2);

        Score s2 = obj.getScore("§7Status: §a" + (plugin.getManager().hasPower(p) ? "Awakened" : "Task Pending"));
        s2.setScore(1);

        p.setScoreboard(board);
    }
}