package org.mineblock.miscript.script.std;

import org.crayne.mi.lang.MiCallable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;

public class MiscMath {

    @MiCallable
    @Nonnull
    public static Double cos(@NotNull final Double x) {
        return Math.cos(x);
    }

    @MiCallable
    @Nonnull
    public static Double sin(@NotNull final Double x) {
        return Math.sin(x);
    }

    @MiCallable
    @Nonnull
    public static Double tan(@NotNull final Double x) {
        return Math.tan(x);
    }

    @MiCallable
    @Nonnull
    public static Double arccos(@NotNull final Double x) {
        return Math.acos(x);
    }

    @MiCallable
    @Nonnull
    public static Double arcsin(@NotNull final Double x) {
        return Math.asin(x);
    }

    @MiCallable
    @Nonnull
    public static Double arctan(@NotNull final Double x) {
        return Math.atan(x);
    }

    @MiCallable
    @Nonnull
    public static Double cosh(@NotNull final Double x) {
        return Math.cosh(x);
    }

    @MiCallable
    @Nonnull
    public static Double sinh(@NotNull final Double x) {
        return Math.sinh(x);
    }

    @MiCallable
    @Nonnull
    public static Double tanh(@NotNull final Double x) {
        return Math.tanh(x);
    }


    @MiCallable
    @Nonnull
    public static Double to_rad(@NotNull final Double x) {
        return Math.toRadians(x);
    }

    @MiCallable
    @Nonnull
    public static Double to_deg(@NotNull final Double x) {
        return Math.toDegrees(x);
    }

    @MiCallable
    @Nonnull
    public static Double ln(@NotNull final Double x) {
        return Math.log(x);
    }

    @MiCallable
    @Nonnull
    public static Double log10(@NotNull final Double x) {
        return Math.log10(x);
    }

    @MiCallable
    @Nonnull
    public static Double sqrt(@NotNull final Double x) {
        return Math.sqrt(x);
    }

    @MiCallable
    @Nonnull
    public static Double cbrt(@NotNull final Double x) {
        return Math.cbrt(x);
    }

    @MiCallable
    @Nonnull
    public static Double ceil(@NotNull final Double x) {
        return Math.ceil(x);
    }

    @MiCallable
    @Nonnull
    public static Double floor(@NotNull final Double x) {
        return Math.floor(x);
    }

    @MiCallable
    @Nonnull
    public static Double round(@NotNull final Double x, @NotNull final Integer n) {
        return Double.valueOf(new DecimalFormat("#" + (x > 0 ? "." + "#".repeat(n) : "")).format(x));
    }

    @MiCallable
    @Nonnull
    public static Double random() {
        return Math.random();
    }

    @MiCallable
    @Nonnull
    public static Double pow(@NotNull final Double x, @NotNull final Double y) {
        return Math.pow(x, y);
    }

    @MiCallable
    @Nonnull
    public static Double abs(@NotNull final Double x) {
        return Math.abs(x);
    }

    @MiCallable
    @Nonnull
    public static Double max(@NotNull final Double x, @NotNull final Double y) {
        return Math.max(x, y);
    }

    @MiCallable
    @Nonnull
    public static Double min(@NotNull final Double x, @NotNull final Double y) {
        return Math.min(x, y);
    }

}
