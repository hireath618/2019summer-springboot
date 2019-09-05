package com.ggxxq.ylbbs.controller;

import com.ggxxq.ylbbs.controller.repository.MessageRepository;
import com.ggxxq.ylbbs.controller.repository.UserRepository;
import com.ggxxq.ylbbs.entity.Message;
import com.ggxxq.ylbbs.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    /**
     * 登录功能
     * 用户名不存在返回0，密码错返回1，登录成功返回2
     */
    @PostMapping(value="/login")
    public User UserLogin(@RequestBody HashMap<String,String> params){
        User user =  userRepository.findByUserName(params.get("name"));
        if (user != null && params.get("password").equals(user.getUserPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 注册 过程
     * @param params
     * @return 若用户名已存在，则返回null，表示不能注册同名用户; 否则,返回注册成功的用户
     */
    @PostMapping(value ="/register")
    public User saveUser(@RequestBody HashMap<String,String> params) {
        if (userRepository.findByUserName(params.get("name")) != null)
            return null;
        else {
            User user = new User();
            user.setUserName(params.get("name"));
            user.setUserPassword(params.get("password"));
            user.setRegisterTime(params.get("time"));
            //自动发送新手通知
            Message message = new Message();
            message.setTitle("新手上路");
            message.setContent("请先阅读新手上路。");
            message.setSender("系统管理员");
            message.setReceiver(params.get("name"));
            message.setSendTime(params.get("time"));
            message.getNextTimeSequence();
            messageRepository.save(message);

            return userRepository.save(user);
        }
    }

    //   通过用户名搜索用户
    @PostMapping(value ="/byname")
    public List<User> searchByUserName(@RequestBody HashMap<String,String> params)
    {
        return userRepository.findByUserNameLike(params.get("str"));
    }

    //通过用户名完全匹配
    @PostMapping(value ="/finduser")
    public User findUserByName(@RequestBody HashMap<String,String> params)
    {
        System.out.println(params.get("name"));
        return userRepository.findByUserName(params.get("name"));
    }

    /**修改密码
     * @param params 此参数调用get()方法，name-用户名，oldpassword-原密码，newpassword-新密码
     * @return 修改后的用户,原密码错误则返回null
     */
    @PostMapping(value="/resetpsw")
    public User modifyPassword(@RequestBody  HashMap<String,String> params){

        User user =  userRepository.findByUserName(params.get("name"));
        if(!user.getUserPassword().equals(params.get("oldpassword"))) {
            return null;
        }
        user.setUserPassword(params.get("newpassword"));
        return userRepository.save(user);
    }

    //修改个人签名
    @PostMapping(value="/resetlog")
    public boolean resetLog(@RequestBody HashMap<String,String> params)
    {
        User user = userRepository.findByUserName(params.get("name"));
        if (user!=null){
            user.setUserLog(params.get("newlog"));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    //修改个人签名
    @PostMapping(value="/resetavatar")
    public boolean resetAvatar(String name, String avatar)
    {
        User user = userRepository.findByUserName(name);
        if (user!=null){
            user.setUserAvatar(avatar);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    //成功阅读新手须知
    @PostMapping(value="/read")
    public void readUserInfo(@RequestBody HashMap<String,String> params)
    {
        User user = userRepository.findByUserName(params.get("name"));
        user.setRead(true);
        userRepository.save(user);
    }


    //管理员功能
    //显示所有用户
    @PostMapping(value="/show")
    public List<User> showUser()
    {
        return userRepository.findAll();
    }

    /**
     * 设置用户禁言状态（解除只能管理员手动解禁，否则禁言无限期）
     * @param paras name-被操作的用户名，status-状态：true为禁言，false为解禁
     * @return 被操作用户的实例
     */
    @PostMapping(value = "/setSilence")
    public Boolean setSilence(@RequestBody HashMap<String,Object>params){
        User user = userRepository.findByUserName((String) params.get("name"));
        user.setBan((Boolean) params.get("ifBan"));
        return userRepository.save(user).isBan();
    }

    @PostMapping(value="/delete")
    public User deleteUser(@RequestBody HashMap<String,String>params)
    {
        User user = userRepository.findByUserName(params.get("name"));
        if (user!=null)
             userRepository.delete(user);
        return user;
    }

}