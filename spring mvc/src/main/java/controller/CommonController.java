package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

//    @GetMapping({"/","/index"})
//    public String index(){
//        return "redirect:index.html";
//    }


}
