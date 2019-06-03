package com.fonsview.soapserver.service;

import java.util.Properties;

public interface ProConfigService {

    String getString(String key, String defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    Properties getProperties(String fileName);
}
