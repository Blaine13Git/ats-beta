package com.common.ats.DBUtils.DbOperationUtils;

import com.common.ats.DBUtils.DBConnUtil.OracleConnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Db_operation extends OracleConnect {
    public static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(Db_operation.class);
    public static OracleConnect oOracleConnect = null;
    private static PreparedStatement oPreparedStatement = null;
    public static ResultSet oResultSet = null;

    public Db_operation(String tableName) {
        super(tableName);
    }

    protected static Connection getConn(String dbName) {
        oOracleConnect = new OracleConnect(dbName);
        conn = OracleConnect.conn;
        return conn;
    }

    protected static ResultSet dbSelect(String dbName, String dbSql, String dbConfigKey) {
        try {
            oPreparedStatement = getConn(dbName).prepareStatement(dbSql);
            oResultSet = oPreparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            OracleConnect.closeDbConn();
            logger.error("dbSelect 查询----数据库操作出错,请检查数据库配置信息.\r\n  ");
        }
        return oResultSet;
    }

    protected static int dbUpdate(String dbName, String dbSql, String dbConfigKey) {
        try {
            oPreparedStatement = getConn(dbName).prepareStatement(dbSql);
            return oPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            OracleConnect.closeDbConn();
            logger.error("dbUpdate 更新操作----数据库操作出错,请检查数据库配置信息.\r\n  ");
            return 0;
        }
    }

    protected static void getDbColumnName(String dbName, String dbSql, Object object) {
        try {
            ResultSetMetaData metal = oResultSet.getMetaData();
            int i = metal.getColumnCount();
            for (int j = 1; j <= i; j++) {
                logger.info(metal.getColumnName(j));
            }
            logger.info("\r\n");
        } catch (SQLException e) {
            logger.error("数据库结果遍历异常，请检查数据!");
        }
    }

    protected static void iteratesResultSet(ResultSet oResultSet2) {
        try {
            ResultSetMetaData metal = oResultSet2.getMetaData();
            int i = metal.getColumnCount();
            while (oResultSet2.next()) {
                for (int j = 1; j <= i; j++) {
                    String okey = metal.getColumnName(j);
                    String rs = metal.getColumnClassName(j);
                    if (rs.equals("java.sql.Timestamp")) {
                        logger.info("当前返回的参数内容是： " + okey + ":" + oResultSet2.getLong(j));
                    } else if (rs.equals("java.sql.Date")) {
                        logger.info("当前返回的参数内容是： " + okey + ":" + oResultSet2.getLong(j));
                    } else {
                        logger.info("当前返回的参数内容是： " + okey + ":" + oResultSet2.getString(j));
                    }
                }
            }
            logger.info("\r\n");
        } catch (SQLException e) {
            logger.error("数据库结果遍历异常，请检查数据---iteratesResultSet!");
        }
    }

    protected static List<Map<String, String>> getResultMap(ResultSet oResultSet2) {
        String value;
        List<Map<String, String>> list = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = oResultSet2.getMetaData();
            int count = rsmd.getColumnCount();
            while (oResultSet2.next()) {
                Map<String, String> hm = new HashMap<>();
                for (int i = 1; i <= count; i++) {
                    String key = rsmd.getColumnLabel(i);
                    String rs = rsmd.getColumnClassName(i);
                    if (rs.equals("java.sql.Timestamp") || rs.equals("java.sql.Date")) {
                        value = String.valueOf(oResultSet2.getLong(i));
                    } else {
                        value = oResultSet2.getString(i);
                    }
                    logger.info("当前返回的参数内容是： " + key + ":" + value);
                    hm.put(key, value);
                }
                list.add(hm);
            }
        } catch (Exception e) {
            logger.error("将ResultSet转换成Map失败:转换出现异常.\r\n" + e);
        }
        return list;
    }
}
