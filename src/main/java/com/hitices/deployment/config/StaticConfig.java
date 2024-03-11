package com.hitices.deployment.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 17:20
 */
public class StaticConfig {
    public static List<String> Status = new ArrayList<>(Arrays.asList(
            "已执行",
            "未执行",
            "执行中",
            "执行失败"));
}
