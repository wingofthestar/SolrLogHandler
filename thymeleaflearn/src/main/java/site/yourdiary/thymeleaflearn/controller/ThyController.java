package site.yourdiary.thymeleaflearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.yourdiary.thymeleaflearn.pojo.Product;
import site.yourdiary.thymeleaflearn.pojo.User;
import site.yourdiary.thymeleaflearn.service.LoginService;
import site.yourdiary.thymeleaflearn.service.ProductService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ThyController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model){
        String welcome = "welcome to here!";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        model.addAttribute("home",welcome);
        model.addAttribute("today", strDate);
        User u = loginService.getUser();
        model.addAttribute("user", u);
        List<Product> productList = productService.getProduct();
        model.addAttribute("productList", productList);
        return "home";

    }

    @GetMapping("/txt")
    public String txt(Model model){
        User u = loginService.getUser();
        model.addAttribute("user", u);
        List<Product> productList = productService.getProduct();
        model.addAttribute("productList", productList);
        return "note";

    }

}
