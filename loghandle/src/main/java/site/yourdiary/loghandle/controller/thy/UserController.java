package site.yourdiary.loghandle.controller.thy;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import site.yourdiary.loghandle.entity.jpa.User;
import site.yourdiary.loghandle.pojo.ResponseInfo;
import site.yourdiary.loghandle.service.UserService;

@Controller
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResponseInfo register(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email) {
        ResponseInfo responseInfo = userService.userRegister(userName, password, email);
        return responseInfo;
    }

    @RequestMapping("/loginCheck")
    public String loginCheck(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        Model model){
       Boolean b =  userService.checkUser(userName, password);
       if (b){
           User user = userService.findUser(userName, password);
           request.getSession().setAttribute("userId", user.getUserId());
           request.getSession().setAttribute("userName", user.getUserName());
           return "redirect:/";
       }else {
           return "login";
       }
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        if (request.getSession().getAttribute("userId")!=null){
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("userName");
        return "login";
    }

}
