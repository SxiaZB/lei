package com.lei.smart.im;


import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class ChatMessage {
    private int version = 1;
    private String proto;
    @Id
    private String id;
    private String level;//等级
    private String chatName;//说话者姓名
    private String timeTag;// 显示人性化的时间标记，如：5分钟前，1小时前等等
    private String groupId;// 组号id
    private Integer chatCode;// 信息模式：普通聊天 还是系统消息  权限认证
    private Integer messageType;// 消息类型：1,文本；2，图片；3，小视频；4，文件；5，地理位置；6，语音；7，视频通话
    private String message;// 文本消息内容
    private boolean succ;// 是否成功
    private String suffix;// 二进制文件后缀，如txt,acc,jpg,png,gif等
    private String messageFileName;// 文件名字
    private String binaryAddress;// 二进制文件的存储位置。上传之后，将文件存储在指定的位置，然后将binary清空
    private String imageBase64;// 文件的base64字符串
    private String messageFrom;//来源
    private String messageTo;//去向
    private List<String> userIdList;//去向是数组
    private Integer terminalType;//终端类型
    private Date createDate;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(String timeTag) {
        this.timeTag = timeTag;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getChatCode() {
        return chatCode;
    }

    public void setChatCode(Integer chatCode) {
        this.chatCode = chatCode;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMessageFileName() {
        return messageFileName;
    }

    public void setMessageFileName(String messageFileName) {
        this.messageFileName = messageFileName;
    }

    public String getBinaryAddress() {
        return binaryAddress;
    }

    public void setBinaryAddress(String binaryAddress) {
        this.binaryAddress = binaryAddress;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
