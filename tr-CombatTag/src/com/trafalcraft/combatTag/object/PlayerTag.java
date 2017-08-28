package com.trafalcraft.combatTag.object;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.trafalcraft.combatTag.Main;


public class PlayerTag {
	
	private int task;
	private boolean taskRun;
	private String players;

	public PlayerTag(String player){
		players = player;
		runTask();
		Player p = Bukkit.getServer().getPlayer(player);
		if(p == null){
			Bukkit.getServer().getPlayer(player).sendMessage("§4Vous êtes en combat ! Ne vous déconnectez pas avant le prochain message.");
		}
	}
	
	
	private void runTask(){
		taskRun = true;
		task = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(Main.getPtc().contains(players)){
					Main.getPtc().removePlayer(players);
					Player p = Bukkit.getServer().getPlayer(players);
					if(p == null){
						Bukkit.getServer().getPlayer(players).sendMessage("§4Vous n'êtes plus en combat.");
					}
				}
				stopTask();
			}
		}, 15*20);
	}
	
	public void updateTask(){
		Bukkit.getScheduler().cancelTask(task);
		runTask();
	}
	
	public boolean taskRun(){
		return taskRun;
	}
	
	public void stopTask(){
		Bukkit.getScheduler().cancelTask(task);
		taskRun = false;
	}
	
	
}
