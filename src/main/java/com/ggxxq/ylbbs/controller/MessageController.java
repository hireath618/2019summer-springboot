package com.ggxxq.ylbbs.controller;

import com.ggxxq.ylbbs.controller.repository.MessageRepository;
import com.ggxxq.ylbbs.controller.repository.UserRepository;
import com.ggxxq.ylbbs.entity.Message;
import com.ggxxq.ylbbs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;//这个会和UserController里的userRepository重复吗

    /**
     * 发消息
     * @return 消息对象
     */
    @PostMapping(value = "/sendMessage")
    public Message sendMessage(@RequestBody HashMap<String,String> paras){
        Message message = new Message();
        message.setContent(paras.get("content"));
        message.setTitle(paras.get("title"));
        message.setSender(paras.get("sender"));
        message.setReceiver(paras.get("receiver"));
        message.setSendTime(paras.get("sendTime"));
        message.getNextTimeSequence();

        return messageRepository.save(message);
    }

    /**
     * 根据完整用户名搜索其收到的所有消息
     * @return 集合
     */
    @PostMapping(value = "/myMessage")
    public List<Message> getMyMessage(@RequestBody HashMap<String,String>paras){
        return messageRepository.findByReceiverOrderByTimeSequence(paras.get("receiver"));
    }

    /**
     * 根据完整用户名搜索其未读消息数目
     * @return
     */
    @PostMapping(value = "/myUnreadMessage")
    public Integer getMyUnreadMessage(@RequestBody HashMap<String,String>paras){
        return messageRepository.findByReceiverAndIsReadFalse(paras.get("receiver")).size();
    }



////////////////////////////////////////本功能待测试
    /**
     * 发全体公告
     * @return 消息容器
     */
    @PostMapping(value = "/broadcast")
    public void broadcast(@RequestBody HashMap<String,String>paras){
        List<User> userList= userRepository.findAll();
        for(Iterator iter= userList.iterator();iter.hasNext();){
            User user=(User)iter.next();
            Message message=new Message();
            message.setTitle(paras.get("title"));
            message.setContent(paras.get("content"));
            message.setSender("系统管理员");//发送者就没必要传参了吧？
            message.setReceiver(paras.get(user.getUserName()));
            message.setSendTime(paras.get("sendTime"));
            message.getNextTimeSequence();
            messageRepository.save(message);
        }
    }
    /**
     * 设置某消息为已读
     * @param params
     * @return
     */
    @PostMapping(value = "/setIsRead")
    public boolean setIsRead(@RequestBody HashMap<String,String>params){
        Message message=messageRepository.findByReceiverAndSendTime(params.get("receiver"),params.get("sendTime"));
        if(message==null) return false;
        message.setRead(true);
        messageRepository.save(message);
        return true;
    }

}