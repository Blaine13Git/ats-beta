package com.common.ats.CsvProviderUtils.CsvDataProviderFilePathConfig;

import com.common.ats.LoggerUtils.TcRunLog;
import java.io.File;
import java.io.IOException;

public class CsvFileOperationUtils {
    public static boolean createCsvFile(String csvFilePath) {
        File oFile = new File(csvFilePath);
        try {
            if (!oFile.getParentFile().exists()) {
                oFile.getParentFile().mkdirs();
            }
            oFile.createNewFile();
            return true;
        } catch (IOException e) {
            TcRunLog.error("创建测试用例对应的测试文件失败,文件路径名称如下: \r\n                 " + csvFilePath + "\r\n错误信息如下: " + e);
            return false;
        }
    }
}
