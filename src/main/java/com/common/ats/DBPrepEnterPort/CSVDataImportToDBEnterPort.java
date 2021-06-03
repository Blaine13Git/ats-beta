package com.common.ats.DBPrepEnterPort;

import com.common.ats.Common.DateUtil;
import com.common.ats.DBUtils.DbOperationUtils.DbSqlCommand;

public class CSVDataImportToDBEnterPort {
    public void csvToOracle(java.lang.String r16, java.lang.String r17, java.lang.String... r18) {
        throw new UnsupportedOperationException("Method not decompiled: com.common.ats.DBPrepEnterPort.CSVDataImportToDBEnterPort.csvToOracle(java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    public void csvToOracle(String[] csvFile, String[] tableName) {
        for (int number = 0; number < csvFile.length; number++) {
            csvToOracle(csvFile[number], tableName[number], new String[0]);
        }
    }

    public void cleanDataAfterTomorrow(String tableName, String condition) {
        DbSqlCommand.dbDelete("DELETE FROM " + tableName + " WHERE " + condition + ">TO_DATE('" + DateUtil.getBeforeDayString(-1) + "','yyyymmdd')", tableName);
    }

    public void cleanDataByFormatDate(String tableName, String condition) {
        DbSqlCommand.dbDelete("DELETE FROM " + tableName + " WHERE " + condition + ">'" + DateUtil.getBeforeDayString(0) + "'", tableName);
    }

    public void cleanDataBySysDate(String tableName, String condition) {
        DbSqlCommand.dbDelete("DELETE FROM " + tableName + " WHERE " + condition + ">sysdate", tableName);
    }

    public void cleanDataBySysDate(String[] tableName, String[] condition) {
        for (int number = 0; number < tableName.length; number++) {
            cleanDataBySysDate(tableName[number], condition[number]);
        }
    }
}
