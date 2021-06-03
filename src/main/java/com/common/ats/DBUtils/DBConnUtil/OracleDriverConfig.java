package com.common.ats.DBUtils.DBConnUtil;

public class OracleDriverConfig {
    private String dbUrl;
    private String name;
    private String oracleDriver = "oracle.jdbc.driver.OracleDriver";
    private String password;
    private String schema;

    public String getOracleDriver() {
        return this.oracleDriver;
    }

    public void setOracleDriver(String oracleDriver2) {
        this.oracleDriver = oracleDriver2;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public void setDbUrl(String dbUrl2) {
        this.dbUrl = dbUrl2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getSchema() {
        return this.schema;
    }

    public void setSchema(String schema2) {
        this.schema = schema2;
    }

    public static String printDriverInfo(OracleDriverConfig odc) {
        return "数据库配置信息如下： \r\n        数据库连接串：" + odc.getDbUrl() + "  \r\n        数据库访问名:  " + odc.getName() + "  \r\n        数据库访问密码： " + odc.getPassword() + "\r\n        数据库对应Schema: " + odc.getSchema();
    }
}
