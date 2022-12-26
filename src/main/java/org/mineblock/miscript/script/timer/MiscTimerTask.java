package org.mineblock.miscript.script.timer;

import org.bukkit.Bukkit;
import org.crayne.mi.bytecode.communication.MiCommunicator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineblock.miscript.MiScriptPlugin;
import org.mineblock.miscript.script.MiScript;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class MiscTimerTask {

    @Nullable
    private final Long startingDelay;
    @Nullable
    private final Long delay;
    @Nullable
    private final String function;

    public MiscTimerTask(@Nullable final Long startingDelay, @Nullable final Long delay, @Nullable final String function) {
        this.startingDelay = startingDelay;
        this.delay = delay;
        this.function = function;
    }

    public long delay() {
        return delay == null ? 0L : delay;
    }

    public long startingDelay() {
        return startingDelay == null ? 0L : startingDelay;
    }

    @Nullable
    public String function() {
        return function;
    }

    private static final Map<MiScript, Set<Integer>> registeredTasks = new ConcurrentHashMap<>();

    public static void unregisterTimerTasks(@NotNull final MiScript script) {
        Optional.ofNullable(registeredTasks.get(script)).ifPresent(s -> s.forEach(i -> Bukkit.getScheduler().cancelTask(i)));
    }

    public static void registerTimerTask(@NotNull final MiCommunicator communicator, @NotNull final MiScript script, @NotNull final MiscTimerTask task) {
        final int taskId = Bukkit.getScheduler().runTaskTimer(MiScriptPlugin.plugin(), () -> {
            final String func = task.function();
            if (func == null) {
                MiScriptPlugin.log("Script '" + script.name() + "' has wrongly defined timer task; No function provided!", Level.SEVERE);
                return;
            }
            communicator.newThread().invoke(func);
        }, task.startingDelay(), task.delay()).getTaskId();

        registeredTasks.putIfAbsent(script, new HashSet<>());
        registeredTasks.get(script).add(taskId);
    }

}
