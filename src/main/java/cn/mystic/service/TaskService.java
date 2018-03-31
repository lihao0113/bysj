package cn.mystic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mystic.dao.TaskRepository;
import cn.mystic.domain.Project;
import cn.mystic.domain.Task;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	/**
	 * 分页获取所有项目
	 * 
	 * @return
	 */
	public JSONObject pageAll(String projectName, String searchPhrase, String current, String rowCount) {
		JSONObject result = new JSONObject();
		try {
			List<Task> tasks = new ArrayList<Task>();
			int pageNumber = Integer.valueOf(current);
			int pageSize = Integer.valueOf(rowCount);
			int first = (pageNumber - 1) * pageSize;
			List<Task> taskList = taskRepository.findAll();
			if ("-1".equals(rowCount)) {
				tasks = taskRepository.findAllList(searchPhrase);
			} else {
				tasks = taskRepository.findProjects(searchPhrase, first, pageSize);
			}
			JSONArray array = new JSONArray();
			for (Task item : tasks) {
				array.add(item);
			}
			int total = taskList.size();
			result.put("current", current);
			result.put("rowCount", rowCount);
			result.put("rows", array);
			result.put("total", total);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}


}
