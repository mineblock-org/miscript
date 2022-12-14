package org.mineblock.miscript.script.event;

import org.crayne.mi.bytecode.communication.MiCommunicator;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.script.MiScript;

public record RegisteredMiscEventListener(@NotNull MiScript script, @NotNull MiCommunicator communicator) { }
