package com.hitices.deployment.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/4/2 10:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchemeDetailBean {
    private Long id;
    private String name;
    private String namespace;
    private String status;
    private Date time;
    private List<InstanceDeployBean> scheme;
}
