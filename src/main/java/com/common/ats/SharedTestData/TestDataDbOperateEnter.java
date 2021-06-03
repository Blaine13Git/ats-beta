package com.common.ats.SharedTestData;

import com.common.ats.Assert.AtsAssertUtils;
import com.common.ats.PropertyUtils.ReadProperties.DefinePropertiesField;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class TestDataDbOperateEnter {
    static String[] files = null;
    private static TestDataInitHander oTestDataInitHanderImpl = new TestDataInitHanderImpl();
    static String[] sqls = null;
    static String[] tableNames = null;

    public static void dbInsert(String filePath) {
        judgeParaNameSame(filePath, 0);
        oTestDataInitHanderImpl.dbInsert(files, tableNames);
    }

    public static int[] dbUpdate(String filePath) {
        judgeParaNameSame(filePath, 1);
        return oTestDataInitHanderImpl.dbUpdate(sqls, tableNames);
    }

    public static List<List<Map<String, String>>> dbSelect(String filePath) {
        judgeParaNameSame(filePath, 1);
        return oTestDataInitHanderImpl.dbSelect(sqls, tableNames);
    }

    public static List<ResultSet> dbSelectResultSet(String filePath) {
        judgeParaNameSame(filePath, 1);
        return oTestDataInitHanderImpl.dbSelectResultSet(sqls, tableNames);
    }

    public static Boolean dbCheck(String filePath) {
        judgeParaNameSame(filePath, 3);
        oTestDataInitHanderImpl.dbCheck(sqls, tableNames, files);
        return false;
    }

    private static void judgeParaNameSame(String filePath, int num) {
        if (!StringUtils.isBlank(filePath)) {
            DefinePropertiesField oDefinePropertiesField = SharedTestDataConfig.initDefinePropertiesField(filePath);
            sqls = oDefinePropertiesField.getTD_SQL();
            files = oDefinePropertiesField.getTD_FilesPath();
            tableNames = oDefinePropertiesField.getTD_TableName();
            if (num == 1) {
                AtsAssertUtils.assertEquals("属性文件配置错误,TD_SQL与TD_TableName配置的参数个数不一致，请检查文件数据.", (Object) Integer.valueOf(sqls.length), (Object) Integer.valueOf(tableNames.length));
            } else if (num == 0) {
                AtsAssertUtils.assertEquals("属性文件配置错误,TD_FilesPath与TD_TableName配置的参数个数不一致，请检查文件数据.", (Object) Integer.valueOf(files.length), (Object) Integer.valueOf(tableNames.length));
            } else if (num == 3) {
                AtsAssertUtils.assertEquals("属性文件配置错误,TD_FilesPath与TD_TableName配置的参数个数不一致，请检查文件数据.", (Object) Integer.valueOf(files.length), (Object) Integer.valueOf(tableNames.length));
                AtsAssertUtils.assertEquals("属性文件配置错误,TD_SQL与TD_TableName配置的参数个数不一致，请检查文件数据.", (Object) Integer.valueOf(sqls.length), (Object) Integer.valueOf(tableNames.length));
            } else {
                AtsAssertUtils.assertEquals("输入参数错误，只能输入0或者1: 0表示是插入判断，1是更新判断", (Object) 1, (Object) 2);
            }
        }
    }
}
