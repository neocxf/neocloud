package top.neospot.cloud.common.util;

import java.util.UUID;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
public final class UUIDUtil {
    static UUID uuid = UUID.randomUUID();

    public static String messageId() {
        String str = UUID.randomUUID().toString();
        return str.replace("-", "");
    }

}
