package com.trafalcraft.combatTag;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.trafalcraft.combatTag.object.PlayerTag;
import com.trafalcraft.combatTag.object.PlayerTagController;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

        private static PlayerTagController ptc;
        private static JavaPlugin plugin;
        private static boolean worldGuard;
        private static boolean useBossBar;

        private static int timeForFight;

        public void onEnable() {
                plugin = this;
                Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
                ptc = new PlayerTagController();

                worldGuard = getWorldGuard() != null;

                plugin.getConfig().options().copyDefaults(true);
                plugin.saveDefaultConfig();
                plugin.reloadConfig();

                useBossBar = plugin.getConfig().getBoolean("Settings.use_boss_bar");
                timeForFight = plugin.getConfig().getInt("Settings.time_for_fight_in_second");



                try {
                        Class.forName("org.bukkit.boss.BossBar");
                } catch (ClassNotFoundException e) {
                        this.getLogger().info("The BossBar need Spigot 1.9 or more, you are in "
                                +Bukkit.getBukkitVersion());
                        this.getLogger().info("BossBar feature disable");
                        useBossBar = false;
                }
        }

        public void onDisable() {
                for (PlayerTag playerTag : ptc.getAll()) {
                        playerTag.stopTask();
                }

        }

        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                return false;
        }

        public static PlayerTagController getPtc() {
                return ptc;
        }

        public static JavaPlugin getPlugin() {
                return plugin;
        }

        public static boolean hasWorldGuard() {
                return worldGuard;
        }

        public static WorldGuardPlugin getWorldGuard() {
                Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

                // WorldGuard may not be loaded
                if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
                        return null; // Maybe you want throw an exception instead
                }

                return (WorldGuardPlugin) plugin;
        }

        public static boolean canUseBossBar() {
                return useBossBar;
        }

        public static int getTimeForFight() {
                return timeForFight;
        }
}
