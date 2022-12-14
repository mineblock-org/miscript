package org.mineblock.miscript.script;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.crayne.mi.lang.MiCallable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class MiscItemBlockLib {

    private static Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public static ItemStack itemFromJson(@NotNull final String json) throws JsonSyntaxException {
        return createGson().fromJson(json, ItemStack.class);
    }

    public static String itemToJson(final ItemStack itemStack) {
        return createGson().toJson(itemStack == null ? new ItemStack(Material.AIR) : itemStack);
    }

    @MiCallable
    public static String get_block_at(@NotNull final Integer x, @NotNull final Integer y, @NotNull final Integer z, @NotNull final String world) {
        try {
            final Optional<World> w = Optional.ofNullable(Bukkit.getWorld(world));
            final Block block = w.map(value -> value.getBlockAt(x, y, z)).orElse(null);
            return block == null ? null : block.getType().name().toLowerCase();
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    @Nonnull
    public static Boolean set_block_at(@NotNull final Integer x, @NotNull final Integer y, @NotNull final Integer z, @NotNull final String world, @NotNull final String mat) {
        try {
            final Optional<World> w = Optional.ofNullable(Bukkit.getWorld(world));
            final Block block = w.map(value -> value.getBlockAt(x, y, z)).orElse(null);
            if (block == null) return false;
            Optional.ofNullable(Material.getMaterial(mat.toUpperCase())).ifPresent(block::setType);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    @MiCallable
    public static String new_item(@NotNull final String material, @NotNull final Integer amount) {
        try {
            return Optional.ofNullable(Material.getMaterial(material.toUpperCase())).map(mat -> itemToJson(new ItemStack(mat, amount))).orElse(null);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    public static String get_item_type(final String itemJson) {
        try {
            return itemFromJson(itemJson).getType().name().toLowerCase();
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    public static String set_item_type(final String itemJson, @NotNull final String newType) {
        try {
            final ItemStack item = itemFromJson(itemJson);
            final Optional<Material> mat = Optional.ofNullable(Material.getMaterial(newType.toUpperCase()));
            mat.ifPresent(item::setType);
            return mat.isEmpty() ? null : itemToJson(item);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    public static Integer get_item_amount(final String itemJson) {
        try {
            return itemFromJson(itemJson).getAmount();
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    public static String set_item_amount(final String itemJson, @NotNull final Integer newAmount) {
        try {
            final ItemStack item = itemFromJson(itemJson);
            item.setAmount(newAmount);
            return itemToJson(item);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    public static String get_player_item(@NotNull final String player, @NotNull final Boolean uuid, @NotNull final Integer slot) {
        try {
            final Optional<Player> online = MiscLib.player(player, uuid);
            final ItemStack item = online.map(v -> v.getInventory().getItem(slot)).orElse(new ItemStack(Material.AIR));
            return online.map(value -> itemToJson(new ItemStack(item))).orElse(null);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    @Nonnull
    public static Boolean set_player_item(@NotNull final String player, @NotNull final Boolean uuid, @NotNull final Integer slot, @NotNull final String itemJson) {
        try {
            final Optional<Player> online = MiscLib.player(player, uuid);
            online.ifPresent(value -> value.getInventory().setItem(slot, itemFromJson(itemJson)));
            return true;
        } catch (final Exception ignored) {
            return false;
        }
    }

    @MiCallable
    public static Integer get_player_hand_slot(@NotNull final String player, @NotNull final Boolean uuid) {
        try {
            final Optional<Player> online = MiscLib.player(player, uuid);
            return online.map(value -> value.getInventory().getHeldItemSlot()).orElse(null);
        } catch (final Exception e) {
            return null;
        }
    }

}
