package org.mineblock.miscript.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.crayne.mi.bytecode.communication.MiCommunicator;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.MiScriptPlugin;
import org.mineblock.miscript.script.MiScript;
import org.mineblock.miscript.script.event.MiscRegisteredCommand;
import org.mineblock.miscript.script.std.MiscLib;
import org.mineblock.miscript.util.Colorization;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MiscCommand implements CommandExecutor {

    private static final Color MISCRIPT_COLOR = Color.decode("#bf42f5");

    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        if (!sender.isOp()) {
            MiscLib.unknownCommand(sender);
            return false;
        }

        if (args.length == 0) {
            sendVersion(sender);
            return true;
        }
        return switch (args[0]) {
            case "restart" -> restart(sender, args);
            case "list" -> listScripts(sender);
            case "version" -> sendVersion(sender);
            case "disable" -> disableScript(sender, args);
            case "enable" -> enableScript(sender, args);
            case "reload" -> reload(sender);
            case "info" -> showScriptInfo(sender, args);
            default -> false;
        };
    }

    private static boolean sendVersion(@NotNull final CommandSender sender) {
        sender.sendMessage(Colorization.colorize("You are using version " + MiScriptPlugin.version() + " of MiScript.", MISCRIPT_COLOR));
        return true;
    }

    private static boolean listScripts(@NotNull final CommandSender sender) {
        final List<MiScript> loadedScripts = MiScriptPlugin.compiledScripts().keySet().stream().toList();
        sender.sendMessage(Colorization.colorize("Currently active script(s) [" + loadedScripts.size() + "]: ", MISCRIPT_COLOR));
        sender.sendMessage(listScripts(loadedScripts));
        return true;
    }

    private static String listScripts(@NotNull final Collection<MiScript> scripts) {
        return scripts.stream().map(s -> (s.active() ? ChatColor.DARK_GREEN : ChatColor.DARK_RED) + s.name()).collect(Collectors.joining("§r, §r"));
    }

    private static boolean disableScript(@NotNull final CommandSender sender, @NotNull final String... args) {
        if (args.length < 2) {
            sender.sendMessage(Colorization.colorize("Expected 2 arguments, but got " + args.length + ". Usage: /misc disable <scriptname>", Color.RED));
            return false;
        }
        final String scriptStr = args[1];
        final Optional<MiScript> script = MiScriptPlugin.findScriptByName(scriptStr);
        if (script.isEmpty()) {
            sender.sendMessage(Colorization.colorize("Could not find any script called '" + scriptStr + "'.", Color.RED));
            return false;
        }
        final MiCommunicator communicator = MiScriptPlugin.communicatorOfScript(script.get());
        if (!script.get().active()) {
            sender.sendMessage(Colorization.colorize("Script '" + scriptStr + "' is already disabled. Use /misc reload <scriptname>, to reload the script instead.", Color.RED));
            return false;
        }

        MiScriptPlugin.disableScript(script.get(), communicator);
        sender.sendMessage(Colorization.colorize("Disabled script '" + scriptStr + "'.", Color.DARK_GRAY));
        return true;
    }

    private static boolean showScriptInfo(@NotNull final CommandSender sender, @NotNull final String... args) {
        if (args.length < 2) {
            sender.sendMessage(Colorization.colorize("Expected 2 arguments, but got " + args.length + ". Usage: /misc info <scriptname>", Color.RED));
            return false;
        }
        final String scriptStr = args[1];
        final Optional<MiScript> script = MiScriptPlugin.findScriptByName(scriptStr);
        if (script.isEmpty()) {
            sender.sendMessage(Colorization.colorize("Could not find any script called '" + scriptStr + "'.", Color.RED));
            return false;
        }
        sender.sendMessage("Script info for '" + scriptStr + "'...");
        sender.sendMessage("    Description: " + ChatColor.GRAY + script.get().description());
        sender.sendMessage("    Commands: " + ChatColor.GRAY + script.get().commands().stream().map(MiscRegisteredCommand::command).toList());
        sender.sendMessage("    Authors: " + ChatColor.DARK_GRAY + script.get().authors());
        sender.sendMessage("    Script File: " + ChatColor.DARK_GRAY + script.get().filepath());
        sender.sendMessage("    Currently Enabled: " + (script.get().active() ? ChatColor.DARK_GREEN : ChatColor.DARK_RED) + script.get().active());
        sender.sendMessage("    Priority: " + script.get().priority());
        return true;
    }

    private static boolean enableScript(@NotNull final CommandSender sender, @NotNull final String... args) {
        if (args.length < 2) {
            sender.sendMessage(Colorization.colorize("Expected 2 arguments, but got " + args.length + ". Usage: /misc enable <scriptname>", Color.RED));
            return false;
        }
        final String scriptStr = args[1];
        final Optional<MiScript> script = MiScriptPlugin.findScriptByName(scriptStr);
        if (script.isEmpty()) {
            sender.sendMessage(Colorization.colorize("Could not find any script called '" + scriptStr + "'.", Color.RED));
            return false;
        }
        final MiCommunicator communicator = MiScriptPlugin.communicatorOfScript(script.get());
        if (script.get().active()) {
            sender.sendMessage(Colorization.colorize("Script '" + scriptStr + "' is already enabled. Use /misc reload <scriptname>, to reload the script instead.", Color.RED));
            return false;
        }

        MiScriptPlugin.enableScript(script.get(), communicator);
        sender.sendMessage(Colorization.colorize("Enabled script '" + scriptStr + "'.", Color.GRAY));
        return true;
    }

    private static boolean restart(@NotNull final CommandSender sender, @NotNull final String... args) {
        if (args.length != 1) {
            final String scriptStr = args[1];
            final Optional<MiScript> script = MiScriptPlugin.findScriptByName(scriptStr);
            if (script.isEmpty()) {
                sender.sendMessage(Colorization.colorize("Could not find any script called '" + scriptStr + "'.", Color.RED));
                return false;
            }
            final MiCommunicator communicator = MiScriptPlugin.communicatorOfScript(script.get());
            sender.sendMessage(Colorization.colorize("Reloading script '" + scriptStr + "'...", Color.CYAN));
            MiScriptPlugin.reloadScript(script.get(), communicator);
            sender.sendMessage(Colorization.colorize("Successfully reloaded script.", Color.GREEN));
            return true;
        }
        sender.sendMessage(Colorization.colorize("Reloading all scripts...", Color.GREEN));
        MiScriptPlugin.reload();
        sender.sendMessage(Colorization.colorize("Successfully reloaded all scripts.", Color.GREEN));
        return true;
    }

    private static boolean reload(@NotNull final CommandSender sender) {
        sender.sendMessage(Colorization.colorize("Restarting MiScript...", Color.GREEN));
        MiScriptPlugin.reenable();
        sender.sendMessage(Colorization.colorize("Successfully restarted MiScript.", Color.GREEN));
        return true;
    }

}
