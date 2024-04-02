package com.hitices.deployment.service.impl;

import com.google.gson.Gson;
import com.hitices.deployment.bean.*;
import com.hitices.deployment.client.InstanceServiceClient;
import com.hitices.deployment.common.MResponse;
import com.hitices.deployment.config.StaticConfig;
import com.hitices.deployment.entity.SchemeEntity;
import com.hitices.deployment.repository.SchemeRepository;
import com.hitices.deployment.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 18:27
 */
@Service
@Component
@Transactional
public class SchemeServiceImpl implements SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private InstanceServiceClient instanceServiceClient;


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

    @Override
    public String deployScheme(SchemeDeployBean schemeDeployBean) {
        Gson gson = new Gson();
        Optional<SchemeEntity> schemeEntity = schemeRepository.findById(schemeDeployBean.getId());
        if (!schemeEntity.isPresent()){
            return "Fail";
        }
        SchemeEntity scheme = schemeEntity.get();
        if (schemeRepository.findAllByNamespaceAndStatus(schemeDeployBean.getNamespace(), 0).size() > 0 ||
                schemeRepository.findAllByNamespaceAndStatus(schemeDeployBean.getNamespace(), 2).size() > 0){
            return "Fail";
        }
        if (updateSchemeStatus(2, schemeDeployBean.getId()) == 0){
            return "Fail";
        }
        List<InstanceDeployBean> instanceDeployBeans = Arrays.asList(gson.fromJson(scheme.getData(), InstanceDeployBean[].class));
        instanceServiceClient.deployInstanceScheme(
                new SchemeInstanceBean("ices104", scheme.getId(), scheme.getName(), scheme.getNamespace(), instanceDeployBeans));
        return "Success";
    }

    @Override
    public void deploySchemeCallback(SchemeDeployCallbackBean schemeDeployCallbackBean) {
        if (schemeDeployCallbackBean.getStatus() == 0){
            // todo: 进一步对比实例状态是否正常
            updateSchemeStatus(0, schemeDeployCallbackBean.getId());
            System.out.println("get callback");
        }else {
            updateSchemeStatus(4, schemeDeployCallbackBean.getId());
        }
    }

    @Override
    public SchemeDetailBean getSchemeDetail(Long id) {
        Gson gson = new Gson();
        SchemeEntity scheme = schemeRepository.getById(id);
        SchemeDetailBean schemeInfoBean = new SchemeDetailBean(scheme.getId(), scheme.getName(), scheme.getNamespace(),
                StaticConfig.Status.get(scheme.getStatus()), scheme.getTime(),
                Arrays.asList(gson.fromJson(scheme.getData(), InstanceDeployBean[].class)));
        return schemeInfoBean;
    }

    public int updateSchemeStatus(Integer status, Long id){
        Query query = entityManager.createQuery("update SchemeEntity s set s.status = :status where s.id = :id");
        query.setParameter("status", status);
        query.setParameter("id", id);
        // 执行更新操作
        return query.executeUpdate();
    }


    private List<SchemeInfoBean> getSchemeInfo(List<SchemeEntity> schemes){
        List<SchemeInfoBean> results = new ArrayList<>();
        for (SchemeEntity scheme : schemes){
            results.add(new SchemeInfoBean(scheme.getId(), scheme.getName(), scheme.getNamespace(),
                    StaticConfig.Status.get(scheme.getStatus()), scheme.getTime()));
        }
        return results;
    }
}
