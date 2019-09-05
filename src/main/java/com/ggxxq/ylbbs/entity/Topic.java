package com.ggxxq.ylbbs.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description 话题，相当于帖子的1Floor; 2L以后用Post类
 *
 */
@Document(collection = "Topic")
public class Topic implements Serializable {
    private static final long SerialVersionUID = 2L;
    @Id
    private ObjectId topicID;
    @Field("Headline")
    private String topicHeadline = null;//帖子标题
    @Field("StarterName")
    private String starterName = null;//创建者名字
    @Field("LikedNumber")
    private Integer topicLikedNumber = 0;//点赞数
    @Field("Content")
    private String topicContent = null;//1Floor内容
    @Field("TopicFloor")
    private Integer topicFloor = 0;//总楼层数
    @Field("TopicDate")
    private String topicDate;//发帖日期，更新为String
    @Field("TopicType")
    private String topicType;//帖子类型1课程推荐2题目探讨3校园周边
    @Field("replyDate")
    private String replyDate = null;//最新回帖日期，若无回帖则为发帖日期
     @Field("timeSequence")
    private Integer timeSequence=0;//时间顺序，用于排序(因为不知道怎么用Date类或String类排序

    private static int timeCounter = 0;//时间顺序计数器，每生成一个新topic,计数器+1


    //生成下一个时序
    public void getNextTimeSequence (){
        this.timeSequence= timeCounter++;
    }

    public Integer addaFloor()
    {
        this.topicFloor++;
        return topicFloor;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public void setTopicID(ObjectId topicID) {
        this.topicID = topicID;
    }

    public void setTopicHeadline(String topicHeadline) {
        this.topicHeadline = topicHeadline;
    }

    public void setTopicLikedNumber(Integer topicLikedNumber) {
        this.topicLikedNumber = topicLikedNumber;
    }



    public Integer getTopicLikedNumber() {
        return topicLikedNumber;
    }

    public static long getSerialVersionUID() {
        return SerialVersionUID;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public String getTopicHeadline() {
        return topicHeadline;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

//    public void setTopicType(String topicType) {
//        this.topicType=Integer.parseInt(topicType);
//    }

    public String getStarterName() {
        return starterName;
    }

    public void setStarterName(String starterName) {
        this.starterName = starterName;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(String topicDate) {
        this.topicDate = topicDate;
    }

    public void addLikedNumber() { topicLikedNumber++; }

    public Integer getTimeSequence() {
        return timeSequence;
    }

    public void setTimeSequence(Integer timeSequence) {
        this.timeSequence = timeSequence;
    }

    public Integer getTopicFloor() {
        return topicFloor;
    }

    public void setTopicFloor(Integer topicFloor) {
        this.topicFloor = topicFloor;
    }
}
