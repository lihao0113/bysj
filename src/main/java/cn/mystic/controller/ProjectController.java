package cn.mystic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cn.mystic.annotation.CurrentUser;
import cn.mystic.domain.Project;
import cn.mystic.domain.SysUser;
import cn.mystic.service.ProjectService;
import cn.mystic.utils.OutputUtil;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	public void findAll(HttpServletRequest request, HttpServletResponse response) {
		JSONObject res = projectService.findAll();
		OutputUtil.print(response, res);
	}
	
	@RequestMapping(value = "/findIng", method = RequestMethod.POST)
	public void findIng(HttpServletRequest request, HttpServletResponse response) {
		JSONObject res = projectService.findIng();
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/pageAll", method = RequestMethod.POST)
	public void pageAll(HttpServletRequest request, HttpServletResponse response, String searchPhrase, String current, String rowCount) {
		JSONObject res = projectService.pageAll(searchPhrase, current, rowCount);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response, String project,
			@CurrentUser SysUser currentUser) {
		JSONObject jsonObject = JSONObject.parseObject(project);
		Project bean = jsonObject.toJavaObject(Project.class);
		JSONObject res = projectService.add(bean, currentUser);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, String project,
			@CurrentUser SysUser currentUser) {
		JSONObject jsonObject = JSONObject.parseObject(project);
		Project bean = jsonObject.toJavaObject(Project.class);
		JSONObject res = projectService.update(bean, currentUser);
		OutputUtil.print(response, res);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response, String id) {
		Integer userId = Integer.parseInt(id);
		JSONObject res = projectService.delete(userId);
		OutputUtil.print(response, res);
	}
	

	@RequestMapping(value = "/progress", method = RequestMethod.POST)
	public void progress(HttpServletRequest request, HttpServletResponse response, String id) {
		Integer projectId = Integer.parseInt(id);
		JSONObject res = projectService.progress(projectId);
		OutputUtil.print(response, res);
	}
	
}
