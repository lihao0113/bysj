package cn.mystic.controller;

import cn.mystic.domain.SysUser;
import cn.mystic.service.SysUserService;
import cn.mystic.utils.OutputUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by lihao on 2018/1/26.
 */
@Controller
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public void findAll(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = userService.findAll();
        OutputUtil.print(response,res);
    }

    @RequestMapping(value = "/pageAll", method = RequestMethod.POST)
    public void pageAll(HttpServletRequest request, HttpServletResponse response, SysUser user, int pageNumber, int pageSize) {
        JSONObject res = userService.pageAll(pageNumber, pageSize);
        OutputUtil.print(response,res);
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        JSONObject res = userService.signin(username, password);
        OutputUtil.print(response,res);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(HttpServletRequest request, HttpServletResponse response, SysUser user) {
    	System.out.println("jinlaile");
        JSONObject res = userService.add(user);
        OutputUtil.print(response,res);
    }
}
