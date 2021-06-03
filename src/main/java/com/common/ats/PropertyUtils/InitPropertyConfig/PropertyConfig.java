package com.common.ats.PropertyUtils.InitPropertyConfig;

import com.common.ats.ConfigParaUtil.Configuration;
import com.common.ats.DBUtils.DBconfigFileUtil.GetDbConfigInfo;
import com.common.ats.PropertyUtils.LoadPropertiesFile.LoadConfigFile;

public class PropertyConfig {
    public static Configuration testConfigs = null;

    public static void initConfigs(String filePath) {
        if (testConfigs == null) {
            testConfigs = LoadConfigFile.oReadConfigFile(filePath);
        }
        if (testConfigs != null) {
            testConfigs.setPropertyValue("Env_Path", filePath);
            GetDbConfigInfo.readDBConfigInfo(testConfigs);
        }
    }
}
