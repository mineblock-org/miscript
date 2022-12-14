package org.mineblock.miscript.script;

import org.apache.commons.lang3.StringUtils;
import org.crayne.mi.lang.MiCallable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Collections;

public class MiscString {

    @MiCallable
    @Nonnull
    public static Integer length(@NotNull final String s) {
        return s.length();
    }

    @MiCallable
    @Nonnull
    public static Integer index_of(@NotNull final String s, @NotNull final String s2) {
        return s.indexOf(s2);
    }

    @MiCallable
    @Nonnull
    public static Integer index_ofc(@NotNull final String s, @NotNull final Character c) {
        return s.indexOf(c);
    }

    @MiCallable
    @Nonnull
    public static Integer lindex_of(@NotNull final String s, @NotNull final String s2) {
        return s.lastIndexOf(s2);
    }

    @MiCallable
    @Nonnull
    public static Integer lindex_ofc(@NotNull final String s, @NotNull final Character c) {
        return s.lastIndexOf(c);
    }

    @MiCallable
    @Nonnull
    public static String replace(@NotNull final String s, @NotNull final String s1, @NotNull final String s2) {
        return s.replace(s1, s2);
    }

    @MiCallable
    @Nonnull
    public static String replace_all(@NotNull final String s, @NotNull final String s1, @NotNull final String s2) {
        return s.replaceAll(s1, s2);
    }

    @MiCallable
    public static Character char_at(@NotNull final String s, @NotNull final Integer i) {
        try {
            return s.charAt(i);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    public static String substr(@NotNull final String s, @NotNull final Integer i) {
        try {
            return s.substring(i);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    public static String substr(@NotNull final String s, @NotNull final Integer i, @NotNull final Integer j) {
        try {
            return s.substring(i, j);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    @Nonnull
    public static String substr_bf(@NotNull final String s, @NotNull final String s2) {
        return StringUtils.substringBefore(s, s2);
    }

    @MiCallable
    @Nonnull
    public static String substr_bfl(@NotNull final String s, @NotNull final String s2) {
        return StringUtils.substringBeforeLast(s, s2);
    }

    @MiCallable
    @Nonnull
    public static String substr_af(@NotNull final String s, @NotNull final String s2) {
        return StringUtils.substringAfter(s, s2);
    }

    @MiCallable
    @Nonnull
    public static String substr_afl(@NotNull final String s, @NotNull final String s2) {
        return StringUtils.substringAfterLast(s, s2);
    }

    @MiCallable
    @Nonnull
    public static Boolean ends_with(@NotNull final String s, @NotNull final String s2) {
        return s.endsWith(s2);
    }

    @MiCallable
    @Nonnull
    public static Boolean starts_with(@NotNull final String s, @NotNull final String s2) {
        return s.startsWith(s2);
    }

    @MiCallable
    @Nonnull
    public static Boolean contains(@NotNull final String s, @NotNull final String s2) {
        return s.contains(s2);
    }

    @MiCallable
    @Nonnull
    public static String repeat(@NotNull final String s, @NotNull final Integer i) {
        try {
            return String.join("", Collections.nCopies(i, s));
        } catch (final Exception e) {
            return "";
        }
    }

}
