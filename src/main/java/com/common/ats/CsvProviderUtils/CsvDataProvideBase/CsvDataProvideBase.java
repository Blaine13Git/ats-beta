package com.common.ats.CsvProviderUtils.CsvDataProvideBase;

import com.common.ats.Assert.AtsAssertUtils;
import com.common.ats.CsvProviderUtils.CsvDataProviderFilePathConfig.CsvDataProviderFilePathConfigClass;
import com.common.ats.LoggerUtils.TcRunLog;
import com.common.ats.PropertyUtils.InitPropertyConfig.PropertyConfig;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;

public class CsvDataProvideBase extends AtsAssertUtils {
    public static String filePathUrl;

    public static String getFilePathUrl() {
        return filePathUrl;
    }

    public static void setFilePathUrl(String filePathUrl2) {
        filePathUrl = filePathUrl2;
    }

    public CsvDataProvideBase() {
        if (StringUtils.isBlank(filePathUrl)) {
            filePathUrl = String.valueOf(System.getProperty("user.dir")) + "/src/test/resources/";
        }
        setFilePathUrl(filePathUrl);
        PropertyConfig.initConfigs(String.valueOf(filePathUrl) + "/");
    }

    public CsvDataProvideBase(String filePathUrl2) {
        setFilePathUrl(filePathUrl2);
        PropertyConfig.initConfigs(String.valueOf(filePathUrl2) + "/");
    }

    @DataProvider(name = "oCsvDataProviderConfig")
    public static Iterator<Object[]> getCsvTestDataProvider(Method oMethod) {
        return getCsvTestDataProvider(oMethod.getDeclaringClass(), oMethod, getFilePathUrl());
    }

    public Iterator<Object[]> getCsvTestData(Method oMethod) {
        Class<?> oClass = oMethod.getDeclaringClass();
        TcRunLog.info(" <----------->" + filePathUrl + "<---------------->");
        return getCsvTestDataProvider(oClass, oMethod, filePathUrl);
    }

    protected static Iterator<Object[]> getCsvTestDataProvider(Class<?> oClass, Method oMethod, String filePath) {
        Iterator<Object[]> rs = CsvDataProviderFilePathConfigClass.oCsvDataProviderFilePathConfigClass(oClass, oMethod, filePath);
        if (rs == null) {
            TcRunLog.error("\r\n             当前测试类：【" + oClass.getName() + "】" + "\r\n             测  试 方 法: 【" + oMethod.getName() + "】" + "\r\n             错  误 提 示: 请根据规范要求设置用户名以及目录结构：接口或用例为Packge，测试用例分为正常用例," + "\r\n             名称格式:\r\n                   正常用例---接口名称+NormalTest. " + "\r\n                   异常用例---接口名称+AbormalTest.");
        }
        return rs;
    }
}
