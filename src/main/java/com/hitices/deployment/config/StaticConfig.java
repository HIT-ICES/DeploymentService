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
    public static Integer Normal = 0;
    public static Integer Hold = 1;
    public static Integer Running = 2;
    public static Integer Fail = 3;
    public static Integer Error = 4;
    public static List<String> Status = new ArrayList<>(Arrays.asList(
            "已执行",
            "未执行",
            "执行中",
            "执行失败",
            "运行异常"));
}
