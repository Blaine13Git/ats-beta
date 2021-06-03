package com.common.ats.ConfigParaUtil;

import java.util.Map;

public interface Configuration {
    Map<String, String> getConfig();

    String getPropertyValue(String str);

    String getPropertyValue(String str, String str2);

    void setPropertyValue(String str, String str2);
}
