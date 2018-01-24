package com.trafalcraft.combatTag;

import com.trafalcraft.combatTag.util.Msg;
import com.trafalcraft.combatTag.util.WorldGuardLink;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class PlayerListener implements Listener {

        @EventHandler
        public void onPlayerHit(EntityDamageByEntityEvent e) {
                if (e.getEntity().getType() == EntityType.PLAYER) {
                        Player p = (Player) e.getEntity();
                        LivingEntity damager = null;
                        if (e.getDamager().getType() == EntityType.PLAYER) {
                                damager = (Player) e.getDamager();
                        } else if (isClass("org.bukkit.entity.AreaEffectCloud")
                                && e.getDamager() instanceof AreaEffectCloud) {

                                ProjectileSource shooter = ((AreaEffectCloud) e.getDamager()).getSource();
                                damager = getLivingEntity(shooter);
                        } else if (e.getDamager() instanceof Projectile) {
                                ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
                                damager = getLivingEntity(shooter);
                        }else if(e.getDamager() instanceof LivingEntity){
                                if(Main.isWorkOnMobDamage()){
                                        damager = (LivingEntity) e.getDamager();
                                }
                        }
                        if (damager != null) {
                                if(damager instanceof Player){
                                        if (checkNoPvpRegion(p))
                                                return;
                                        if (checkNoPvpRegion((Player) damager))
                                                return;
                                        updatePtc((Player) damager);

                                }else{
                                        if(checkNoMobDamageRegion(p)){
                                                return;
                                        }
                                }
                                updatePtc(p);
                        }
                }
        }

        public boolean isClass(String className) {
                try  {
                        Class.forName(className);
                        return true;
                }  catch (ClassNotFoundException e) {
                        return false;
                }
        }


        private LivingEntity getLivingEntity(ProjectileSource shooter) {
                LivingEntity damager = null;
                if (shooter instanceof Player) {
                        damager = (Player) shooter;
                }else if(shooter instanceof LivingEntity){
                        if(Main.isWorkOnMobDamage()){
                                damager = (LivingEntity) shooter;
                        }
                }
                return damager;
        }

        private boolean checkNoPvpRegion(Player p) {
                return Main.hasWorldGuard() && WorldGuardLink.pvpNotAllowed(p);
        }

        private boolean checkNoMobDamageRegion(Player p) {
                return Main.hasWorldGuard() && WorldGuardLink.mobDamageNotAllowed(p);
        }

        private void updatePtc(Player p) {
                if (Main.getPtc().contains(p.getName())) {
                        Main.getPtc().getPlayer(p.getName()).updateTask();
                } else {
                        Main.getPtc().addPlayer(p.getName());
                }
        }

        @EventHandler
        public void onPlayerLeave(PlayerQuitEvent e) {
                if (Main.getPtc().contains(e.getPlayer().getName())) {
                        for (int i = 0; i < e.getPlayer().getInventory().getContents().length; i++) {
                                final ItemStack itemStack = e.getPlayer().getInventory().getContents()[i];
                                if (itemStack != null) {
                                        e.getPlayer().getWorld()
                                                .dropItemNaturally(e.getPlayer().getLocation(), itemStack);
                                }
                        }
                        Bukkit.getServer().broadcastMessage(Msg.PREFIX + Msg.PLAYER_LEAVE_FIGHT_BROADCAST.toString()
                                .replace("$player", e.getPlayer().getDisplayName()));
                        Bukkit.getLogger().info("The player " + e.getPlayer().getName()
                                + " have leave a fight at the location x:" + e.getPlayer().getLocation().getX()
                                + " y:" + e.getPlayer().getLocation().getY()
                                + " z:" + e.getPlayer().getLocation().getZ());
                        e.getPlayer().getInventory().clear();
                        e.getPlayer().damage(9999999);
                        Main.getPtc().removePlayer(e.getPlayer().getName());
                }
        }
}
