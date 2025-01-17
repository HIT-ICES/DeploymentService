package com.hitices.deployment.bean;

import com.hitices.deployment.json.Port;
import com.hitices.deployment.json.Resources;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/6
 */
@Getter
@Setter
@NoArgsConstructor
public class InstanceDeployBean {
    private String serviceId;
    private String serviceName;
    private String namespace;
    private String imageUrl;
    private int replicas;
    private List<Port> ports;
    private Resources resources;
    private String nodeName;
    private String nodeIp;
}
