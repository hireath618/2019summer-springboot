package com.ggxxq.ylbbs.controller.repository;

import com.ggxxq.ylbbs.entity.Topic;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TopicRepository extends MongoRepository<Topic, ObjectId> {

    List<Topic> findByTopicHeadlineLike(String topicName);//标题关键字模糊查找帖子

    Topic findByTopicHeadline(String topicHeadline);//根据标题名精确查找, (如果有两个同名帖怎么办)

    //分类查看帖子
    List<Topic> findByTopicType(String topicType);

    List<Topic> findByStarterName(String starterName);

    List<Topic> findByTopicTypeOrderByTimeSequenceDesc(String t);

    //删除
    //boolean deleteByTopicHeadline(String topicHeadline);


}
