package com.common.ats.PropertyUtils.ReadProperties;

import com.common.ats.Common.StringUtils;
import com.common.ats.LoggerUtils.TcRunLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class ReadPropertiesContents {
    public static HashMap<String, String> readPropertiesContents(Class<?> cls, String propertiesFilePath) {
        HashMap<String, String> oPropertiesContents = new HashMap<>();
        Properties oProperties = new Properties();
        InputStream oInputStream = null;
        try {
            oInputStream = cls.getClassLoader().getResourceAsStream(propertiesFilePath);
            oProperties.load(oInputStream);
            Enumeration<?> en = oProperties.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = upEncode(oProperties.getProperty(key), "utf-8");
                TcRunLog.debug("当前配置文件<" + propertiesFilePath + ">的内容是：【" + key + " : " + value + " 】");
                oPropertiesContents.put(key, value);
            }
            if (oInputStream != null) {
                try {
                    oInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    TcRunLog.info("关闭流失败");
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            TcRunLog.info("读取资源文件出错");
            if (oInputStream != null) {
                try {
                    oInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                    TcRunLog.info("关闭流失败");
                }
            }
        } catch (Throwable th) {
            if (oInputStream != null) {
                try {
                    oInputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                    TcRunLog.info("关闭流失败");
                }
            }
            throw th;
        }
        return oPropertiesContents;
    }

    private static String upEncode(String value, String encode) {
        if (value == null || value == StringUtils.EMPTY_STRING || org.apache.commons.lang3.StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return new String(value.getBytes("ISO-8859-1"), encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            TcRunLog.info("properties文件内容编码转换错误");
            return value;
        }
    }
}
