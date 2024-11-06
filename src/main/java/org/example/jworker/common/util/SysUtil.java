package org.example.jworker.common.util;

import cn.hutool.core.util.IdUtil;

import java.util.UUID;

public class SysUtil {

    public static Integer getId(){
        String uuid = UUID.randomUUID().toString();
        String numericUUID = uuid.replaceAll("[^0-9]", "");
        return Integer.parseInt(numericUUID);
    }
}
