package cn.mystic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lihao on 2018/1/26.
 */
@Controller
public class MvcController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/fileList", method = RequestMethod.GET)
    public String fileUpDown() {
        return "file";
    }
    

    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    public String add() {
        return "user_add";
    }
    

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String userList() {
        return "user_list";
    }
    
}
