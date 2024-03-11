package com.hitices.deployment.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 19:19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchemeAddBean {
    private String name;
    private String namespace;
    private List<InstanceDeployBean> scheme;
}
