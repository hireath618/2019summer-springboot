package com.ggxxq.ylbbs.controller.repository;

import com.ggxxq.ylbbs.entity.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends MongoRepository<Post, ObjectId>  {
    List<Post> findByTopicHeadlineOrderByPostFloorAsc(String topicHeadline);
    Post findByTopicHeadlineAndAndPostFloor(String topicHeadline, String postFloor);




}