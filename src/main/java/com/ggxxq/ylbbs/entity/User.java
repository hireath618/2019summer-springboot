package com.ggxxq.ylbbs.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection="user")
public class User implements Serializable {
    private static final long SerialVersionUID = 1L;
    @Id
    private ObjectId userID;
    @Field("user_name")
    private String userName = null;
    @Field("user_password")
    private String userPassword = null;
    @Field("isAdmin")
    private boolean isAdmin = false;//是否是管理员
    @Field("isRead")
    private boolean isRead = false;//是否阅读过新手上路
    @Field("isBan")
    private boolean isBan = false;//是否
    @Field("userLog")
    private String userLog = "这个人有点懒，还没有编辑个性签名。";
    @Field("registerTime")
    private String registerTime;
    @Field("userArticleNum")
    private Integer userArticleNum = 0;
    @Field("userAvatar")
    private String userAvatar = null;


    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setUserID(ObjectId userID) {
        this.userID = userID;
    }

    public ObjectId getUserID() {
        return userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    public boolean isBan() {
        return isBan;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setUserLog(String userLog) {
        this.userLog = userLog;
    }

    public String getUserLog() {
        return userLog;
    }

    public Integer getUserArticleNum() {
        return userArticleNum;
    }

    public void setUserArticleNum(Integer userArticleNum) {
        this.userArticleNum = userArticleNum;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}