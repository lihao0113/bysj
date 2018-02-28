package cn.mystic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.mystic.service.TaskService;

@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	

}
