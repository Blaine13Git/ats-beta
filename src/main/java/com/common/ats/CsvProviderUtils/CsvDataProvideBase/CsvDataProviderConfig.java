package com.common.ats.CsvProviderUtils.CsvDataProvideBase;

import au.com.bytecode.opencsv.CSVReader;
import com.common.ats.LoggerUtils.TcRunLog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

public class CsvDataProviderConfig implements Iterator<Object[]> {
    Converter[] converters;
    Method errMethodPara;
    Class<?>[] methodParaType;
    CSVReader oCSVReader;
    String oCsvFilePath;
    String[] rowData;

    public CsvDataProviderConfig(Class<?> cls, Method method, String csvFilePath, String filePath) {
        this.errMethodPara = method;
        InputStream oInputStream = null;
        csvFilePath = (StringUtils.isBlank(filePath) || filePath == null) ? String.valueOf(System.getProperty("user.dir")) + "/" + csvFilePath : csvFilePath;
        TcRunLog.debug("当前的CSV文件地址是: " + csvFilePath + "-------------");
        try {
            oInputStream = new FileInputStream(csvFilePath);
        } catch (FileNotFoundException e) {
            TcRunLog.error(e.getStackTrace().toString());
        }
        this.oCSVReader = new CSVReader(new InputStreamReader(oInputStream), ',', '\"', 1);
        this.methodParaType = method.getParameterTypes();
        this.converters = new Converter[this.methodParaType.length];
        for (int i = 0; i < this.converters.length; i++) {
            this.converters[i] = ConvertUtils.lookup(this.methodParaType[i]);
        }
    }

    public boolean hasNext() {
        if (this.oCSVReader == null) {
            return false;
        }
        try {
            this.rowData = this.oCSVReader.readNext();
            if (this.rowData != null) {
                String[] strArr = this.rowData;
                int length = strArr.length;
                for (int i = 0; i < length; i++) {
                    TcRunLog.debug("当前参数值是: " + strArr[i] + "--------------");
                }
            }
        } catch (IOException e) {
            TcRunLog.error("读取测试用例对应的测试文件数据出错，请检查文件.");
        }
        if (this.rowData != null) {
            return true;
        }
        return false;
    }

    private String[] reReadCsvData() {
        if (this.rowData == null) {
            try {
                this.rowData = this.oCSVReader.readNext();
            } catch (IOException ioe) {
                TcRunLog.error("get next line error!");
                throw new RuntimeException(ioe);
            }
        }
        return this.rowData;
    }

    public void remove() {
    }

    public CSVReader getReader() {
        return this.oCSVReader;
    }

    public Object[] next() {
        String[] paraValue;
        if (this.rowData != null) {
            paraValue = this.rowData;
        } else {
            paraValue = reReadCsvData();
        }
        this.rowData = null;
        return judgeParaObjects(paraValue);
    }

    private Object[] judgeParaObjects(String[] paraValue) {
        int paraNumber = paraValue.length;
        int methodParaNumber = this.methodParaType.length;
        if (paraNumber != methodParaNumber) {
            TcRunLog.error("CSV文件【" + this.oCsvFilePath + "】的参数个数 [" + paraNumber + "] \r\n与方法【" + this.errMethodPara + "】的参数个数 [" + methodParaNumber + "] 不相等.请检查对应文件");
            return null;
        }
        Object[] result = new Object[paraNumber];
        for (int i = 0; i < paraNumber; i++) {
            result[i] = this.converters[i].convert(this.methodParaType[i], paraValue[i]);
        }
        return result;
    }
}
