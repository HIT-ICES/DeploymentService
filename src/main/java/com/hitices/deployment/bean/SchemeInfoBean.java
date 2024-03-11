package com.hitices.deployment.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 19:03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchemeInfoBean {
    private String name;
    private String namespace;
    private String status;
    private Date time;
}
