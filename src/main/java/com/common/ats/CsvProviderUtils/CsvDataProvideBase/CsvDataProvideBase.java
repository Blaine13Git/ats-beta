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
            TcRunLog.error("\r\n             ?????????????????????" + oClass.getName() + "???" + "\r\n             ???  ??? ??? ???: ???" + oMethod.getName() + "???" + "\r\n             ???  ??? ??? ???: ???????????????????????????????????????????????????????????????????????????Packge?????????????????????????????????," + "\r\n             ????????????:\r\n                   ????????????---????????????+NormalTest. " + "\r\n                   ????????????---????????????+AbormalTest.");
        }
        return rs;
    }
}
