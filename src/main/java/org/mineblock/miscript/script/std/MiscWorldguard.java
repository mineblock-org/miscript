package org.mineblock.miscript.script.std;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.entity.Player;
import org.crayne.mi.lang.MiCallable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class MiscWorldguard {

    public static boolean in_region(@NotNull final Player p, @NotNull final String region) {
        final Location loc = BukkitAdapter.adapt(p.getLocation());
        final ApplicableRegionSet set = WorldGuard
                .getInstance()
                .getPlatform()
                .getRegionContainer()
                .createQuery()
                .getApplicableRegions(loc);

        return set.getRegions().stream().anyMatch(r -> r.getId().equals(region));
    }

    @MiCallable
    @Nonnull
    public static Boolean in_region(@NotNull final String player, @NotNull final String region, @NotNull final Boolean use_uuid) {
        final Optional<Player> p = MiscLib.player(player, use_uuid);
        return p.isPresent() && in_region(p.get(), region);
    }

}
