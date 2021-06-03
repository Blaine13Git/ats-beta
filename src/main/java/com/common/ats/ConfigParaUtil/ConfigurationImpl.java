package com.common.ats.ConfigParaUtil;

import com.common.ats.LoggerUtils.TcRunLog;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationImpl implements Configuration {
    private Map<String, String> atsConfigMap = new HashMap();

    public Map<String, String> getConfig() {
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("getConfig() - start");
        }
        Map<String, String> oMap = new HashMap<>();
        oMap.putAll(this.atsConfigMap);
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("getConfig() - end");
        }
        return oMap;
    }

    public void setConfig(Map<String, String> map) {
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("setConfig(Map<String,String>) - start");
        }
        this.atsConfigMap.putAll(map);
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("setConfig(Map<String,String>) - end");
        }
    }

    public String getPropertyValue(String key) {
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("getPropertyValue(String) - start");
        }
        String returnString = this.atsConfigMap.get(key);
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("getPropertyValue(String) - end");
        }
        return returnString;
    }

    public void setPropertyValue(String key, String value) {
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("setPropertyValue(String, String) - start");
        }
        this.atsConfigMap.put(key, value);
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("setPropertyValue(String, String) - end");
        }
    }

    public String getPropertyValue(String key, String defaultValue) {
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("getPropertyValue(String, String) - start");
        }
        String returnString = this.atsConfigMap.get(key) == null ? defaultValue : this.atsConfigMap.get(key);
        if (TcRunLog.isDebugEnabled()) {
            TcRunLog.debug("getPropertyValue(String, String) - end");
        }
        return returnString;
    }

    public static void iterateConfigration(Configuration conf) {
        for (Map.Entry<String, String> entry : conf.getConfig().entrySet()) {
            TcRunLog.info(String.valueOf(entry.getKey()) + " : " + entry.getValue());
        }
    }
}
