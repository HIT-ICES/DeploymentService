package com.hitices.deployment.service;


import com.hitices.deployment.bean.*;
import com.hitices.deployment.entity.SchemeEntity;

import java.util.List;

public interface SchemeService {
    public List<SchemeInfoBean> getScheme(String namespace, String name);

    public String addScheme(SchemeAddBean schemeAddBean);

    public String deployScheme(SchemeDeployBean schemeDeployBean);

    public void deploySchemeCallback(SchemeDeployCallbackBean schemeDeployCallbackBean);

    public SchemeDetailBean getSchemeDetail(Long id);
}
