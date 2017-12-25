package site.yourdiary.thymeleaflearn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @RequestMapping("/api/hello")
    public String sayHello(){
        return "hello!";
    }
}
