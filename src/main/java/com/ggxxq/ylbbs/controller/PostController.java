package com.ggxxq.ylbbs.controller;

import com.ggxxq.ylbbs.controller.repository.MessageRepository;
import com.ggxxq.ylbbs.controller.repository.PostRepository;
import com.ggxxq.ylbbs.controller.repository.TopicRepository;
import com.ggxxq.ylbbs.entity.Message;
import com.ggxxq.ylbbs.entity.Post;
import com.ggxxq.ylbbs.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MessageRepository messageRepository;

//    查看某帖子下所有回复
    @PostMapping(value ="/showposts")
    public List<Post> showPost(@RequestBody HashMap<String,String> params) {
        return postRepository.findByTopicHeadlineOrderByPostFloorAsc(params.get("headline"));
    }
//
//
//    回复帖子(在生成一个新的post对象)
    @PostMapping(value ="/release")
    public Post savePost(@RequestBody HashMap<String,String> params){
        Post post = new Post();
        post.setTopicHeadline(params.get("headline"));
        post.setPostUser(params.get("user"));
        post.setPostContent(params.get("content"));
        post.setPostDate(params.get("date"));
        //更新topic的回复日期和楼层，并获取楼层存到post
        Topic topic = topicRepository.findByTopicHeadline(params.get("headline"));
        post.setPostFloor(topic.addaFloor());
        topic.setReplyDate(params.get("date"));
        //向楼主发送通知
        Message message = new Message();
        message.setTitle(params.get("headline"));
        message.setContent("我回复了你的帖子，快来看看我是怎么说的吧！");
        message.setSender(params.get("user"));
        message.setReceiver(topic.getStarterName());
        message.setSendTime(params.get("date"));
        message.getNextTimeSequence();
        //更新message\topic\post
        topicRepository.save(topic);
        messageRepository.save(message);
        return postRepository.save(post);
    }

    //
    //    给回复点赞 类似给帖子点赞
    @PostMapping(value="/likePost")
    public Integer likePost (@RequestBody HashMap<String,String> params)
    {
        Post post = postRepository.findByTopicHeadlineAndAndPostFloor(params.get("headline"),params.get("floor"));
        post.addLikedNumber();
        return postRepository.save(post).getPostLikedNumber();
    }

//    删除回复 但楼层不变

}
