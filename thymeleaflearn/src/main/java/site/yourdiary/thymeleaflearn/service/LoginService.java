package site.yourdiary.thymeleaflearn.service;

import org.springframework.stereotype.Service;
import site.yourdiary.thymeleaflearn.pojo.User;

@Service
public class LoginService {

    public User getUser(){
        User user = new User();
        user.setName("崔希伟");
        user.setPhoneNumber(13588042773L);
        user.setEmail("caihonghai2011@sina.com");
        user.setPassword("123456");
        user.setRole("manager");
        return user;
    }
}
