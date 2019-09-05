package com.ggxxq.ylbbs.controller;

import com.ggxxq.ylbbs.controller.repository.PostRepository;
import com.ggxxq.ylbbs.controller.repository.UserRepository;
import com.ggxxq.ylbbs.controller.repository.TopicRepository;
import com.ggxxq.ylbbs.entity.Post;
import com.ggxxq.ylbbs.entity.Topic;
import com.ggxxq.ylbbs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

     //查看所有帖子
     @PostMapping(value ="/showAll")
     public List<Topic> showTopic(){
         return topicRepository.findAll();
     }

    //时间顺序查看指定栏目的帖子
    /**
     * 1课程推荐
     * @param params page-页号
     * @return 如果page=0，返回整个集合；页号对应第1条帖子不存在，返回null；页号对应第10条帖子不存在，返回[page*10-10,size-1]
     */
    @PostMapping(value ="/show")
    public List<Topic> showTopic(@RequestBody HashMap<String,String>params) {
        int page=Integer.parseInt(params.get("page"));
        List<Topic> topicList = topicRepository.findByTopicTypeOrderByTimeSequenceDesc(params.get("type"));
        int size=topicList.size();
        if(page<0)
            return null;
        else if(page==0) 
            return topicList;
        else if(page*10-10>=size)
            return null;
        else if(page*10-1>=size)
            return topicList.subList(page*10-10,size);
        else
            return topicList.subList(page*10-10,page*10);
    }
    


    //查看某用户的所有帖子
        @PostMapping(value="/showmytopic")
        public List<Topic> showMyTopic(@RequestBody HashMap<String,String> params){
            return topicRepository.findByStarterName(params.get("username"));
        }
    //
    //保存帖子（发布帖子）
    @PostMapping(value ="/release")
    public Topic saveTopic(@RequestBody HashMap<String,String> params){
        if(topicRepository.findByTopicHeadline(params.get("topicHeadline")) != null)
            return null;
        Topic topic = new Topic();
        topic.setTopicHeadline(params.get("topicHeadline"));
        topic.setStarterName(params.get("starterName"));
        topic.setTopicContent(params.get("topicContent"));
        topic.setTopicDate(params.get("date"));
        topic.setReplyDate(params.get("date"));
        topic.setTopicType(params.get("type"));
        topic.addaFloor();
        topic.getNextTimeSequence();
        User user = userRepository.findByUserName(params.get("starterName"));
        user.setUserArticleNum(user.getUserArticleNum()+1);
        return topicRepository.save(topic);
    }

    //获取帖子
    @PostMapping(value = "/getByHeadline")
    public Topic getTopic(@RequestBody HashMap<String,String> params)
    {
        return topicRepository.findByTopicHeadline(params.get("headline"));
    }

    //搜索帖子
    @PostMapping(value = "/searchtopic")
    public List<Topic> searchTopic(@RequestBody HashMap<String,String> params)
    {
        List<Topic> topicList = topicRepository.findByTopicHeadlineLike(params.get("headline"));
        if(params.get("type")!=null){
            List<String> typeList = new ArrayList<String>();
            List<Topic> result = null;
            typeList.add(params.get("type"));
            result = topicList.stream()
                    .filter((Topic s) -> typeList.contains(s.getTopicType()))
                    .collect(Collectors.toList());
            return result;
        }
        return topicList;
    }

    /*删除帖子*/
     @PostMapping(value="/deletetopic")
     public boolean deleteTopic(@RequestBody HashMap<String,String> params)
     {
         Topic topic = topicRepository.findByTopicHeadline(params.get("headline"));
         User user = userRepository.findByUserName(topic.getStarterName());
         if (topic!=null){
             topicRepository.delete(topic);
             List<Post> postList= postRepository.findByTopicHeadlineOrderByPostFloorAsc(params.get("headline"));
             for(Iterator iter = postList.iterator(); iter.hasNext();) {
                 Post post = (Post) iter.next();
                 postRepository.delete(post);
             }
             user.setUserArticleNum(user.getUserArticleNum()-1);
             return true;
         }
         else
             return false;
     }


    /* 给帖子点赞
     * @return 帖子的新点赞数
     */
    @PostMapping(value="/likeTopic")
    public Integer likeTopic (@RequestBody HashMap<String,String> params)
    {
        Topic topic = topicRepository.findByTopicHeadline(params.get("headline"));
        topic.addLikedNumber();
        return topicRepository.save(topic).getTopicLikedNumber();
    }

    /*
    待办事项：
    1. 查找指定用户发过的帖子，以时间倒序排序
     */
}
