package org.mineblock.miscript.script;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.crayne.mi.lang.MiCallable;
import org.jetbrains.annotations.NotNull;

public class MiscLib {

    public static String library() {
        return """
mod misc {
    
    pub nat fn broadcast(string s) -> "$miscstd";
    priv nat fn console_log(string scr, string s) -> "$miscstd";
    
    pub fn console_log(string s) {
        console_log("$NAME", s);
    }
    
}
STANDARDLIB_MI_FINISH_CODE;"""
                .replace("$miscstd", MiscLib.class.getName());
    }

    @MiCallable
    public static void broadcast(@NotNull final String s) {
        Bukkit.broadcast(Component.text(s.replace("&", "§")));
    }

    @MiCallable
    public static void console_log(@NotNull final String script, @NotNull final String msg) {
        Bukkit.getConsoleSender().sendMessage("(" + script + ") " + msg.replace("&", "§"));
    }

}
