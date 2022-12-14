package org.mineblock.miscript.script.event;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MiscEventListener {

    private final Integer priority;
    private final String event;

    public MiscEventListener(@NotNull final String event, final Integer priority) {
        this.event = event;
        this.priority = priority;
    }

    public MiscEventListener(@NotNull final String event) {
        this.event = event;
        this.priority = 1000;
    }

    public String event() {
        return event;
    }

    public int priority() {
        return Optional.ofNullable(priority).orElse(1000);
    }

}
