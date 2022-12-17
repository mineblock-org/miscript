package org.mineblock.miscript;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.crayne.mi.Mi;
import org.crayne.mi.bytecode.common.ByteCodeInstruction;
import org.crayne.mi.bytecode.communication.MiCommunicator;
import org.crayne.mi.bytecode.communication.MiExecutionException;
import org.crayne.mi.bytecode.reader.ByteCodeInterpreter;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.command.MiscCommand;
import org.mineblock.miscript.script.MiScript;
import org.mineblock.miscript.script.event.MiscEventHandler;
import org.mineblock.miscript.script.std.MiscLib;
import org.mineblock.miscript.util.Colorization;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public final class MiScriptPlugin extends JavaPlugin {

    private static String version;

    private static final Map<Integer, Set<MiScript>> scriptsWithPriorities = new HashMap<>();
    private static final Map<MiScript, MiCommunicator> compiledScripts = new ConcurrentHashMap<>();

    public static Map<MiScript, MiCommunicator> compiledScripts() {
        return compiledScripts;
    }

    public void onEnable() {
        log(">>> Loading MiScript...", Level.INFO);
        MiscEventHandler.register();
        load();

        Objects.requireNonNull(getCommand("misc")).setExecutor(new MiscCommand());
        version = plugin().getDescription().getVersion();
    }

    public static void reenable() {
        compiledScripts.forEach(MiScriptPlugin::unloadScript);
        load();
    }

    public static void reload() {
        compiledScripts.forEach(MiScriptPlugin::reloadScript);
    }

    public static void load() {
        loadAllScripts();
        compileAllScripts();
        registerScriptEvents();
        finalizeAllScripts();
        registerAllScriptCommands();
    }

    public static Optional<MiScript> findScriptByName(@NotNull final String name) {
        return compiledScripts.keySet().stream().filter(m -> m.name().equals(name)).findFirst();
    }

    public static MiCommunicator communicatorOfScript(@NotNull final MiScript script) {
        return compiledScripts.get(script);
    }

    public static void disableScript(@NotNull final MiScript miScript, @NotNull final MiCommunicator communicator) {
        shutdownScript(miScript, communicator);
        unregisterScriptEventListeners(miScript);
        miScript.markAs(false);
    }

    public static void enableScript(@NotNull final MiScript miScript, @NotNull final MiCommunicator communicator) {
        executeScript(miScript, communicator);
        registerScriptCommands(miScript);
        registerScriptEventListeners(miScript, communicator);
        miScript.markAs(true);
    }

    public static void unloadScript(@NotNull final MiScript miScript, @NotNull final MiCommunicator communicator) {
        disableScript(miScript, communicator);
        Optional.ofNullable(scriptsWithPriorities.get(miScript.priority())).ifPresent(p -> p.remove(miScript));
        compiledScripts.remove(miScript);
    }

    public static void reloadScript(@NotNull final MiScript miScript, @NotNull final MiCommunicator communicator) {
        unloadScript(miScript, communicator);
        loadScript(miScript, miScript.name());
        compileScript(miScript);
        enableScript(miScript, communicator);
    }

    public static void loadScript(@NotNull final MiScript miScript, @NotNull final String name) {
        log("    Loading script '" + miScript.name() + "'", Level.INFO);
        log("    Script description: " + miScript.description(), Level.INFO);

        final boolean nameNull = miScript.name() == null;
        final boolean filepathNull = miScript.filepath() == null;
        final boolean moduleNull = miScript.module() == null;
        final boolean alreadyLoaded = scriptsWithPriorities.values().stream().anyMatch(sl-> sl.stream().anyMatch(s -> s.name().equals(miScript.name())));

        if (nameNull)      log("    Invalid miscript " + name + "; no name provided.", Level.SEVERE);
        if (filepathNull)  log("    Invalid miscript " + name + "; no script file provided.", Level.SEVERE);
        if (moduleNull)    log("    Invalid miscript " + name + "; no module provided.", Level.SEVERE);
        if (alreadyLoaded) log("    Invalid miscript " + name + "; already loaded. please use a unique name for your script.", Level.SEVERE);

        if (nameNull || filepathNull || moduleNull || alreadyLoaded) {
            log(Colorization.colorize("    Could not load script", Color.RED), Level.SEVERE);
            return;
        }
        scriptsWithPriorities.putIfAbsent(miScript.priority(), new HashSet<>());
        scriptsWithPriorities.get(miScript.priority()).add(miScript);
        log(Colorization.colorize("    Successfully loaded script", Color.GREEN), Level.INFO);
    }

    public static String version() {
        return version;
    }

    public static Plugin plugin() {
        return getPlugin(MiScriptPlugin.class);
    }

    public static <T> void log(@NotNull final T msg, @NotNull final Level level) {
        if (msg instanceof final Component comp) {
            switch (level.getName()) {
                case "SEVERE" -> plugin().getComponentLogger().error(comp);
                case "WARNING" -> plugin().getComponentLogger().warn(comp);
                default -> plugin().getComponentLogger().info(comp);
            }
            return;
        }
        plugin().getLogger().log(level, String.valueOf(msg));
    }

    private static void registerAllScriptCommands() {
        log(Colorization.colorize("Registering all script commands...", Color.GREEN.darker()), Level.INFO);
        compiledScripts.keySet().forEach(MiScriptPlugin::registerScriptCommands);
    }

    private static void registerScriptCommands(@NotNull final MiScript script) {
        log(Colorization.colorize("    Registering " + script.commands().size() + " command(s) for script '" + script.name() + "'...", Color.GREEN), Level.INFO);
        script.commands().forEach(r -> Bukkit.getServer().getCommandMap().register("misc", new Command(r.command()) {
            public boolean execute(@NotNull final CommandSender sender, @NotNull final String commandLabel, @NotNull final String[] args) {return false;}
        }));
    }

    private static void finalizeAllScripts() {
        log(Colorization.colorize("Executing all scripts...", Color.CYAN.darker()), Level.INFO);
        compiledScripts.forEach((s, c) -> {
            MiScriptPlugin.executeScript(s, c);
            s.markAs(true);
        });
    }

    private static void registerScriptEvents() {
        log(Colorization.colorize("Registering all script event listeners...", Color.CYAN.darker()), Level.INFO);
        compiledScripts.forEach(MiScriptPlugin::registerScriptEventListeners);
    }

    private static void registerScriptEventListeners(@NotNull final MiScript script, @NotNull final MiCommunicator communicator) {
        log(Colorization.colorize("    Registering " + script.listeners().size() + " eventlistener(s) for script '" + script.name() + "'...", Color.CYAN), Level.INFO);
        script.listeners().forEach(l -> MiscEventHandler.registerMiscEventListener(communicator, script, l));
    }

    private static void unregisterScriptEventListeners(@NotNull final MiScript script) {
        script.listeners().forEach(l -> MiscEventHandler.unregisterMiscEventListener(script, l));
    }

    private static void executeScript(@NotNull final MiScript script, @NotNull final MiCommunicator communicator) {
        log("    Executing script '" + script.name() + "'...", Level.INFO);
        new Thread(() -> {
            try {
                communicator.invoke(script.module(), "on_enable");
            } catch (final MiExecutionException e) {
                if (e.getMessage().startsWith("Could not find the Mi function"))
                    return; // ignore when the on_enable function does not exist
                log("    Error executing script: " + e.getMessage(), Level.SEVERE);
            }
        }).start();
    }

    private static void shutdownScript(@NotNull final MiScript script, @NotNull final MiCommunicator communicator) {
        log("    Shutting down script '" + script.name() + "'...", Level.INFO);
        forceShutdown(script, communicator);
    }

    private static void forceShutdown(@NotNull final MiScript script, @NotNull final MiCommunicator communicator) {
        boolean printedBusyMessage = false;
        while (true) {
            try {
                communicator.invoke(script.module(), "on_disable");
                break;
            } catch (final MiExecutionException e) {
                if (e.getMessage().startsWith("Could not find the Mi function")) break; // ignore when the on_enable function does not exist
                if (e.getMessage().startsWith("Cannot run multiple Mi functions at once") && !printedBusyMessage) {
                    printedBusyMessage = true;
                    log("    Script is busy; forcing shutdown...", Level.WARNING);
                    communicator.forceShutdown();
                    continue;
                }
                log("    Error shutting down script: " + e.getMessage(), Level.SEVERE);
                throw e;
            }
        }
    }

    private static void compileAllScripts() {
        log(Colorization.colorize("Compiling scripts that need to be compiled...", Color.GRAY), Level.INFO);
        scriptsWithPriorities.values().forEach(sl -> sl.forEach(MiScriptPlugin::compileScript));
    }

    private static void compileScript(@NotNull final MiScript script) {
        log("    Compiling script '" + script.name() + "'...", Level.INFO);
        final String fp = script.filepath();
        final File scriptFile = new File(fp);

        if (!scriptFile.isFile()) {
            log("Cannot find script file '" + fp + "', aborting compilation.", Level.SEVERE);
            return;
        }
        final Mi mi = new Mi(System.out, true);

        try {
            final String scriptString = Files.readString(scriptFile.toPath()).replace("$NAME", script.name());
            final List<ByteCodeInstruction> instrs = mi.compile(MiscLib.library().replace("$NAME", script.name()), scriptString);
            if (instrs.isEmpty()) {
                log("Could not compile script.", Level.SEVERE); // the mi messagehandler shows enough info already
                return;
            }

            final ByteCodeInterpreter runtime = new ByteCodeInterpreter(instrs, mi.messageHandler());
            final MiCommunicator communicator = new MiCommunicator(runtime);
            compiledScripts.put(script, communicator);
            log(Colorization.colorize("    Compiled script successfully", Color.GREEN), Level.INFO);
        } catch (final Exception e) {
            log("Error when compiling script '" + script.name() + "': " + e.getMessage(), Level.SEVERE);
            e.printStackTrace();
        }
    }

    private static void loadAllScripts() {
        log(Colorization.colorize("Loading all miscripts...", Color.GRAY), Level.INFO);
        final boolean scriptFolderLoaded = createScriptFolder();
        final File scriptFolder = scriptFolder();

        if (!scriptFolderLoaded || !scriptFolder.isDirectory()) {
            log("Could not load scripts! Script folder was not found.", Level.SEVERE);
            return;
        }
        Arrays.stream(scriptInfoFiles()).forEach(MiScriptPlugin::loadScript);
    }

    public static boolean isScriptInfoFile(@NotNull final File f) {
        return f.getName().endsWith(".toml");
    }

    private static void loadScript(@NotNull final File script) {
        if (!isScriptInfoFile(script)) return;

        final MiScript miScript = MiScript.tomlRead(script);
        loadScript(miScript, script.getName());
    }

    private static boolean createScriptFolder() {
        final File scriptFolder = scriptFolder();
        if (scriptFolder.exists()) return true;
        return scriptFolder.mkdir();
    }

    private static File[] scriptInfoFiles() {
        return Optional.ofNullable(scriptFolder().listFiles()).orElse(new File[0]);
    }

    private static File scriptFolder() {
        return new File(serverFolder(), "miscript");
    }

    private static File serverFolder() {
        return Bukkit.getServer().getPluginsFolder().getParentFile();
    }

}
