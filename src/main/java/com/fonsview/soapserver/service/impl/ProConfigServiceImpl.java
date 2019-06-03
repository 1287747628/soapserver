package com.fonsview.soapserver.service.impl;

import com.custom.mutil.service.BasePropertiesService;
import com.fonsview.soapserver.service.ProConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ProConfigServiceImpl extends BasePropertiesService implements InitializingBean, ProConfigService {

    @Override
    public void afterPropertiesSet() throws Exception {
        addDirectory("/opt/fonsview/NE/soapserver");
    }

    @Override
    public String getString(String key, String defaultValue) {
        return super.getString(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return super.getBoolean(key, defaultValue);
    }

    @Override
    public Properties getProperties(String fileName) {
        return super.getProperties(fileName);
    }

}
