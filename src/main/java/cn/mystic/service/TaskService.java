package cn.mystic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mystic.dao.LoggerRepository;
import cn.mystic.dao.ProjectRepository;
import cn.mystic.dao.TaskRepository;
import cn.mystic.domain.Logger;
import cn.mystic.domain.Project;
import cn.mystic.domain.SysUser;
import cn.mystic.domain.Task;
import cn.mystic.utils.LogUtil;
import cn.mystic.utils.state.TaskState;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private LoggerRepository loggerRepository;

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
			List<Task> taskList = taskRepository.findByProjectName(projectName);
			if ("-1".equals(rowCount)) {
				tasks = taskRepository.findAllList(searchPhrase, projectName);
			} else {
				tasks = taskRepository.findTasks(searchPhrase, projectName, first, pageSize);
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

	/**
	 * 添加任务
	 * 
	 * @return
	 */
	public JSONObject add(Task task, SysUser currentUser, String projectName) {
		JSONObject result = new JSONObject();
		Date now = new Date();
		try {
			String taskName = task.getTaskName();
			Task isExist = taskRepository.findByTaskNameAndProjectName(taskName, projectName);
			if (isExist != null) {
				result.put("code", 0);
				result.put("data", "该任务已存在");
				return result;
			}
			Project project = projectRepository.findByProjectName(projectName);
			task.setProjectName(projectName);
			task.setProject(project);
			task.setCreateTime(now);
			task.setTaskState(TaskState.NOTSTARTED.toString());
			taskRepository.save(task);
			result.put("code", 1);
			result.put("data", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("data", "系统错误，添加失败");
		}
		return result;
	}

	/**
	 * 修改任务
	 * 
	 * @return
	 */
	public JSONObject update(Task task, SysUser currentUser) {
		JSONObject result = new JSONObject();
		try {
			Task isExist = taskRepository.findOne(task.getId());
			if (isExist != null) {
				isExist.setTaskName(task.getTaskName());
				isExist.setRemark(task.getRemark());
				taskRepository.save(isExist);
			}
			result.put("code", 1);
			result.put("info", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("info", "系统错误，修改失败");
		}
		return result;
	}

	/**
	 * 修改任务指派
	 * 
	 * @return
	 */
	public JSONObject updateZhipai(Task task, SysUser currentUser) {
		JSONObject result = new JSONObject();
		try {
			Task isExist = taskRepository.findOne(task.getId());
			if (isExist != null) {
				isExist.setAssignName(task.getAssignName());
				isExist.setRemark(task.getRemark());
				taskRepository.save(isExist);
				result.put("code", 1);
				result.put("info", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("info", "系统错误，修改失败");
		}
		return result;
	}

	/**
	 * 删除任务
	 * 
	 * @return
	 */
	public JSONObject delete(Integer taskId) {
		JSONObject result = new JSONObject();
		try {
			Task task = taskRepository.findOne(taskId);
			if (task != null) {
				task.setProject(null);
				taskRepository.delete(task);
			}
			Logger logger = LogUtil.getLogger(null, task, "删除了任务");
			loggerRepository.save(logger);
			result.put("code", 1);
			result.put("info", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("info", "系统错误，删除失败");
		}
		return result;
	}

	/**
	 * 开始任务
	 * 
	 * @return
	 */
	public JSONObject start(Integer taskId) {
		JSONObject result = new JSONObject();
		try {
			Task task = taskRepository.findOne(taskId);
			Project project = projectRepository.findByProjectName(task.getProjectName());
			if (task != null) {
				if (task.getTaskState().equals(TaskState.FINISH.toString())) {
					result.put("code", 0);
					result.put("info", "任务已完成，不能开始");
					return result;
				} else {
					project.setProjectState(TaskState.STARTING.toString());
					projectRepository.save(project);
					task.setTaskState(TaskState.STARTING.toString());
					taskRepository.save(task);
					result.put("code", 1);
					result.put("info", "任务已开始");
				}
			}
			result.put("code", 1);
			result.put("info", "任务已开始");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("info", "系统错误，开始失败");
		}
		return result;
	}

	/**
	 * 完成任务
	 * 
	 * @return
	 */
	public JSONObject finshed(Integer taskId, String username) {
		JSONObject result = new JSONObject();
		try {
			Task task = taskRepository.findOne(taskId);
			Project project = projectRepository.findByProjectName(task.getProjectName());
			if (task.getTaskState().equals(TaskState.FINISH.toString())) {
				result.put("code", 0);
				result.put("info", "任务已完成");	
				return result;
			} else {
				Logger logger = LogUtil.getLogger(null, task, "完成了任务");
				loggerRepository.save(logger);
				project.setProjectState(TaskState.STARTING.toString());
				projectRepository.save(project);
				task.setFinishName(username);
				task.setTaskState(TaskState.FINISH.toString());
				taskRepository.save(task);
				result.put("code", 1);
				result.put("info", "任务已完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("info", "系统错误，完成失败");
		}
		return result;
	}

	/**
	 * 获取一个项目
	 * 
	 * @return
	 */
	public JSONObject findOne(String id) {
		JSONObject result = new JSONObject();
		try {
			Task task = taskRepository.findOne(Integer.valueOf(id));
			result.put("code", 1);
			result.put("data", task);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}

}
