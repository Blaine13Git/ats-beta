package com.common.ats.PropertyUtils.LoadPropertiesFile;

import com.common.ats.ConfigParaUtil.Configuration;
import com.common.ats.LoggerUtils.TcRunLog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class LoadPropertiesFile {
    private static Configuration oConfiguration = LoadConfigFile.oConfiguration;

    public static void oLoadPropertiesFile(String fileName, int flag) {
        TcRunLog.info("加载配置文件 ：【 " + fileName + " 】");
        InputStream oInputStream = null;
        Properties oProperties = new Properties();
        try {
            oInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            oProperties.load(oInputStream);
            for (Map.Entry<Object, Object> entry : oProperties.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                TcRunLog.debug(key + "------------key:value--------" + value);
                oConfiguration.setPropertyValue(key.toString(), value.toString());
            }
        } catch (Exception e2) {
            TcRunLog.error("指定文件不存在！ [" + fileName + "] ,错误详情：  [" + e2.getMessage() + "]");
        }
    }
}
