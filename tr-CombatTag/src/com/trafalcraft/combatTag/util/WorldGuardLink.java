package com.trafalcraft.combatTag.util;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.trafalcraft.combatTag.Main;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WorldGuardLink {

        public static boolean pvpNotAllowed(Player p) {
                LocalPlayer lp = Objects.requireNonNull(Main.getWorldGuard()).wrapPlayer(p);
                ApplicableRegionSet set = Objects.requireNonNull(Main.getWorldGuard()).getRegionManager(p.getWorld())
                        .getApplicableRegions(p.getLocation());
                return !set.testState(lp, DefaultFlag.PVP);
        }
}
