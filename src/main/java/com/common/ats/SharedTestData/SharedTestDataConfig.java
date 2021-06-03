package com.common.ats.SharedTestData;

import com.common.ats.Common.PrintXslFormat;
import com.common.ats.Common.StringUtils;
import com.common.ats.LoggerUtils.TcRunLog;
import com.common.ats.PropertyUtils.ReadProperties.DefinePropertiesField;
import com.common.ats.PropertyUtils.ReadProperties.PropertiesFieldEnum;
import com.common.ats.PropertyUtils.ReadProperties.ReadPropertiesContents;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SharedTestDataConfig {
    private static Map<String, HashMap<String, String>> oMemoryConfigCacheItem = new HashMap();

    public static void clear() {
        oMemoryConfigCacheItem.clear();
    }

    public static void initTestDataConfigs(String propertiesFilePath) {
        if (!oMemoryConfigCacheItem.containsKey(propertiesFilePath)) {
            new HashMap();
            oMemoryConfigCacheItem.put(propertiesFilePath, ReadPropertiesContents.readPropertiesContents(SharedTestDataConfig.class, propertiesFilePath));
        }
    }

    public static DefinePropertiesField initDefinePropertiesField(String propertiesFilePath) {
        DefinePropertiesField oDefinePropertiesField = new DefinePropertiesField();
        initTestDataConfigs(propertiesFilePath);
        HashMap<String, String> oPropertiesField = oMemoryConfigCacheItem.get(propertiesFilePath);
        String td_sql = oPropertiesField.get(PropertiesFieldEnum.TD_SQL.getValue());
        String td_tableName = oPropertiesField.get(PropertiesFieldEnum.TD_TableName.getValue());
        String td_files = oPropertiesField.get(PropertiesFieldEnum.TD_FilesPath.getValue());
        Iterator<Map.Entry<String, String>> it = oPropertiesField.entrySet().iterator();
        while (true) {
            if (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (matchCaseId("TD001", entry.getValue())) {
                    String str = getPrefix(entry.getKey());
                    td_tableName = joinString(td_tableName, oPropertiesField.get(str + "TableName"));
                    td_files = joinString(td_files, oPropertiesField.get(str + "FilesPath"));
                    td_sql = joinString(td_sql, oPropertiesField.get(str + "SQL"));
                    break;
                }
            } else {
                break;
            }
        }
        oDefinePropertiesField.setTD_FilesPath(getSplitString(td_files));
        oDefinePropertiesField.setTD_TableName(getSplitString(td_tableName));
        oDefinePropertiesField.setTD_SQL(getSplitString(td_sql));
        if (TcRunLog.isDebugEnabled()) {
            PrintXslFormat.oPrintPropertiesField(oDefinePropertiesField);
        }
        return oDefinePropertiesField;
    }

    private static boolean matchCaseId(String caseId, String caseIdConfig) {
        if (StringUtils.isBlank(caseIdConfig) || StringUtils.isBlank(caseId) || !caseId.equals(caseIdConfig.toUpperCase())) {
            return false;
        }
        return true;
    }

    private static String[] getSplitString(String s) {
        return StringUtils.isEmpty(s) ? new String[0] : s.split(";");
    }

    private static String getPrefix(String s) {
        return s.substring(0, s.indexOf("_") + 1);
    }

    private static String joinString(String str1, String str2) {
        if (StringUtils.isEmpty(str1)) {
            String str = str2;
        }
        if (StringUtils.isEmpty(str2)) {
            return str1;
        }
        return str1 + ";" + str2;
    }

    private static String getJoinedString(String s1, String s2) {
        if (StringUtils.isEmpty(s1)) {
            return s2;
        }
        if (StringUtils.isEmpty(s2)) {
            return s1;
        }
        return s1 + ";" + s2;
    }
}
