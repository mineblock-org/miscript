package org.mineblock.miscript.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Colorization {

    public static <T> Component colorize(@NotNull final T text, @NotNull final Color c) {
        return Component.text(String.valueOf(text), Style.style(TextColor.color(c.getRed(), c.getGreen(), c.getBlue())));
    }

}
