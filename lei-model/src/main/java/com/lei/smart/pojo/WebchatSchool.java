package com.lei.smart.pojo;

import java.io.Serializable;

public class WebchatSchool extends WebchatSchoolKey implements Serializable {
    private String sName;

    private String sDescription;

    private String sAddress;

    private String sPostCode;

    private Integer sPhone;

    private String sFax;

    private String sHomePage;

    private String sMemo;

    private Integer bValid;

    private String sSchoolEnglishName;

    private Integer sorder;

    private static final long serialVersionUID = 1L;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName == null ? null : sName.trim();
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription == null ? null : sDescription.trim();
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress == null ? null : sAddress.trim();
    }

    public String getsPostCode() {
        return sPostCode;
    }

    public void setsPostCode(String sPostCode) {
        this.sPostCode = sPostCode == null ? null : sPostCode.trim();
    }

    public Integer getsPhone() {
        return sPhone;
    }

    public void setsPhone(Integer sPhone) {
        this.sPhone = sPhone;
    }

    public String getsFax() {
        return sFax;
    }

    public void setsFax(String sFax) {
        this.sFax = sFax == null ? null : sFax.trim();
    }

    public String getsHomePage() {
        return sHomePage;
    }

    public void setsHomePage(String sHomePage) {
        this.sHomePage = sHomePage == null ? null : sHomePage.trim();
    }

    public String getsMemo() {
        return sMemo;
    }

    public void setsMemo(String sMemo) {
        this.sMemo = sMemo == null ? null : sMemo.trim();
    }

    public Integer getbValid() {
        return bValid;
    }

    public void setbValid(Integer bValid) {
        this.bValid = bValid;
    }

    public String getsSchoolEnglishName() {
        return sSchoolEnglishName;
    }

    public void setsSchoolEnglishName(String sSchoolEnglishName) {
        this.sSchoolEnglishName = sSchoolEnglishName == null ? null : sSchoolEnglishName.trim();
    }

    public Integer getSorder() {
        return sorder;
    }

    public void setSorder(Integer sorder) {
        this.sorder = sorder;
    }
}