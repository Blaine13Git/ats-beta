package com.common.ats.DBUtils.DbOperationUtils;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class DbSqlCommand {
    public static int dbUpdate(String dbName, String dbSql) {
        return Db_operation.dbUpdate(dbName, dbSql, (String) null);
    }

    public static int dbInsert(String dbName, String dbSql) {
        return Db_operation.dbUpdate(dbName, dbSql, (String) null);
    }

    public static int dbDelete(String dbName, String dbSql) {
        return Db_operation.dbUpdate(dbName, dbSql, (String) null);
    }

    public static List<Map<String, String>> dbSelect(String dbName, String dbSql) {
        return Db_operation.getResultMap(select(dbName, dbSql));
    }

    public static ResultSet select(String dbName, String dbSql) {
        return Db_operation.dbSelect(dbName, dbSql, (String) null);
    }

    public static void getDbColumnName(String dbName, String dbSql) {
        Db_operation.getDbColumnName(dbName, dbSql, (Object) null);
    }

    public static void iteratesResultSet(ResultSet oResultSet) {
        Db_operation.iteratesResultSet(oResultSet);
    }

    public static List<Map<String, String>> getResultMap(ResultSet oResultSet) {
        return Db_operation.getResultMap(oResultSet);
    }
}
