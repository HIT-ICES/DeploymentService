package com.hitices.deployment.service;


import com.hitices.deployment.bean.SchemeAddBean;
import com.hitices.deployment.bean.SchemeInfoBean;

import java.util.List;

public interface SchemeService {
    public List<SchemeInfoBean> getScheme(String namespace, String name);

    public String addScheme(SchemeAddBean schemeAddBean);
}
