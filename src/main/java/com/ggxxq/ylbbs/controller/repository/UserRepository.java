package com.ggxxq.ylbbs.controller.repository;

import com.ggxxq.ylbbs.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    //用户名精确查找
    User findByUserName(String userName);

    List<User> findByUserNameLike(String userName);

}
