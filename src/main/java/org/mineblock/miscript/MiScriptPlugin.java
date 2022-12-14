package org.mineblock.miscript;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.crayne.mi.Mi;
import org.crayne.mi.bytecode.common.ByteCodeInstruction;
import org.crayne.mi.bytecode.communication.MiCommunicator;
import org.crayne.mi.bytecode.communication.MiExecutionException;
import org.crayne.mi.bytecode.reader.ByteCodeInterpreter;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.script.MiScript;
import org.mineblock.miscript.script.MiscLib;
import org.mineblock.miscript.util.Colorization;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

public final class MiScriptPlugin extends JavaPlugin {

    private static final Map<Integer, MiScript> scriptsWithPriorities = new HashMap<>();
    private static final Map<MiScript, MiCommunicator> compiledScripts = new HashMap<>();

    public void onEnable() {
        log(">>> Loading MiScript...", Level.INFO);
        loadAllScripts();
        compileAllScripts();
        finalizeAllScripts();
    }

    public void onDisable() {

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

    private static void finalizeAllScripts() {
        log(Colorization.colorize("Executing all scripts...", Color.CYAN.darker()), Level.INFO);
        compiledScripts.forEach(MiScriptPlugin::executeScript);
    }

    private static void executeScript(@NotNull final MiScript script, @NotNull final MiCommunicator communicator) {
        log("    Executing script '" + script.name() + "'...", Level.INFO);
        try {
            communicator.invoke(script.module(), "on_enable");
        } catch (final MiExecutionException e) {
            log("    Error executing script: " + e.getMessage(), Level.SEVERE);
        }
    }

    private static void compileAllScripts() {
        log(Colorization.colorize("Compiling scripts that need to be compiled...", Color.GRAY), Level.INFO);
        scriptsWithPriorities.values().forEach(MiScriptPlugin::compileScript);
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

    public static boolean isUncompiledMiScriptFile(@NotNull final File f) {
        return f.getName().endsWith(".misc");
    }

    public static boolean isCompiledMiScriptFile(@NotNull final File f) {
        return f.getName().endsWith(".cmisc");
    }

    public static boolean isMiScriptFile(@NotNull final File f) {
        return isUncompiledMiScriptFile(f) || isCompiledMiScriptFile(f);
    }

    private static void loadScript(@NotNull final File script) {
        if (!isScriptInfoFile(script)) return;

        final MiScript miScript = MiScript.tomlRead(script);
        log("    Loading script '" + miScript.name() + "'", Level.INFO);
        log("    Script description: " + miScript.description(), Level.INFO);

        final boolean nameNull = miScript.name() == null;
        final boolean filepathNull = miScript.filepath() == null;
        final boolean moduleNull = miScript.module() == null;
        final boolean alreadyLoaded = scriptsWithPriorities.values().stream().anyMatch(s -> s.name().equals(miScript.name()));

        if (nameNull)      log("    Invalid miscript " + script.getName() + "; no name provided.", Level.SEVERE);
        if (filepathNull)  log("    Invalid miscript " + script.getName() + "; no script file provided.", Level.SEVERE);
        if (moduleNull)    log("    Invalid miscript " + script.getName() + "; no module provided.", Level.SEVERE);
        if (alreadyLoaded) log("    Invalid miscript " + script.getName() + "; already loaded. please use a unique name for your script.", Level.SEVERE);

        if (nameNull || filepathNull || moduleNull || alreadyLoaded) {
            log(Colorization.colorize("    Could not load script", Color.RED), Level.SEVERE);
            return;
        }
        scriptsWithPriorities.put(miScript.priority(), miScript);
        log(Colorization.colorize("    Successfully loaded script", Color.GREEN), Level.INFO);
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
