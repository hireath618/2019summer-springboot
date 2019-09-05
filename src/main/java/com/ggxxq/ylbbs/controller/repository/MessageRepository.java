package com.ggxxq.ylbbs.controller.repository;

import com.ggxxq.ylbbs.entity.Message;
import com.ggxxq.ylbbs.entity.Topic;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    public List<Message> findByReceiverOrderByTimeSequence(String messageReceiver);
    public List<Message> findByReceiverAndIsReadFalse(String messageReceiver);
    public Message findByReceiverAndSendTime(String receiver,String sendTime);
}
