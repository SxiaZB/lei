package com.lei.smart.im;

import java.io.Serializable;
import java.util.Date;

public class ChatUser implements Serializable {
    private Long id;

    private String loginName;

    private String password;

    private String nickName;

    private Date createTime;

    private Integer sex;

    private Integer age;

    private String mobile;

    private String signature;

    private String rigion;

    private String qrCode;

    private String chatCode;

    private String avatar;
    private String groupId;
    private Long roleId;
    private Integer deleted;
private ChatRole chatRole;

    public ChatRole getChatRole() {
        return chatRole;
    }

    public void setChatRole(ChatRole chatRole) {
        this.chatRole = chatRole;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getRigion() {
        return rigion;
    }

    public void setRigion(String rigion) {
        this.rigion = rigion == null ? null : rigion.trim();
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public String getChatCode() {
        return chatCode;
    }

    public void setChatCode(String chatCode) {
        this.chatCode = chatCode == null ? null : chatCode.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }
}