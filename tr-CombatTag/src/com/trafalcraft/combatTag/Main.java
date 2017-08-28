package com.trafalcraft.combatTag;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.trafalcraft.combatTag.object.PlayerTagController;

public class Main extends JavaPlugin{
	
	private static PlayerTagController ptc;
	private static JavaPlugin plugin;
	private static boolean worldGuard;
	
	
	public void onEnable(){
		plugin = this;
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		ptc = new PlayerTagController();
		
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	    
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        worldGuard = false; // Maybe you want throw an exception instead
	    }else{
	    	worldGuard = true;
	    }
	}
	
	public void onDisable(){
		
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}
	
	public static PlayerTagController getPtc() {
		return ptc;
	}
	
	public static JavaPlugin getPlugin(){
		return plugin;
	}
	
	public static boolean hasWorldGuard(){
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
}
