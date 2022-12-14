package org.mineblock.miscript.script;

import org.crayne.mi.lang.MiCallable;

import javax.annotation.Nonnull;
import java.util.UUID;

public class MiscStd {

    @MiCallable
    public static void println(@Nonnull final String str) {
        System.out.println(str);
    }

    @MiCallable
    public static void print(@Nonnull final String str) {
        System.out.print(str);
    }

    @MiCallable
    public static void println(@Nonnull final Integer i) {
        System.out.println(i);
    }

    @MiCallable
    public static void print(@Nonnull final Integer i) {
        System.out.print(i);
    }

    @MiCallable
    public static void println(@Nonnull final Double d) {
        System.out.println(d);
    }

    @MiCallable
    public static void print(@Nonnull final Double d) {
        System.out.print(d);
    }

    @MiCallable
    public static void println(@Nonnull final Float f) {
        System.out.println(f);
    }

    @MiCallable
    public static void print(@Nonnull final Float f) {
        System.out.print(f);
    }

    @MiCallable
    public static void println(@Nonnull final Long l) {
        System.out.println(l);
    }

    @MiCallable
    public static void print(@Nonnull final Long l) {
        System.out.print(l);
    }

    @MiCallable
    public static void println(@Nonnull final Boolean b) {
        System.out.println(b);
    }

    @MiCallable
    public static void print(@Nonnull final Boolean b) {
        System.out.print(b);
    }

    @MiCallable
    public static void println(@Nonnull final Character c) {
        System.out.println(c);
    }

    @MiCallable
    public static void print(@Nonnull final Character c) {
        System.out.print(c);
    }

    @MiCallable
    public static void sleep(@Nonnull final Long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    @MiCallable
    @Nonnull
    public static Long random_uuid_long() {
        return UUID.randomUUID().getMostSignificantBits();
    }

}
