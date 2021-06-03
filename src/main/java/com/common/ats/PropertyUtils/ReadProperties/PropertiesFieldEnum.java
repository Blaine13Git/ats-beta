package com.common.ats.PropertyUtils.ReadProperties;

public enum PropertiesFieldEnum {
    TD_SQL("TD_SQL"),
    TD_TableName("TD_TableName"),
    TD_FilesPath("TD_FilesPath");
    
    private String PropertiesFieldEnum;

    private PropertiesFieldEnum(String rss) {
        this.PropertiesFieldEnum = rss;
    }

    public String getValue() {
        return this.PropertiesFieldEnum;
    }

    public void setValue(String propertiesFieldEnum) {
        this.PropertiesFieldEnum = propertiesFieldEnum;
    }
}
