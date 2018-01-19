package com.trafalcraft.combatTag.util;

import com.trafalcraft.combatTag.Main;
import org.bukkit.entity.Player;

public enum Msg {

        PREFIX("§f[§5CombatTag§f]>§5 "),
        NO_PERMISSIONS("§4ERROR §9§l> §r§bYou don't have permission to do that!"),
        COMMAND_USE("§9[§4CombatTag§9]> §r§cCommand use: §6$command"),

        PLAYER_ENTER_IN_FIGHT("§4You enter in a fight, don't leave the server."),
        PLAYER_NO_LONGER_IN_FIGHT("§4You are no longer in fight."),
        PLAYER_LEAVE_FIGHT_BROADCAST("§4$player have leave a fight !"),

        BOSS_BAR_TEXT("You are in fight : $timer s left!");

        private String value;

        Msg(String stringValue) {
                this.value = stringValue;
        }

        public static void sendHelp(Player p) {
                p.sendMessage("");
                p.sendMessage("§3§l-------------------Easter-------------------");
                p.sendMessage("§3/easter spawn §b- Spawn a new Easter rabbit at your position.");
                p.sendMessage("§3/easter addItem <itemName> - Add the item in your hand to the config file.");
                p.sendMessage("§3/combatTag reload - to reload the config file");
                p.sendMessage("§3------------------------------------------------");
                p.sendMessage("");
        }

        public static void load() {
                PREFIX.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.prefix")
                                .replace("&", "§"));
                NO_PERMISSIONS.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.no_permission")
                                .replace("&", "§"));
                COMMAND_USE.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.command_use")
                                .replace("&", "§"));
                PLAYER_ENTER_IN_FIGHT.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.player_enter_in_fight")
                                .replace("&", "§"));
                PLAYER_NO_LONGER_IN_FIGHT.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.player_no_longer_in_fight")
                                .replace("&", "§"));
                PLAYER_LEAVE_FIGHT_BROADCAST.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.player_leave_fight_broadcast")
                                .replace("&", "§"));
                BOSS_BAR_TEXT.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.bossBar_text")
                                .replace("&", "§"));
        }

        public String toString() {
                return value;
        }

        private void replaceBy(String value) {
                this.value = value;
        }
}