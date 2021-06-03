package com.common.ats.DBUtils.DBConnUtil;

import com.common.ats.DBUtils.DBconfigFileUtil.GetDbConfigInfo;
import com.common.ats.LoggerUtils.TcRunLog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;

public class OracleConnect {
    public static Connection conn = null;
    public static String dbNname = null;
    public static String dbPassword = null;
    public static String dbSchema = null;
    public static String dbUrl = null;
    public static ResultSet result = null;
    public static PreparedStatement stmt = null;
    public JdbcDriverTypeEnum oJdbcDriverTypeEnum;

    public OracleConnect(String tableName) {
        this(tableName, null);
    }

    public OracleConnect(String tableName, String dbConfigKey) {
        this.oJdbcDriverTypeEnum = null;
        initDBConfig(GetDbConfigInfo.oGetDbConfigInfo(tableName, dbConfigKey));
        initDBConn();
        checkDbConn(tableName);
    }

    public void initDBConfig(OracleDriverConfig oracleDriver) {
        dbUrl = oracleDriver.getDbUrl().toLowerCase();
        try {
            if (StringUtils.isBlank(dbUrl)) {
                throw new Exception("获取到的数据库配置URL地址为空，请检查数据库配置文件是否配置.");
            }
            if (dbUrl.contains("mysql")) {
                this.oJdbcDriverTypeEnum = JdbcDriverTypeEnum.MYSQL;
            }
            if (dbUrl.contains("oracle")) {
                this.oJdbcDriverTypeEnum = JdbcDriverTypeEnum.ORACLE;
            }
            Class.forName(this.oJdbcDriverTypeEnum.getDriverClass());
            dbNname = oracleDriver.getName();
            dbPassword = oracleDriver.getPassword();
            dbSchema = oracleDriver.getSchema();
            if (!dbUrl.endsWith(dbSchema)) {
                dbUrl = dbUrl + "/" + dbSchema;
            }
        } catch (Exception e) {
            new RuntimeException("数据库配置信息出错，请检查配置。");
        }
    }

    public static Connection initDBConn() {
        try {
            if (TcRunLog.isDebugEnabled()) {
                TcRunLog.info("当前数据库配置项信息如下: dbUrl =【" + dbUrl + "】,dbNname=【" + dbNname + "】,dbPassword=【" + dbPassword + "】");
            }
            conn = DriverManager.getConnection(dbUrl, dbNname, dbPassword);
        } catch (SQLException e) {
            TcRunLog.error("数据库连接异常,请检查数据库配置信息.\r\n  ");
            closeDbConn();
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeDbConn() {
        try {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            TcRunLog.error("关闭数据库连接出错. 错误信息如下： \r\n" + e);
        }
    }

    public void checkDbConn(String dbName) {
        try {
            stmt = conn.prepareStatement("select * from " + dbName + " limit 0,1");
            result = stmt.executeQuery();
        } catch (Exception e) {
            closeDbConn();
        }
    }
}
