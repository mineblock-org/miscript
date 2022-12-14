package org.mineblock.miscript.script.event;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.crayne.mi.bytecode.common.ByteDatatype;
import org.crayne.mi.bytecode.communication.MiCommunicator;
import org.crayne.mi.bytecode.communication.Value;
import org.jetbrains.annotations.NotNull;
import org.mineblock.miscript.MiScriptPlugin;
import org.mineblock.miscript.script.MiScript;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static java.util.Collections.reverseOrder;

public class MiscEventHandler implements Listener {

    private static final Map<String, Map<RegisteredMiscEventListener, Integer>> registeredListeners = new ConcurrentHashMap<>(); // map<eventname, map<listener, priority>>

    public static void register() {
        try (final ScanResult result = new ClassGraph().enableClassInfo().scan()) {
            final ClassInfoList infoList = result.getClassInfo(Event.class.getName())
                    .getSubclasses()
                    .filter(info -> !info.isAbstract());

            for (final ClassInfo event : infoList) {
                @SuppressWarnings("unchecked") final Class<? extends Event> eventClass = (Class<? extends Event>) Class.forName(event.getName());

                if (Arrays.stream(eventClass.getDeclaredMethods()).anyMatch(method ->
                        method.getParameterCount() == 0 && method.getName().equals("getHandlers"))) {

                    Bukkit.getPluginManager().registerEvent(eventClass, new MiscEventHandler(),
                            EventPriority.NORMAL, (listener, ev) -> handle(ev), MiScriptPlugin.plugin(), true);
                }
            }
        } catch (final Exception e) {
            MiScriptPlugin.log("Could not register global event handler: " + e.getMessage(), Level.SEVERE);
            e.printStackTrace();
        }
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
            final Object[] args = argumentsForEvent(event);
            try {
                final Optional<Value> result = l.communicator().invoke(l.script().module() + "." + event.getEventName(), args);

                if (result.isPresent() && result.get().value() != null
                        && result.get().type().byteDatatype().equals(ByteDatatype.BOOL)
                        && event instanceof final Cancellable cancellable) {

                    cancellable.setCancelled((boolean) result.get().value());
                }
            } catch (final Exception e) {
                MiScriptPlugin.log("Could not launch event for eventlistener " + event.getEventName() + " in script '" + l.script().name() + "': " + e.getMessage(), Level.SEVERE);
                MiScriptPlugin.log("Using args " + Arrays.toString(args) + " / " + Arrays.stream(args).map(o -> o.getClass().getName()).toList(), Level.SEVERE);
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
            case "PlayerRespawnEvent" -> new Object[] {((PlayerRespawnEvent) event).getPlayer().getUniqueId().toString()};
            case "EntityAirChangeEvent" -> new Object[] {((EntityAirChangeEvent) event).getEntity().getUniqueId().toString(), ((EntityAirChangeEvent) event).getEntity().getType().name()};
            case "FoodLevelChangeEvent" -> new Object[] {((FoodLevelChangeEvent) event).getEntity().getUniqueId().toString(), ((FoodLevelChangeEvent) event).getFoodLevel(), ((FoodLevelChangeEvent) event).getEntity().getFoodLevel()};
            default -> new Object[0];
        };
    }

}
