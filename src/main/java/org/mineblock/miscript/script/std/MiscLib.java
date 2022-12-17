package org.mineblock.miscript.script.std;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.crayne.mi.lang.MiCallable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static org.mineblock.miscript.MiScriptPlugin.plugin;

@SuppressWarnings("unused")
public class MiscLib {

    public static String library() {
        String lib = """
pub? true = 1b;
pub? false = 0b;

mod std {

	pub nat fn println(string s) -> "$stdclass";
    pub nat fn print(string s) -> "$stdclass";
    pub nat fn println(int i) -> "$stdclass";
    pub nat fn print(int i) -> "$stdclass";
    pub nat fn println(double d) -> "$stdclass";
    pub nat fn print(double d) -> "$stdclass";
    pub nat fn println(float f) -> "$stdclass";
    pub nat fn print(float f) -> "$stdclass";
    pub nat fn println(long l) -> "$stdclass";
    pub nat fn print(long l) -> "$stdclass";
    pub nat fn println(bool b) -> "$stdclass";
    pub nat fn print(bool b) -> "$stdclass";
    pub nat fn println(char c) -> "$stdclass";
    pub nat fn print(char c) -> "$stdclass";
    pub nat fn sleep(long millis) -> "$stdclass";
    pub nullable nat fn random_uuid_long :: long() -> "$stdclass";

}

mod strings {
    
    pub nat fn length :: int (string s) -> "$stdstring";
    
    pub nat fn index_of :: int (string s, string s2) -> "$stdstring";
    pub nat fn index_ofc :: int (string s, char c) -> "$stdstring";
    pub nat fn lindex_of :: int (string s, string s2) -> "$stdstring";
    pub nat fn lindex_ofc :: int (string s, char c) -> "$stdstring";
    pub nat fn replace :: string (string s, string find, string replace) -> "$stdstring";
    pub nat fn replace_all :: string (string s, string find, string replace) -> "$stdstring";
    
    pub nat nullable fn char_at :: char (string s, int i) -> "$stdstring";
    pub nat nullable fn substr :: string (string s, int i) -> "$stdstring";
    pub nat nullable fn substr :: string (string s, int i, int j) -> "$stdstring";
    
    pub nat fn substr_bf :: string (string s, string s2) -> "$stdstring";
    pub nat fn substr_bfl :: string (string s, string s2) -> "$stdstring";
    pub nat fn substr_af :: string (string s, string s2) -> "$stdstring";
    pub nat fn substr_afl :: string (string s, string s2) -> "$stdstring";
    
    pub nat fn ends_with :: bool (string s, string s2) -> "$stdstring";
    pub nat fn starts_with :: bool (string s, string s2) -> "$stdstring";
    pub nat fn contains :: bool (string s, string s2) -> "$stdstring";
    
    pub nat fn repeat :: string (string s, int a) -> "$stdstring";
    
}

mod files {
    
    pub nat fn delete :: bool (string path) -> "$stdfiles";
    pub nat fn list_amt :: int (string path) -> "$stdfiles";
    pub nat nullable fn list_get :: string (string path, int index) -> "$stdfiles";
    pub nat fn is_file :: bool (string path) -> "$stdfiles";
    pub nat fn is_directory :: bool (string path) -> "$stdfiles";
    pub nat fn exists :: bool (string path) -> "$stdfiles";
    
    pub nat fn lock_filelist(string path) -> "$stdfiles";
    pub nat fn unlock_filelist(string path) -> "$stdfiles";
    pub nat fn is_locked_filelist :: bool (string path) -> "$stdfiles";
    
}

mod misc {

    pub nat fn opped(string playername, bool b) -> "$stdmisc";
    pub nat fn opped(string name_or_uuid, bool b, bool uuid) -> "$stdmisc";
    pub nat fn is_opped :: bool(string playername) -> "$stdmisc";
    pub nat fn is_opped :: bool(string name_or_uuid, bool uuid) -> "$stdmisc";
    
    pub nat fn gamemode(string playername, string mode) -> "$stdmisc";
    pub nat fn gamemode(string name_or_uuid, string mode, bool uuid) -> "$stdmisc";
    
    pub nat fn player_amount :: int() -> "$stdmisc";
    pub nat nullable fn player :: string(int index) -> "$stdmisc";
    pub nat nullable fn player_uuid :: string(int index) -> "$stdmisc";
    
    pub nat fn message(string playername, string message) -> "$stdmisc";
    pub nat fn message(string name_or_uuid, string message, bool uuid) -> "$stdmisc";
    pub nat fn broadcast_message(string message) -> "$stdmisc";
    
    pub nat fn teleport(string playername, double x, double y, double z) -> "$stdmisc";
    pub nat fn teleport(string name_or_uuid, bool uuid, double x, double y, double z) -> "$stdmisc";
    pub nat fn teleport(string playername, double x, double y, double z, string world) -> "$stdmisc";
    pub nat fn teleport(string name_or_uuid, bool uuid, double x, double y, double z, string world) -> "$stdmisc";
    
    pub nat fn kick(string playername, string message) -> "$stdmisc";
    pub nat fn kick(string name_or_uuid, string message, bool uuid) -> "$stdmisc";
    
    pub nat fn ban(string playername, string reason) -> "$stdmisc";
    pub nat fn unban(string playername) -> "$stdmisc";
    
    pub nat fn ban_ip(string playername, string reason) -> "$stdmisc";
    pub nat fn unban_ip(string playername) -> "$stdmisc";
    
    pub nat fn is_online :: bool(string playername) -> "$stdmisc";
    pub nat fn is_online :: bool(string name_or_uuid, bool uuid) -> "$stdmisc";
    
    pub nat nullable fn uuid_by_name :: string(string playername) -> "$stdmisc";
    
    pub nat nullable fn player_pos_x :: double(string playername) -> "$stdmisc";
    pub nat nullable fn player_pos_x :: double(string name_or_uuid, bool uuid) -> "$stdmisc";
    
    pub nat nullable fn player_pos_y :: double(string playername) -> "$stdmisc";
    pub nat nullable fn player_pos_y :: double(string name_or_uuid, bool uuid) -> "$stdmisc";
    
    pub nat nullable fn player_pos_z :: double(string playername) -> "$stdmisc";
    pub nat nullable fn player_pos_z :: double(string name_or_uuid, bool uuid) -> "$stdmisc";
    
    pub nat nullable fn player_pos_world :: string(string playername) -> "$stdmisc";
    pub nat nullable fn player_pos_world :: string(string name_or_uuid, bool uuid) -> "$stdmisc";
    
    pub nat nullable fn get_gamemode :: string(string playername) -> "$stdmisc";
    pub nat nullable fn get_gamemode :: string(string name_or_uuid, bool uuid) -> "$stdmisc";
    
    pub nat fn banlist_by_name :: string() -> "$stdmisc";
    pub nat fn banlist_by_ip :: string() -> "$stdmisc";
    
    pub nat fn banlist_size_name :: int() -> "$stdmisc";
    pub nat fn banlist_size_ip :: int() -> "$stdmisc";
    
    pub nat nullable fn banned_name :: string(int index) -> "$stdmisc";
    pub nat nullable fn banned_ip :: string(int index) -> "$stdmisc";
    
    mod blocks {

        pub nat nullable fn get_block_at :: string(int x, int y, int z, string world) -> "$stdib";
        pub nat fn set_block_at :: bool(int x, int y, int z, string world, string mat) -> "$stdib";

    }
    
    mod items {
    
        pub nat nullable fn new_item :: string(string mat, int amt) -> "$stdib";
        
        pub nat nullable fn get_item_type :: string(nullable string item) -> "$stdib";
        pub nat nullable fn set_item_type :: string(nullable string item, string mat) -> "$stdib";
        
        pub nat nullable fn get_item_amount :: int(nullable string item) -> "$stdib";
        pub nat nullable fn set_item_amount :: string(nullable string item, int amt) -> "$stdib";
        
        pub nat nullable fn get_player_item :: string(string player, bool uuid, int slot) -> "$stdib";
        pub nat fn set_player_item :: bool(string player, bool uuid, int slot, nullable string item) -> "$stdib";
        
        pub nat nullable fn get_player_hand_slot :: int(string player, bool uuid) -> "$stdib";
    
    }
    
}

mod console {
    
    priv nat fn log(string scr, string s) -> "$stdmisc";
    priv nat fn warn(string scr, string s) -> "$stdmisc";
    priv nat fn error(string scr, string s) -> "$stdmisc";
    
    pub fn log(string s) {
        log("$NAME", s);
    }
    
    pub fn warn(string s) {
        warn("$NAME", s);
    }
    
    pub fn error(string s) {
        error("$NAME", s);
    }
    
    pub fn plain(string s) {
        use strings;
        if contains(s, "\\n") {
            log("$NAME", "\\r" + replace(substr_bf(s, "\\n"), "\\r", "")
                    + repeat(" ", 27) + "\\n" + replace(substr_af(s, "\\n"), "\\r", ""));
            return;
        }
        log("$NAME", "\\r" + replace(s, "\\r", "") + repeat(" ", 27));
    }
        
}
""";

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            lib += """
mod worldguard {
    
    pub nat fn in_region :: bool (string player, string region, bool use_uuid) -> "$stdworldguard";
    
}
                    """.replace("$stdworldguard", MiscWorldguard.class.getName());
        }

        lib += "STANDARDLIB_MI_FINISH_CODE;";
        lib = lib
                .replace("$stdmisc", MiscLib.class.getName())
                .replace("$stdfiles", MiscFilesLib.class.getName())
                .replace("$stdib", MiscItemBlockLib.class.getName())
                .replace("$stdclass", MiscStd.class.getName())
                .replace("$stdstring", MiscString.class.getName());
        return lib;
    }

    @MiCallable
    public static void log(@NotNull final String script, @NotNull final String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "(" + script + ") "
                + "[INFO]: " + msg.replace("&", "§"));
    }

    @MiCallable
    public static void warn(@NotNull final String script, @NotNull final String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "(" + script + ") "
                + "[WARN]: " + msg.replace("&", "§"));
    }

    @MiCallable
    public static void error(@NotNull final String script, @NotNull final String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "(" + script + ") "
                + "[ERROR]: " + msg.replace("&", "§"));
    }

    @MiCallable
    public static void opped(@NotNull final String playername, @NotNull final Boolean b) {
        final Player online = Bukkit.getPlayer(playername);
        final OfflinePlayer offline = Bukkit.getOfflinePlayer(playername);

        if (online != null) {
            online.setOp(b);
            return;
        }
        offline.setOp(b);
    }

    @MiCallable
    public static void opped(@NotNull final String nameOrUUID, @NotNull final Boolean b, @NotNull final Boolean uuid) {
        if (!uuid) {
            opped(nameOrUUID, b);
            return;
        }
        final UUID asUUID = UUID.fromString(nameOrUUID);

        final Player online = Bukkit.getPlayer(asUUID);
        final OfflinePlayer offline = Bukkit.getOfflinePlayer(asUUID);

        if (online != null) {
            online.setOp(b);
            return;
        }
        offline.setOp(b);
    }

    @MiCallable
    public static void gamemode(@NotNull final String name, @NotNull final String gamemode) {
        gamemode(name, gamemode, false);
    }

    protected static Optional<Player> player(@NotNull final String nameOrUUID, final boolean uuid) {
        return Optional.ofNullable(uuid ? Bukkit.getPlayer(UUID.fromString(nameOrUUID)) : Bukkit.getPlayer(nameOrUUID));
    }

    @MiCallable
    public static void gamemode(@NotNull final String nameOrUUID, @NotNull final String gamemode, @NotNull final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        final Optional<GameMode> gm = Arrays.stream(GameMode.values()).filter(g -> g.name().equalsIgnoreCase(gamemode)).findFirst();

        if (online.isEmpty() || gm.isEmpty()) return;
        Bukkit.getScheduler().runTask(plugin(), () -> online.get().setGameMode(gm.get()));
    }

    @MiCallable
    public static Integer player_amount() {
        return Bukkit.getOnlinePlayers().size();
    }

    @MiCallable
    public static String player(@NotNull final Integer index) {
        try {
            return new ArrayList<>(Bukkit.getOnlinePlayers()).get(index).getName();
        } catch (final IndexOutOfBoundsException e) {
            return null;
        }
    }

    @MiCallable
    public static String player_uuid(@NotNull final Integer index) {
        try {
            return new ArrayList<>(Bukkit.getOnlinePlayers()).get(index).getUniqueId().toString();
        } catch (final IndexOutOfBoundsException e) {
            return null;
        }
    }

    @MiCallable
    public static void message(@NotNull final String name, @NotNull final String message) {
        message(name, message, false);
    }

    @MiCallable
    public static void message(@NotNull final String nameOrUUID, @NotNull final String message, final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        if (online.isEmpty()) return;
        Bukkit.getScheduler().runTask(plugin(), () -> online.get().sendMessage(message.replace("&", "§")));
    }

    @MiCallable
    public static void broadcast_message(@NotNull final String message) {
        Bukkit.getScheduler().runTask(plugin(), () -> Bukkit.broadcast(Component.text(message.replace("&", "§"))));
    }

    @MiCallable
    public static Boolean is_opped(@NotNull final String playername) {
        final Player online = Bukkit.getPlayer(playername);
        final OfflinePlayer offline = Bukkit.getOfflinePlayer(playername);

        return online != null && online.isOp() || offline.isOp();
    }

    @MiCallable
    public static Boolean is_opped(@NotNull final String nameOrUUID, final Boolean uuid) {
        if (!uuid) return is_opped(nameOrUUID);

        final UUID asUUID = UUID.fromString(nameOrUUID);

        final Player online = Bukkit.getPlayer(asUUID);
        final OfflinePlayer offline = Bukkit.getOfflinePlayer(asUUID);

        return online != null && online.isOp() || offline.isOp();
    }

    @MiCallable
    public static void teleport(@NotNull final String name, @NotNull final Double x, @NotNull final Double y, @NotNull final Double z) {
        teleport(name, false, x, y, z);
    }

    @MiCallable
    public static void teleport(@NotNull final String nameOrUUID, @NotNull final Boolean uuid, @NotNull final Double x, @NotNull final Double y, @NotNull final Double z) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        if (online.isEmpty()) return;
        teleport(nameOrUUID, uuid, x, y, z, online.get().getWorld().getName());
    }

    @MiCallable
    public static void teleport(@NotNull final String name, @NotNull final Double x, @NotNull final Double y, @NotNull final Double z, @NotNull final String world) {
        teleport(name, false, x, y, z, world);
    }

    @MiCallable
    public static void teleport(@NotNull final String nameOrUUID, @NotNull final Boolean uuid, @NotNull final Double x, @NotNull final Double y, @NotNull final Double z, @NotNull final String world) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        if (online.isEmpty()) return;
        Bukkit.getScheduler().runTask(plugin(), () -> online.get().teleport(new Location(Bukkit.getWorld(world), x, y, z)));
    }

    @MiCallable
    @Nonnull
    public static Boolean is_online(@NotNull final String name) {
        return is_online(name, false);
    }

    @MiCallable
    @Nonnull
    public static Boolean is_online(@NotNull final String nameOrUUID, @NotNull final Boolean uuid) {
        return player(nameOrUUID, uuid).isPresent();
    }

    @MiCallable
    public static Double player_pos_x(@NotNull final String name) {
        return player_pos_x(name, false);
    }

    @MiCallable
    public static Double player_pos_x(@NotNull final String nameOrUUID, @NotNull final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        if (online.isEmpty()) return null;
        return online.get().getLocation().getX();
    }

    @MiCallable
    public static Double player_pos_y(@NotNull final String name) {
        return player_pos_y(name, false);
    }

    @MiCallable
    public static Double player_pos_y(@NotNull final String nameOrUUID, @NotNull final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        if (online.isEmpty()) return null;
        return online.get().getLocation().getY();
    }

    @MiCallable
    public static Double player_pos_z(@NotNull final String name) {
        return player_pos_z(name, false);
    }

    @MiCallable
    public static Double player_pos_z(@NotNull final String nameOrUUID, @NotNull final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        if (online.isEmpty()) return null;
        return online.get().getLocation().getZ();
    }

    @MiCallable
    public static String player_pos_world(@NotNull final String name) {
        return player_pos_world(name, false);
    }

    @MiCallable
    public static String player_pos_world(@NotNull final String nameOrUUID, @NotNull final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        return online.map(player -> player.getLocation().getWorld().getName()).orElse(null);
    }

    @MiCallable
    public static String get_gamemode(@NotNull final String name) {
        return get_gamemode(name, false);
    }

    @MiCallable
    public static String get_gamemode(@NotNull final String nameOrUUID, @NotNull final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        return online.map(player -> player.getGameMode().name().toLowerCase()).orElse(null);
    }

    @MiCallable
    public static void kick(@NotNull final String name, @NotNull final String message) {
        kick(name, message, false);
    }

    @MiCallable
    public static void kick(@NotNull final String nameOrUUID, @NotNull final String message, @NotNull final Boolean uuid) {
        final Optional<Player> online = player(nameOrUUID, uuid);
        if (online.isEmpty()) return;
        Bukkit.getScheduler().runTask(plugin(), () -> online.get().kick(Component.text(message.replace("&", "§"))));
    }

    @MiCallable
    public static void ban(@NotNull final String name, @NotNull final String reason) {
        Bukkit.getScheduler().runTask(plugin(), () -> {
            Bukkit.getBanList(BanList.Type.NAME).addBan(name, reason, null, null);
            final Player online = Bukkit.getPlayer(name);
            if (online != null) online.kick(Component.text(reason.replace("&", "§")));
        });
    }

    @MiCallable
    public static void unban(@NotNull final String name) {
        Bukkit.getScheduler().runTask(plugin(), () -> Bukkit.getBanList(BanList.Type.NAME).pardon(name));
    }

    @MiCallable
    public static void ban_ip(@NotNull final String ip, @NotNull final String reason) {
        Bukkit.getScheduler().runTask(plugin(), () -> {
            Bukkit.getBanList(BanList.Type.IP).addBan(ip, reason, null, null);
            Bukkit
                    .getOnlinePlayers()
                    .stream()
                    .filter(p -> Objects.requireNonNull(p.getAddress()).getAddress().toString().replace("/", "").equals(ip.replace("/", "")))
                    .forEach(p -> p.kick(Component.text(reason.replace("&", "§"))));
        });
    }

    @MiCallable
    public static void unban_ip(@NotNull final String ip) {
        Bukkit.getScheduler().runTask(plugin(), () -> Bukkit.getBanList(BanList.Type.IP).pardon(ip));
    }

    public static String banlist(@NotNull final BanList.Type type) {
        return Bukkit
                .getBanList(type)
                .getBanEntries()
                .stream()
                .map(b -> b.getTarget() + " banned by " + b.getSource() + " for '" + b.getReason() + "' at " + b.getCreated() + " until " + b.getExpiration())
                .collect(Collectors.joining("\n"));
    }

    public static String banlist_S(@NotNull final BanList.Type type, final int index) {
        final List<String> bans = Bukkit
                .getBanList(type)
                .getBanEntries()
                .stream()
                .map(BanEntry::getTarget).toList();

        return index < 0 || index >= bans.size() ? null : bans.get(index);
    }

    public static int ban_amount(@NotNull final BanList.Type type) {
        return Bukkit
                .getBanList(type)
                .getBanEntries().size();
    }

    @MiCallable
    @Nonnull
    public static String banlist_by_name() {
        return banlist(BanList.Type.NAME);
    }

    @MiCallable
    @Nonnull
    public static String banlist_by_ip() {
        return banlist(BanList.Type.IP);
    }

    @MiCallable
    @Nonnull
    public static Integer banlist_size_name() {
        return ban_amount(BanList.Type.NAME);
    }

    @MiCallable
    @Nonnull
    public static Integer banlist_size_ip() {
        return ban_amount(BanList.Type.IP);
    }

    @MiCallable
    public static String banned_name(@NotNull final Integer index) {
        return banlist_S(BanList.Type.NAME, index);
    }

    @MiCallable
    public static String banned_ip(@NotNull final Integer index) {
        return banlist_S(BanList.Type.IP, index);
    }

    @MiCallable
    public static String uuid_by_name(@NotNull final String name) {
        final Player online = Bukkit.getPlayer(name);
        final OfflinePlayer offline = Bukkit.getOfflinePlayer(name);

        return online != null ? online.getUniqueId().toString() : offline.getUniqueId().toString();
    }

}
