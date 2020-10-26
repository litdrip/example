package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import persistent.UserRepository;
import service.DatabaseUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CommonController {

    @Autowired
    private DatabaseUserDetailsService userDetailsService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("message", "welcome!");
        HttpSession session = request.getSession();
        session.setAttribute("state","in");
        return mv;
    }

    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public ModelAndView signUpAction(@RequestParam(required = true) String username,
                                     @RequestParam(required = true) String password) {
        ModelAndView mv = new ModelAndView("home");
        String message;
        if (!StringUtils.hasText(username)) {
            message = "username is empty!";
        } else if (!StringUtils.hasText(password)) {
            message = "password is empty!";
        } else {
            userDetailsService.createUser(username, password);
            message = "sign up success.";
        }
        mv.addObject("message", message);
        return mv;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
