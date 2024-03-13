package com.hitices.deployment.controller;

import com.hitices.deployment.bean.SchemeAddBean;
import com.hitices.deployment.bean.SchemeDeployBean;
import com.hitices.deployment.common.MResponse;
import com.hitices.deployment.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 17:12
 */
@CrossOrigin
@RestController
@RequestMapping("/deployment")
public class MController {

    @Autowired
    public SchemeService schemeService;

    @GetMapping("/scheme")
    public MResponse getScheme(@RequestParam("cluster") String cluster, @RequestParam("namespace") String namespace, @RequestParam("name") String name) {
        return MResponse.successMResponse().data(schemeService.getScheme(namespace, name));
    }

    @PostMapping("/scheme/add")
    public MResponse addScheme(@RequestBody SchemeAddBean schemeAddBean) {
        return MResponse.successMResponse().data(schemeService.addScheme(schemeAddBean));
    }

    @PostMapping("/scheme/deploy")
    public MResponse deployScheme(@RequestBody SchemeDeployBean schemeDeployBean) {
        return MResponse.successMResponse().data(schemeService.deployScheme(schemeDeployBean));
    }

    @PostMapping("/scheme/deploy/callback")
    public void deploySchemeCallback(@RequestBody MResponse response) {
        schemeService.deploySchemeCallback(5L);
    }
}
