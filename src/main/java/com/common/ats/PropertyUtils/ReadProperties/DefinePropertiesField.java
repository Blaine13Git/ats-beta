package com.common.ats.PropertyUtils.ReadProperties;

import com.common.ats.LoggerUtils.TcRunLog;
import java.lang.reflect.Field;

public class DefinePropertiesField {
    private String[] TD_FilesPath;
    private String[] TD_SQL;
    private String[] TD_TableName;

    public String[] getTD_SQL() {
        return this.TD_SQL;
    }

    public void setTD_SQL(String[] tD_SQL) {
        this.TD_SQL = tD_SQL;
    }

    public String[] getTD_TableName() {
        return this.TD_TableName;
    }

    public void setTD_TableName(String[] tD_TableName) {
        this.TD_TableName = tD_TableName;
    }

    public String[] getTD_FilesPath() {
        return this.TD_FilesPath;
    }

    public void setTD_FilesPath(String[] tD_FilesPath) {
        this.TD_FilesPath = tD_FilesPath;
    }

    public static String[] getFiledName() {
        Field[] fields = DefinePropertiesField.class.getDeclaredFields();
        String[] fieldNames = new String[(fields.length - 1)];
        for (int i = 0; i < fieldNames.length; i++) {
            fieldNames[i] = fields[i + 1].getName();
        }
        return fieldNames;
    }

    public Object getFieldValueByName(String fieldName) {
        try {
            return getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            TcRunLog.error(e.getMessage());
            return null;
        }
    }
}
