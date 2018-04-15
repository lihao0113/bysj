package cn.mystic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cn.mystic.annotation.CurrentUser;
import cn.mystic.domain.SysUser;
import cn.mystic.domain.Task;
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

	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	public void findOne(HttpServletRequest request, HttpServletResponse response, String id) {
		JSONObject res = taskService.findOne(id);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/pageAll", method = RequestMethod.POST)
	public void findAll(HttpServletRequest request, HttpServletResponse response, String searchPhrase, String current,
			String rowCount) {
		String projectName = this.projectName;
		JSONObject res = taskService.pageAll(projectName, searchPhrase, current, rowCount);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response, String task,
			@CurrentUser SysUser currentUser) {
		String projectName = this.projectName;
		JSONObject jsonObject = JSONObject.parseObject(task);
		Task bean = jsonObject.toJavaObject(Task.class);
		JSONObject res = taskService.add(bean, currentUser, projectName);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, String task,
			@CurrentUser SysUser currentUser) {
		JSONObject jsonObject = JSONObject.parseObject(task);
		Task bean = jsonObject.toJavaObject(Task.class);
		JSONObject res = taskService.update(bean, currentUser);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/updateZhipai", method = RequestMethod.POST)
	public void updateZhipai(HttpServletRequest request, HttpServletResponse response, String task,
			@CurrentUser SysUser currentUser) {
		JSONObject jsonObject = JSONObject.parseObject(task);
		Task bean = jsonObject.toJavaObject(Task.class);
		JSONObject res = taskService.updateZhipai(bean, currentUser);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response, String id) {
		Integer taskId = Integer.parseInt(id);
		JSONObject res = taskService.delete(taskId);
		OutputUtil.print(response, res);
	}
	
	@RequestMapping(value = "/finshed", method = RequestMethod.POST)
	public void finshed(HttpServletRequest request, HttpServletResponse response, String id, @CurrentUser SysUser sysUser) {
		Integer taskId = Integer.parseInt(id);
		JSONObject res = taskService.finshed(taskId, sysUser.getUsername());
		OutputUtil.print(response, res);
	}
	
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public void start(HttpServletRequest request, HttpServletResponse response, String id) {
		Integer taskId = Integer.parseInt(id);
		JSONObject res = taskService.start(taskId);
		OutputUtil.print(response, res);
	}

}
