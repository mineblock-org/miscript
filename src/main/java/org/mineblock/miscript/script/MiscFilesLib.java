package org.mineblock.miscript.script;

import org.crayne.mi.lang.MiCallable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MiscFilesLib {

    private static File fileOf(@NotNull final String path) {
        if (path.isEmpty()) return new File(System.getProperty("user.dir"));
        return new File(path);
    }

    @MiCallable
    @Nonnull
    public static Boolean delete(@NotNull final String path) {
        try {
            return fileOf(path).delete();
        } catch (final Exception e) {
            return false;
        }
    }

    private static final Map<String, List<String>> filelistLocks = new ConcurrentHashMap<>();

    private static List<String> folderContents(@NotNull final String path) {
        return is_locked_filelist(path) ?  filelistLocks.get(path) : actualFolderContents(path);
    }

    private static List<String> actualFolderContents(@NotNull final String path) {
        return Arrays.stream(Optional.ofNullable(fileOf(path).listFiles()).orElse(new File[0])).map(File::getAbsolutePath).collect(Collectors.toList());
    }

    @MiCallable
    public static void lock_filelist(@NotNull final String path) {
        filelistLocks.put(path, actualFolderContents(path));
    }


    @MiCallable
    public static void unlock_filelist(@NotNull final String path) {
        filelistLocks.remove(path);
    }


    @MiCallable
    @Nonnull
    public static Boolean is_locked_filelist(@NotNull final String path) {
        return filelistLocks.containsKey(path);
    }

    @MiCallable
    @Nonnull
    public static Integer list_amt(@NotNull final String path) {
        try {
            return folderContents(path).size();
        } catch (final Exception e) {
            return -1;
        }
    }

    @MiCallable
    public static String list_get(@NotNull final String path, @NotNull final Integer index) {
        try {
            return folderContents(path).get(index);
        } catch (final Exception e) {
            return null;
        }
    }

    @MiCallable
    @Nonnull
    public static Boolean is_file(@NotNull final String path) {
        try {
            return fileOf(path).isFile();
        } catch (final Exception e) {
            return false;
        }
    }

    @MiCallable
    @Nonnull
    public static Boolean is_directory(@NotNull final String path) {
        try {
            return fileOf(path).isDirectory();
        } catch (final Exception e) {
            return false;
        }
    }

    @MiCallable
    @Nonnull
    public static Boolean exists(@NotNull final String path) {
        try {
            return new File(path).exists();
        } catch (final Exception e) {
            return false;
        }
    }

}
