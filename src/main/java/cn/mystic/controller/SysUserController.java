package cn.mystic.controller;

import cn.mystic.annotation.CurrentUser;
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
    
    @RequestMapping(value = "/myself", method = RequestMethod.POST)
    public void findMyself(HttpServletRequest request, HttpServletResponse response, @CurrentUser SysUser user) {
        JSONObject res = userService.findOne(user);
        OutputUtil.print(response,res);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public void findAll(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = userService.findAll();
        OutputUtil.print(response,res);
    }
    
    @RequestMapping(value = "/findAll1", method = RequestMethod.POST)
    public void findAll1(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = userService.findAll1();
        OutputUtil.print(response,res);
    }

    @RequestMapping(value = "/pageAll", method = RequestMethod.POST)
    public void pageAll(HttpServletRequest request, HttpServletResponse response, String user, int pageNumber, int pageSize) {
    	JSONObject jsonObject = JSONObject.parseObject(user);
    	SysUser sysUser = jsonObject.toJavaObject(SysUser.class);
    	JSONObject res = userService.pageAll(sysUser, pageNumber, pageSize);
        OutputUtil.print(response,res);
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        JSONObject res = userService.signin(username, password);
        OutputUtil.print(response,res);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(HttpServletRequest request, HttpServletResponse response, String user) {
    	JSONObject jsonObject = JSONObject.parseObject(user);
    	SysUser sysUser = jsonObject.toJavaObject(SysUser.class);
        JSONObject res = userService.add(sysUser);
        OutputUtil.print(response,res);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(HttpServletRequest request, HttpServletResponse response, String user) {
    	JSONObject jsonObject = JSONObject.parseObject(user);
    	SysUser sysUser = jsonObject.toJavaObject(SysUser.class);
        JSONObject res = userService.update(sysUser);
        OutputUtil.print(response,res);
    }
    
    
    @RequestMapping(value = "/updatePass", method = RequestMethod.POST)
    public void updatePass(HttpServletRequest request, HttpServletResponse response, @CurrentUser SysUser currentUser, String oldPassword, String newPassword) {
    	JSONObject res = userService.updatePass(currentUser, oldPassword, newPassword);
        OutputUtil.print(response,res);
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, String id) {
    	Integer userId = Integer.parseInt(id);
        JSONObject res = userService.delete(userId);
        OutputUtil.print(response,res);
    }
}
