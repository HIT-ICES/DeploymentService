package com.hitices.deployment.service.impl;

import com.google.gson.Gson;
import com.hitices.deployment.bean.SchemeAddBean;
import com.hitices.deployment.bean.SchemeInfoBean;
import com.hitices.deployment.config.StaticConfig;
import com.hitices.deployment.entity.SchemeEntity;
import com.hitices.deployment.repository.SchemeRepository;
import com.hitices.deployment.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 18:27
 */
@Service
@Component
public class SchemeServiceImpl implements SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;

    @Override
    public List<SchemeInfoBean> getScheme(String namespace, String name) {
        List<SchemeEntity> schemes = new ArrayList<>();
        if(namespace.isEmpty()){
            if(name.isEmpty()){
                schemes = schemeRepository.findAllByOrderByStatusAsc();
            }else {
                schemes = schemeRepository.findAllByNameContainingIgnoreCaseOrderByStatusAsc(name);
            }
        }else {
            if(name.isEmpty()){
                schemes = schemeRepository.findAllByNamespaceOrderByStatusAsc(namespace);
            }else {
                schemes = schemeRepository.findAllByNameContainingIgnoreCaseAndNamespaceOrderByStatusAsc(name, namespace);
            }
        }
        return getSchemeInfo(schemes);
    }

    @Override
    public String addScheme(SchemeAddBean schemeAddBean) {
        Gson gson = new Gson();
        schemeRepository.save(new SchemeEntity(schemeAddBean.getName(), schemeAddBean.getNamespace(), gson.toJson(schemeAddBean.getScheme())));
        return "Success";
    }

    private List<SchemeInfoBean> getSchemeInfo(List<SchemeEntity> schemes){
        List<SchemeInfoBean> results = new ArrayList<>();
        for (SchemeEntity scheme : schemes){
            results.add(new SchemeInfoBean(scheme.getName(), scheme.getNamespace(),
                    StaticConfig.Status.get(scheme.getStatus()), scheme.getTime()));
        }
        return results;
    }
}
