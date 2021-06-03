package com.common.ats.CsvProviderUtils.CsvDataProviderFilePathConfig;

import com.common.ats.CsvProviderUtils.CsvDataProvideBase.CsvDataProviderConfig;
import com.common.ats.PropertyUtils.InitPropertyConfig.PropertyConfig;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;

public class CsvDataProviderFilePathConfigClass {
    public static Iterator<Object[]> oCsvDataProviderFilePathConfigClass(Class<?> oClass, Method oMethod, String filePath) {
        String className;
        String tcPackage;
        String className2 = oClass.getSimpleName();
        if (className2.endsWith("_NormalTest")) {
            className = className2.split("_NormalTest")[0];
            tcPackage = "NormalTest";
        } else if (!className2.endsWith("_AbnormalTest")) {
            return null;
        } else {
            className = className2.split("_AbnormalTest")[0];
            tcPackage = "AbnormalTest";
        }
        String csvFilePath = filePath + ("TestCase-Data/" + className + "/" + tcPackage + "/" + oMethod.getName() + ".csv");
        if (!new File(csvFilePath).exists()) {
            CsvFileOperationUtils.createCsvFile(csvFilePath);
        }
        PropertyConfig.testConfigs.setPropertyValue("CsvFilePath", csvFilePath);
        return new CsvDataProviderConfig(oClass, oMethod, csvFilePath, filePath);
    }

    public static String[] getCsvFilePara(Class<?> oClass, Method oMethod, String tcDirCsv) {
        new CsvDataProviderConfig(oClass, oMethod, tcDirCsv, (String) null).getReader();
        return null;
    }
}
