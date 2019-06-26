package com.lei.smart.pojo;

import java.io.Serializable;

public class WebchatSchoolKey implements Serializable {
    private Integer id;

    private String sCode;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode == null ? null : sCode.trim();
    }
}