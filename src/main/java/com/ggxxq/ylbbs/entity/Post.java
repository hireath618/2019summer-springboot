package com.ggxxq.ylbbs.entity;

import org.bson.types.ObjectId;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.io.Serializable;

/**
 * @Description 帖子的2楼及以后的每一楼均为一个Post实例
 */
@Document(collection = "Post")
public class Post implements Serializable {
    private static final long SerialVersionUID = 3L;

    @Id
    private ObjectId postID;//回帖ID
    @Field("postUser")
    private String postUser;//发布用户名
    @Field("topicHeadline")
    private String topicHeadline;//帖子标题
    @Field("postContent")
    private String postContent = null;//回复的内容
    @Field("postLikedNumber")
    private Integer postLikedNumber = 0;//点赞数
    @Field("PostFloor")
    private Integer postFloor = 0;//当前所在楼层数
    @Field("PostDate")
    private String postDate;

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public String getTopicHeadline() {
        return topicHeadline;
    }

    public void setTopicHeadline(String topicHeadline) {
        this.topicHeadline = topicHeadline;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Integer getPostLikedNumber() {
        return postLikedNumber;
    }

    public void setPostLikedNumber(Integer postLikedNumber) {
        this.postLikedNumber = postLikedNumber;
    }

    public void setPostFloor(Integer postFloor) {
        this.postFloor = postFloor;
    }
    public void addLikedNumber() { postLikedNumber++; }

    public Integer getPostFloor() {
        return postFloor;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDate() {
        return postDate;
    }
}
