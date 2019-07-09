package com.lei.smart;

/**
 * 需要分表的必须在这里声明  初始化 利用反射进行初始化
 * dingjianlei@xdf.cn
 */
public enum TableNameEnum {
    ORDER("user", "com.lei.smart.UserSplitStrategy");

    private String value;
    private String desc;

    private TableNameEnum(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
