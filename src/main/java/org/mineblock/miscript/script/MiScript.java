package org.mineblock.miscript.script;

import com.moandjiezana.toml.Toml;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.script.event.MiscEventListener;
import org.mineblock.miscript.script.event.MiscRegisteredCommand;
import org.mineblock.miscript.script.timer.MiscTimerTask;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class MiScript {

    private final String name;
    private final List<String> authors;
    private final String description;
    private final String module;
    private final String filepath;
    private final Integer priority;
    private final List<MiscEventListener> listeners;
    private final List<MiscRegisteredCommand> commands;
    private final List<MiscTimerTask> timers;
    private volatile boolean active;

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, final int priority, @NotNull final List<MiscRegisteredCommand> commands,
                    @NotNull final Collection<MiscEventListener> listeners, @NotNull final Collection<MiscTimerTask> timers, @NotNull final String description, @NotNull final String... authors) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.authors = new ArrayList<>(Arrays.asList(authors));
        this.priority = priority;
        this.filepath = filepath;
        this.listeners = new ArrayList<>(listeners);
        this.commands = new ArrayList<>(commands);
        this.timers = new ArrayList<>(timers);
    }

    public void markAs(final boolean active) {
        this.active = active;
    }

    public boolean active() {
        return active;
    }

    public List<String> authors() {
        return Optional.ofNullable(authors).orElse(Collections.emptyList());
    }

    public String description() {
        return Optional.ofNullable(description).orElse("[no description provided]");
    }

    public String module() {
        return module;
    }

    public String name() {
        return name;
    }

    public int priority() {
        return Optional.ofNullable(priority).orElse(1000);
    }

    public String filepath() {
        return filepath;
    }

    public List<MiscEventListener> listeners() {
        return Optional.ofNullable(listeners).orElse(Collections.emptyList());
    }

    public List<MiscRegisteredCommand> commands() {
        return Optional.ofNullable(commands).orElse(Collections.emptyList());
    }

    public List<MiscTimerTask> timers() {
        return Optional.ofNullable(timers).orElse(Collections.emptyList());
    }

    private static Toml createToml(@NotNull final InputStream inputStream) {
        return new Toml().read(inputStream);
    }

    private static Toml createToml(@NotNull final File tomlFile) {
        return new Toml().read(tomlFile);
    }

    private static Toml createToml(@NotNull final String tomlString) {
        return new Toml().read(tomlString);
    }

    public static MiScript tomlRead(@NotNull final InputStream inputStream) {
        return tomlRead(createToml(inputStream));
    }

    public static MiScript tomlRead(@NotNull final File tomlFile) {
        return tomlRead(createToml(tomlFile));
    }

    public static MiScript tomlRead(@NotNull final String tomlString) {
        return tomlRead(createToml(tomlString));
    }

    public static MiScript tomlRead(@NotNull final Toml toml) {
        return toml.to(MiScript.class);
    }

    public String toString() {
        return "MiScript{" +
                "name='" + name + '\'' +
                ", authors=" + authors +
                ", description='" + description + '\'' +
                ", module='" + module + '\'' +
                '}';
    }

}
