package cn.mystic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cn.mystic.service.LoggerService;
import cn.mystic.utils.OutputUtil;

@Controller
public class LoggerController {

	@Autowired
	private LoggerService loggerService;
	
    @RequestMapping(value = "/pageAll", method = RequestMethod.POST)
    public void pageAll(HttpServletRequest request, HttpServletResponse response, int pageNumber, int pageSize) {
        JSONObject res = loggerService.pageAll(pageNumber, pageSize);
        OutputUtil.print(response,res);
    }
    
    @RequestMapping(value = "/findCurrent", method = RequestMethod.POST)
    public void findCurrent(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = loggerService.findTop20();
        OutputUtil.print(response,res);
    }
}
