package org.mineblock.miscript.util;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class FileUtil {

    public static String noExtension(@NotNull final String fullFilepath) {
        return fullFilepath.contains(".") ? StringUtils.substringBeforeLast(fullFilepath, ".") : fullFilepath;
    }

}
