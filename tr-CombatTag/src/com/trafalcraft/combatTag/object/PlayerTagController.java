package com.trafalcraft.combatTag.object;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class PlayerTagController {
	private final Map<String, PlayerTag> activeMap = Maps.newHashMap();
	
	public void addPlayer(String name){
		activeMap.put(name, new PlayerTag(name));
	}
	
	public boolean contains(String name){
		if(this.activeMap.containsKey(name)){
			return true;
		}
		return false;
	}
	
	public void removePlayer(String name){
		if(this.activeMap.containsKey(name)){
			getPlayer(name).stopTask();
			activeMap.remove(name);
		}
	}
	
	public PlayerTag getPlayer(String name){
		return activeMap.get(name);
	}
	
	public int size(){
		return activeMap.size();
	}

	public Map<String, PlayerTag> getHashMap(){
		return activeMap;
	}
	
    public Collection<PlayerTag> getAll() {
        return activeMap.values();
    }
       
}
