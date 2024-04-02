package com.hitices.deployment.service;

import com.google.gson.Gson;
import com.hitices.deployment.bean.InstanceDeployBean;
import com.hitices.deployment.client.InstanceServiceClient;
import com.hitices.deployment.config.StaticConfig;
import com.hitices.deployment.entity.SchemeEntity;
import com.hitices.deployment.json.PodItem;
import com.hitices.deployment.json.PodList;
import com.hitices.deployment.repository.SchemeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/4/2 14:52
 */
@Slf4j
@Component
@Transactional
public class InstanceMointor {
    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private InstanceServiceClient instanceServiceClient;

    @Autowired
    private EntityManager entityManager;

    /**
     *  每5分钟检查一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void mointor(){
        // Normal
        List<SchemeEntity> schemeEntities =  schemeRepository.findAllByStatus(StaticConfig.Normal);
        for (SchemeEntity scheme : schemeEntities){
            Boolean Flag = check(scheme);
            if (!Flag){
                updateSchemeStatus(StaticConfig.Error, scheme.getId());
            }
        }
        // Running
        schemeEntities =  schemeRepository.findAllByStatus(StaticConfig.Running);
        for (SchemeEntity scheme : schemeEntities){
            Boolean Flag = check(scheme);
            if (!Flag){
                updateSchemeStatus(StaticConfig.Fail, scheme.getId());
            }else {
                updateSchemeStatus(StaticConfig.Normal, scheme.getId());
            }
        }
        // Error
        schemeEntities =  schemeRepository.findAllByStatus(StaticConfig.Error);
        for (SchemeEntity scheme : schemeEntities){
            Boolean Flag = check(scheme);
            if (Flag){
                updateSchemeStatus(StaticConfig.Normal, scheme.getId());
            }
        }
    }


    public Boolean check(SchemeEntity scheme){
        Gson gson = new Gson();
        PodList pods = gson.fromJson(gson.toJson(instanceServiceClient.status("ices104", scheme.getNamespace()).getData()), PodList.class);
        List<InstanceDeployBean> instances = Arrays.asList(gson.fromJson(scheme.getData(), InstanceDeployBean[].class));
        Boolean Flag = false;
        for (InstanceDeployBean instace : instances){
            Flag = false;
            for (PodItem pod : pods.getItems()){
                if (pod.getMetadata().getLabels().get("app").equals(instace.getServiceName())){
                    Flag = pod.getStatus().getPhase().equals("Running");
                    if (!StringUtils.isEmpty(instace.getNodeName())){
                        Flag = pod.getStatus().getHostIP().equals(instace.getNodeIp());
                    }
                    break;
                }
            }
            if (!Flag)
                break;
        }
        return Flag;
    }
    public int updateSchemeStatus(Integer status, Long id){
        Query query = entityManager.createQuery("update SchemeEntity s set s.status = :status where s.id = :id");
        query.setParameter("status", status);
        query.setParameter("id", id);
        // 执行更新操作
        return query.executeUpdate();
    }
}
