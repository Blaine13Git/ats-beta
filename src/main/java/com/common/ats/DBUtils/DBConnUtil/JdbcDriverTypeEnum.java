package com.common.ats.DBUtils.DBConnUtil;

public enum JdbcDriverTypeEnum {
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    MYSQL("com.mysql.jdbc.Driver");

    private final String driverClass;

    JdbcDriverTypeEnum(String driverClass2) {
        this.driverClass = driverClass2;
    }

    public String getDriverClass() {
        return this.driverClass;
    }
}
