package com.ggxxq.ylbbs.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection="Message")
public class Message implements Serializable {
    private static final long SerialVersionUID = 4L;

    @Id
    private ObjectId MessageID;
    @Field("messageSender")
    private String sender;//消息发送者
    @Field("messageReceiver")
    private String receiver;//消息接收者
    @Field("messageTitle")
    private String title;
    @Field("messageContent")
    private String content = null;//消息内容
    @Field("isRead")
    private boolean isRead = false;//未读
    @Field("sendTime")
    private String sendTime;//发送时间
    @Field("timeSequence")
    private Integer timeSequence=0;//时间顺序，用于排序(因为不知道怎么用Date类或String类排序

    private static int timeCounter = 0;//时间顺序计数器，每生成一个新topic,计数器+1

    //生成下一个时序
    public void getNextTimeSequence (){
        this.timeSequence= timeCounter++;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public ObjectId getMessageID() {
        return MessageID;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

}