package com.hitices.deployment.client;

import com.hitices.deployment.bean.InstanceDeleteBean;
import com.hitices.deployment.bean.InstanceDeployBean;
import com.hitices.deployment.bean.SchemeInstanceBean;
import com.hitices.deployment.common.MResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@FeignClient(name = "InstanceService", url = "http://instance-service:8080")
public interface InstanceServiceClient {
    @RequestMapping(value = "/instance/scheme/deploy", method = RequestMethod.POST)
    MResponse deployInstanceScheme(@RequestBody SchemeInstanceBean schemeInstanceBean);
}
