package cn.mystic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cn.mystic.service.TaskService;
import cn.mystic.utils.OutputUtil;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	private String projectName = "";
	
	@Autowired
	private TaskService taskService;
	
    @RequestMapping(value = "/taskList", method = RequestMethod.GET)
    public String taskList(String projectName) {
    	this.projectName = projectName;
        return "task_list";
    }
    

	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	public void findAll(HttpServletRequest request, HttpServletResponse response, String searchPhrase, String current, String rowCount) {
		String projectName  = this.projectName;
		JSONObject res = taskService.pageAll(projectName, searchPhrase, current, rowCount);
		OutputUtil.print(response, res);
	}
	
}
