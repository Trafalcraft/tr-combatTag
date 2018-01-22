package com.trafalcraft.combatTag.util;

import com.trafalcraft.combatTag.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarLink {

        BossBar bossBar;

        public BossBar createBossbar(Player p){
                bossBar = Bukkit
                        .createBossBar(Msg.BOSS_BAR_TEXT.toString()
                                        .replace("$timer", ""
                                                + (int)  Main.getTimeForFight()), BarColor.RED
                                , BarStyle.SOLID);
                bossBar.addPlayer(p);
                bossBar.setVisible(true);
                return bossBar;
        }

        public void removePlayer(Player p){
                bossBar.removePlayer(p);
        }

        public void setTitle(String title) {
                bossBar.setTitle(title);
        }

        public void setProgress(double progress) {
                bossBar.setProgress(progress);
        }
}
