package com.common.ats.DBUtils.DBconfigFileUtil;

import com.common.ats.Common.StringUtils;
import com.common.ats.ConfigParaUtil.Configuration;
import com.common.ats.DBUtils.DBConnUtil.OracleDriverConfig;
import com.common.ats.LoggerUtils.TcRunLog;
import com.common.ats.PropertyUtils.InitPropertyConfig.PropertyConfig;
import com.common.ats.PropertyUtils.LoadPropertiesFile.LoadPropertiesFile;

public class GetDbConfigInfo {
    public static final String DB_File_Key = "DB_File_Key";
    public static final String DB_config_DIR = "DB_Conf/";
    public static int dbConfNumber = -1;
    private static Configuration testConfigs;

    public static void readDBConfigInfo(Configuration testConfigs2) {
        String oEnv_Path;
        String oDB_File = testConfigs2.getPropertyValue(DB_File_Key);
        if (oDB_File == null) {
            TcRunLog.error("配置文件ats_config.properties中未发现配置项字段：【DB_File_Key】,请配置该字段信息");
        } else if (StringUtils.EMPTY_STRING != oDB_File) {
            String oDB_File2 = oDB_File.trim().toLowerCase();
            String oEnv_Path2 = testConfigs2.getPropertyValue("Env_Path");
            int flag = 0;
            if (org.apache.commons.lang3.StringUtils.isBlank(oEnv_Path2) || oEnv_Path2 == null || StringUtils.EMPTY_STRING == oEnv_Path2) {
                oEnv_Path = DB_config_DIR + oDB_File2;
            } else {
                oEnv_Path = oEnv_Path2 + DB_config_DIR + oDB_File2;
                flag = 1;
            }
            LoadPropertiesFile.oLoadPropertiesFile(oEnv_Path, flag);
            dbConfNumber = 1;
            while (testConfigs2.getPropertyValue("ext" + dbConfNumber + "_db_name") != null) {
                dbConfNumber++;
            }
        } else {
            TcRunLog.error("数据库配置文件未找到,请确认是否已经配置了对应的DB_File_Key！");
        }
    }

    public static OracleDriverConfig oGetDbConfigInfo(String tableName, String dbConfigKey) {
        OracleDriverConfig oracleDriverConfig = new OracleDriverConfig();
        TcRunLog.debug("当前连接的数据库配置信息是: " + tableName + "----" + dbConfigKey);
        if (org.apache.commons.lang3.StringUtils.isEmpty(tableName) && org.apache.commons.lang3.StringUtils.isEmpty(dbConfigKey)) {
            return oracleDriverConfig;
        }
        testConfigs = PropertyConfig.testConfigs;
        if (testConfigs == null) {
            PropertyConfig.initConfigs(null);
            testConfigs = PropertyConfig.testConfigs;
        }
        String dbUrl = StringUtils.EMPTY_STRING;
        String dbName = StringUtils.EMPTY_STRING;
        String dbPassword = StringUtils.EMPTY_STRING;
        String dbSchema = StringUtils.EMPTY_STRING;
        if (!tableName.isEmpty()) {
            tableName = tableName.toUpperCase();
        }
        if (Boolean.valueOf((dbConfigKey == null || dbConfigKey.isEmpty() || StringUtils.EMPTY_STRING == dbConfigKey) ? false : true).booleanValue()) {
            String dbConfigKey2 = dbConfigKey.trim().toLowerCase();
            if (dbConfigKey2.startsWith("ext")) {
                dbUrl = testConfigs.getPropertyValue(dbConfigKey2 + "_db_url");
                dbName = testConfigs.getPropertyValue(dbConfigKey2 + "_db_name");
                dbPassword = testConfigs.getPropertyValue(dbConfigKey2 + "_db_password");
                dbSchema = testConfigs.getPropertyValue(dbConfigKey2 + "_db_schema");
            }
        } else if (judgeByTableNameIsExist(tableName, testConfigs.getPropertyValue("db_tablename"))) {
            dbUrl = testConfigs.getPropertyValue("db_url");
            dbName = testConfigs.getPropertyValue("db_name");
            dbPassword = testConfigs.getPropertyValue("db_password");
            dbSchema = testConfigs.getPropertyValue("db_schema");
        } else {
            for (int i = 0; i < dbConfNumber; i++) {
                if (judgeByTableNameIsExist(tableName, testConfigs.getPropertyValue("ext" + i + "_db_tablename"))) {
                    dbUrl = testConfigs.getPropertyValue("ext" + i + "_db_url");
                    dbName = testConfigs.getPropertyValue("ext" + i + "_db_name");
                    dbPassword = testConfigs.getPropertyValue("ext" + i + "_db_password");
                    dbSchema = testConfigs.getPropertyValue("ext" + i + "_db_schema");
                }
            }
        }
        if (dbName == StringUtils.EMPTY_STRING || dbName == null) {
            TcRunLog.info("配置的数据表没有对应的配置信息，请检查表是否存在对应的配置内容");
            return null;
        }
        oracleDriverConfig.setDbUrl(dbUrl);
        oracleDriverConfig.setName(dbName);
        oracleDriverConfig.setPassword(dbPassword);
        oracleDriverConfig.setSchema(dbSchema);
        return oracleDriverConfig;
    }

    public static boolean judgeByTableNameIsExist(String tableName, String db_name) {
        if (org.apache.commons.lang3.StringUtils.isBlank(db_name) || org.apache.commons.lang3.StringUtils.isBlank(tableName)) {
            return false;
        }
        for (String string : db_name.toUpperCase().split(",")) {
            if (string.equals(tableName)) {
                return true;
            }
        }
        return false;
    }
}
