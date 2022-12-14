package org.mineblock.miscript.script;

import com.moandjiezana.toml.Toml;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.script.event.MiscEventListener;

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

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, final int priority,
                    @NotNull final Collection<MiscEventListener> listeners, @NotNull final String description, @NotNull final String... authors) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.authors = Arrays.asList(authors);
        this.priority = priority;
        this.filepath = filepath;
        this.listeners = new ArrayList<>(listeners);
    }

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, final int priority,
                    @NotNull final Collection<MiscEventListener> listeners, @NotNull final String description, @NotNull final Collection<String> authors) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.authors = new ArrayList<>(authors);
        this.priority = priority;
        this.filepath = filepath;
        this.listeners = new ArrayList<>(listeners);
    }

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, final int priority, @NotNull final String description, @NotNull final String... authors) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.authors = Arrays.asList(authors);
        this.priority = priority;
        this.filepath = filepath;
        this.listeners = new ArrayList<>();
    }

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, final int priority, @NotNull final String description, @NotNull final Collection<String> authors) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.authors = new ArrayList<>(authors);
        this.priority = priority;
        this.filepath = filepath;
        this.listeners = new ArrayList<>();
    }

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, @NotNull final String description, @NotNull final Collection<String> authors) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.authors = new ArrayList<>(authors);
        this.priority = 1000;
        this.filepath = filepath;
        this.listeners = new ArrayList<>();
    }

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, @NotNull final String... authors) {
        this.name = name;
        this.description = "[no description provided]";
        this.module = module;
        this.authors = Arrays.asList(authors);
        this.priority = 1000;
        this.filepath = filepath;
        this.listeners = new ArrayList<>();
    }

    public MiScript(@NotNull final String name, @NotNull final String filepath, @NotNull final String module, @NotNull final Collection<String> authors) {
        this.name = name;
        this.description = "[no description provided]";
        this.module = module;
        this.authors = new ArrayList<>(authors);
        this.priority = 1000;
        this.filepath = filepath;
        this.listeners = new ArrayList<>();
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
