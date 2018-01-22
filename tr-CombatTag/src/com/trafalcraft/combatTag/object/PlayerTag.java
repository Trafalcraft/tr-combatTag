package com.trafalcraft.combatTag.object;

import com.trafalcraft.combatTag.Main;
import com.trafalcraft.combatTag.util.BossBarLink;
import com.trafalcraft.combatTag.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerTag {

        private int task;
        private boolean taskRun;
        private String player;
        private BossBarLink bossBar;
        private final double baseTimer;
        private double timer;

        public PlayerTag(String player, BossBarLink bossBar) {
                baseTimer = Main.getTimeForFight();
                timer = baseTimer;
                this.player = player;
                this.bossBar = bossBar;
                taskRun = false;
        }

        public PlayerTag(String player) {
                baseTimer = Main.getTimeForFight();
                timer = baseTimer;
                this.player = player;
                taskRun = false;
        }

        public void runTask() {
                taskRun = true;
                task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
                        if (Main.getPtc().contains(player)) {
                                if (timer == 0) {
                                        Main.getPtc().removePlayer(player);
                                        Player p = Bukkit.getServer().getPlayer(player);
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
                Player p = Bukkit.getServer().getPlayer(player);
                if (Main.canUseBossBar()) {
                        bossBar.removePlayer(p);
                }
                p.sendMessage(
                        Msg.PREFIX + Msg.PLAYER_NO_LONGER_IN_FIGHT.toString());
                Bukkit.getScheduler().cancelTask(task);
                taskRun = false;
        }

}
