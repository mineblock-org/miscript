package org.mineblock.miscript.script.event;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredListener;
import org.crayne.mi.bytecode.communication.MiCommunicator;
import org.crayne.mi.bytecode.communication.Type;
import org.crayne.mi.bytecode.communication.Value;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.MiScriptPlugin;
import org.mineblock.miscript.script.MiScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static java.util.Collections.reverseOrder;

public class MiscEventHandler implements Listener {

    private static final Map<String, Map<RegisteredMiscEventListener, Integer>> registeredListeners = new ConcurrentHashMap<>(); // map<eventname, map<listener, priority>>

    public static void register() {
        final RegisteredListener registeredListener = new RegisteredListener(new MiscEventHandler(), (listener, event) -> handle(event), EventPriority.NORMAL, MiScriptPlugin.plugin(), true);
        HandlerList.getHandlerLists().forEach(h -> h.register(registeredListener));
    }

    public static void unregister() {
        HandlerList.getHandlerLists().forEach(h -> h.unregister(MiScriptPlugin.plugin()));
    }

    public static void handle(@NotNull final Event event) {
        final List<RegisteredMiscEventListener> listeners = Optional.ofNullable(registeredListeners
                        .get(event.getEventName()))
                .map(map -> map
                        .entrySet()
                        .stream()
                        .sorted(reverseOrder(Map.Entry.comparingByValue()))
                        .map(Map.Entry::getKey)
                        .toList()
                ).orElse(new ArrayList<>());

        listeners.forEach(l -> {
            try {
                final Optional<Value> result = l.communicator().invoke(l.script().module() + "." + event.getEventName(), argumentsForEvent(event));

                if (result.isPresent() && result.get().value() != null
                        && result.get().type().equals(Type.of(Boolean.class))
                        && event instanceof final Cancellable cancellable) {

                    cancellable.setCancelled((boolean) result.get().value());
                }
            } catch (final Exception e) {
                MiScriptPlugin.log("Could not launch event for eventlistener " + event.getEventName() + " in script '" + l.script().name() + "': " + e.getMessage(), Level.SEVERE);
            }
        });
    }

    public static void registerMiscEventListener(@NotNull final MiCommunicator communicator, @NotNull final MiScript script, @NotNull final MiscEventListener eventListener) {
        registeredListeners.putIfAbsent(eventListener.event(), new ConcurrentHashMap<>());
        registeredListeners.get(eventListener.event()).put(new RegisteredMiscEventListener(script, communicator), eventListener.priority());
    }

    private static Object[] argumentsForEvent(@NotNull final Event event) {
        return switch (event.getEventName()) {
            case "PlayerJoinEvent" -> new Object[] {((PlayerJoinEvent) event).getPlayer().getUniqueId().toString()};
            default -> new Object[0];
        };
    }

}
