package com.trafalcraft.combatTag.object;

import com.trafalcraft.combatTag.Main;
import com.trafalcraft.combatTag.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class PlayerTag {

        private int task;
        private boolean taskRun;
        private String players;
        private BossBar bossBar;
        private final double baseTimer;
        private double timer;

        public PlayerTag(String player) {
                baseTimer = Main.getTimeForFight();
                timer = baseTimer;
                players = player;
                Player p = Bukkit.getServer().getPlayer(player);
                if (p != null) {
                        if (Main.canUseBossBar()) {
                                bossBar = Bukkit
                                        .createBossBar(Msg.BOSS_BAR_TEXT.toString()
                                                        .replace("$timer", "" + (int) timer), BarColor.RED
                                                , BarStyle.SOLID);
                                bossBar.addPlayer(p);
                                bossBar.setVisible(true);
                        }
                        runTask();
                        Bukkit.getServer().getPlayer(player)
                                .sendMessage(Msg.PREFIX + Msg.PLAYER_ENTER_IN_FIGHT.toString());
                }
        }

        private void runTask() {
                taskRun = true;
                task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
                        if (Main.getPtc().contains(players)) {
                                if (timer == 0) {
                                        Main.getPtc().removePlayer(players);
                                        Player p = Bukkit.getServer().getPlayer(players);
                                        if (p != null) {
                                                if (Main.canUseBossBar()) {
                                                        bossBar.removePlayer(p);
                                                }
                                                p.sendMessage(
                                                        Msg.PREFIX + Msg.PLAYER_NO_LONGER_IN_FIGHT.toString());
                                        }
                                        stopTask();
                                } else {
                                        if (Main.canUseBossBar()) {
                                                bossBar.setTitle(Msg.BOSS_BAR_TEXT.toString()
                                                        .replace("$timer", "" + (int) timer));
                                                bossBar.setProgress(timer / baseTimer);
                                        }
                                        timer--;
                                }
                        }
                }, 0, 20);
        }

        public void updateTask() {
                timer = 15;
        }

        public boolean taskRun() {
                return taskRun;
        }

        public void stopTask() {
                Bukkit.getScheduler().cancelTask(task);
                taskRun = false;
        }

}
