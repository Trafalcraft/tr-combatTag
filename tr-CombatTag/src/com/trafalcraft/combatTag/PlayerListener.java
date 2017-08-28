package com.trafalcraft.combatTag;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class PlayerListener implements Listener{

	//recupere les entité frappé
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent e){
		if(e.getEntity().getType() ==  EntityType.PLAYER  && e.getDamager().getType() == EntityType.PLAYER){		//verifier que l'entité est un joueur
			Player p = (Player) e.getEntity();
			if(Main.hasWorldGuard()){
				LocalPlayer lp = Main.getWorldGuard().wrapPlayer(p);
				ApplicableRegionSet set = Main.getWorldGuard().getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
				if(!set.testState(lp, DefaultFlag.PVP)){
					return;
				}
			}
			if(Main.getPtc().contains(p.getName())){			//check si le joueur a déja été frappé
				Main.getPtc().getPlayer(p.getName()).updateTask();			//si oui mes a jour la valeur heure du coup
			}else{
				Main.getPtc().addPlayer(p.getName());												//Ajoute le joueur
			}
			p = (Player) e.getDamager();
			if(Main.getPtc().contains(p.getName())){			//check si le joueur a déja été frappé
				Main.getPtc().getPlayer(p.getName()).updateTask();			//si oui mes a jour la valeur heure du coup
			}else{
				Main.getPtc().addPlayer(p.getName());												//Ajoute le joueur
			}
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		if(Main.getPtc().contains(e.getPlayer().getName())){
			for(int i = 0;i<e.getPlayer().getInventory().getContents().length;i++){
				final ItemStack itemStack = e.getPlayer().getInventory().getContents()[i];
	            if(itemStack!=null){
	            	e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), itemStack);
	            }
			}
			Bukkit.getServer().broadcastMessage("§4"+e.getPlayer().getName()+" s'est déconnecté en combat !");
			Bukkit.getLogger().info("Deconnection de "+e.getPlayer().getName()+" aux coordonnees x:"+e.getPlayer().getLocation().getX()+" y:"+e.getPlayer().getLocation().getY()+" z:"+e.getPlayer().getLocation().getZ());
	    	e.getPlayer().getInventory().clear();
	    	e.getPlayer().damage(9999999);
	    	Main.getPtc().removePlayer(e.getPlayer().getName());
		}
	}
}
