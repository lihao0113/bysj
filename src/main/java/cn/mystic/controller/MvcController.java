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

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

}
