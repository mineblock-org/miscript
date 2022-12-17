package org.mineblock.miscript.script.event;

import org.jetbrains.annotations.NotNull;

public class MiscRegisteredCommand {

    private final String command;
    private final boolean opOnly;

    public MiscRegisteredCommand(@NotNull final String command, final boolean opOnly) {
        this.command = command;
        this.opOnly = opOnly;
    }

    public MiscRegisteredCommand(@NotNull final String command) {
        this.command = command;
        this.opOnly = false;
    }

    public String command() {
        return command;
    }

    public boolean opOnly() {
        return opOnly;
    }

}
