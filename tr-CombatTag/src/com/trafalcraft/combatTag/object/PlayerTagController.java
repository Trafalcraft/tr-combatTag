package com.trafalcraft.combatTag.object;

import com.google.common.collect.Maps;
import com.trafalcraft.combatTag.Main;
import com.trafalcraft.combatTag.util.BossBarLink;
import com.trafalcraft.combatTag.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;

public class PlayerTagController {
        private final Map<String, PlayerTag> activeMap = Maps.newHashMap();

        public void addPlayer(String name) {
                Player p = Bukkit.getServer().getPlayer(name);
                if (p != null) {
                        PlayerTag playerTag;
                        if (Main.canUseBossBar()) {
                                BossBarLink bossBar = new BossBarLink();
                                bossBar.createBossbar(p);
                                playerTag = new PlayerTag(name, bossBar);
                                activeMap.put(name, playerTag);
                        }else{
                                playerTag = new PlayerTag(name);
                                activeMap.put(name, playerTag);
                        }
                        playerTag.runTask();
                        p.sendMessage(Msg.PREFIX + Msg.PLAYER_ENTER_IN_FIGHT.toString());
                }
        }

        public boolean contains(String name) {
                return this.activeMap.containsKey(name);
        }

        public void removePlayer(String name) {
                if (this.activeMap.containsKey(name)) {
                        getPlayer(name).stopTask();
                        activeMap.remove(name);
                }
        }

        public PlayerTag getPlayer(String name) {
                return activeMap.get(name);
        }

        public int size() {
                return activeMap.size();
        }

        public Map<String, PlayerTag> getHashMap() {
                return activeMap;
        }

        public Collection<PlayerTag> getAll() {
                return activeMap.values();
        }

}
