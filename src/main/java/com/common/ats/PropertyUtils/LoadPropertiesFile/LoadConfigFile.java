package com.common.ats.PropertyUtils.LoadPropertiesFile;

import com.common.ats.ConfigParaUtil.Configuration;
import com.common.ats.ConfigParaUtil.ConfigurationImpl;
import org.apache.commons.lang3.StringUtils;

public class LoadConfigFile {
    private static final String Ats_config_properties_name = "ats_config.properties";
    private static final String Ats_config_url = "ATS_Config/";
    private static final String Env_Key = "Env_Key/";
    private static final String Ext_Config = "Ext_Config/";
    public static Configuration oConfiguration;

    public static Configuration oReadConfigFile(String filePath) {
        if (oConfiguration == null) {
            oConfiguration = new ConfigurationImpl();
            String ext_env = filePath;
            String filePath2 = String.valueOf(filePath) + Ats_config_url + Ats_config_properties_name;
            LoadPropertiesFile.oLoadPropertiesFile(filePath2, 0);
            String extFileContent = oConfiguration.getPropertyValue(Ext_Config);
            String envContent = oConfiguration.getPropertyValue(Env_Key);
            if (StringUtils.isNotBlank(extFileContent)) {
                String[] oList = extFileContent.split(",");
                int length = oList.length;
                for (int i = 0; i < length; i++) {
                    LoadPropertiesFile.oLoadPropertiesFile(String.valueOf(ext_env) + Ext_Config + oList[i], 0);
                }
            }
            if (StringUtils.isNotBlank(envContent)) {
                LoadPropertiesFile.oLoadPropertiesFile(String.valueOf(filePath2) + Env_Key + envContent, 0);
            }
        }
        return oConfiguration;
    }
}
