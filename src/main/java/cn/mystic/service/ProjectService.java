package cn.mystic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private LoggerRepository loggerRepository;

	/**
	 * 获取所有项目
	 * 
	 * @return
	 */
	public JSONObject findAll() {
		JSONObject result = new JSONObject();
		try {
			List<Project> projects = projectRepository.findAll();
			result.put("code", 1);
			result.put("data", projects);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}

	/**
	 * 获取一个项目
	 * 
	 * @return
	 */
	public JSONObject findOne(String id) {
		JSONObject result = new JSONObject();
		try {
			Project project = projectRepository.findOne(Integer.valueOf(id));
			result.put("code", 1);
			result.put("data", project);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}

	/**
	 * 获取进行中的项目
	 * 
	 * @return
	 */
	public JSONObject findIng() {
		JSONObject result = new JSONObject();
		try {
			List<Project> projects = projectRepository.findProIng();
			result.put("code", 1);
			result.put("data", projects);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}

	/**
	 * 获取进行中的项目及进度
	 * 
	 * @return
	 */
	public JSONObject indexEcharts() {
		JSONObject result = new JSONObject();
		try {
			List<Project> projects = projectRepository.findProIng();
			JSONArray xArray = new JSONArray();
			JSONArray yArray = new JSONArray();
			if (projects != null) {
				for (Project project : projects) {
					xArray.add(project.getProjectName());
					int value = 0;
					int finished = 0;
					List<Task> tasks = taskRepository.findByProjectName(project.getProjectName());
					if (tasks.size() > 0) {
						for (int i = 0; i < tasks.size(); i++) {
							if ("2".equals(tasks.get(i).getTaskState())) {
								finished += 1;
							}
						}
						value = (finished * 100) / tasks.size();
					}
					yArray.add(value);
				}
			}
			result.put("xArr", xArray);
			result.put("yArr", yArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 分页获取所有项目
	 * 
	 * @return
	 */
	public JSONObject pageAll(String searchPhrase, String current, String rowCount) {
		JSONObject result = new JSONObject();
		try {
			List<Project> projects = new ArrayList<Project>();
			int pageNumber = Integer.valueOf(current);
			int pageSize = Integer.valueOf(rowCount);
			int first = (pageNumber - 1) * pageSize;
			List<Project> projectList = projectRepository.findAll();
			if ("-1".equals(rowCount)) {
				projects = projectRepository.findAllList(searchPhrase);
			} else {
				projects = projectRepository.findProjects(searchPhrase, first, pageSize);
			}
			JSONArray array = new JSONArray();
			for (Project item : projects) {
				array.add(item);
			}
			int total = projectList.size();
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
	 * 添加项目
	 * 
	 * @return
	 */
	public JSONObject add(Project project, SysUser currentUser) {
		JSONObject result = new JSONObject();
		Project isExist = projectRepository.findByProjectName(project.getProjectName());
		try {
			if ("2".equals(currentUser.getRole())) {
				result.put("code", 0);
				result.put("data", "权限不足，请联系管理员");
			} else {
				if (isExist != null) {
					result.put("code", 0);
					result.put("data", "项目已存在");
				} else {
					project.setCreateUser(currentUser.getUsername());
					project.setCreateTime(new Date());
					project.setProjectState(TaskState.NOTSTARTED.toString());
					projectRepository.save(project);
					Logger logger = LogUtil.getLogger(project, null, "新建了项目");
					loggerRepository.save(logger);
					result.put("code", 1);
					result.put("data", "项目添加成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("data", "发生异常");
			return result;
		}
		return result;
	}

	/**
	 * 修改项目
	 * 
	 * @return
	 */
	public JSONObject update(Project project, SysUser currentUser, String projectName) {
		JSONObject result = new JSONObject();
		Project checkProject = projectRepository.findByProjectName(projectName);
		try {
			if (checkProject == null) {
				result.put("code", 0);
				result.put("data", "项目不存在");
			} else {
				if ("2".equals(currentUser.getRole())) {
					result.put("code", 0);
					result.put("data", "权限不足，请联系管理员");
				} else {
					checkProject.setProjectName(project.getProjectName());
					checkProject.setRemark(project.getRemark());
					checkProject.setUpdateTime(new Date());
					projectRepository.save(checkProject);
					result.put("code", 1);
					result.put("data", "项目修改成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("data", "发生异常");
			return result;
		}
		return result;
	}

	/**
	 * 删除项目
	 * 
	 * @return
	 */
	public JSONObject delete(Integer projectId) {
		JSONObject result = new JSONObject();
		try {
			Project project = projectRepository.findOne(projectId);
			if (project != null) {
				List<Task> tasks = taskRepository.findByProjectName(project.getProjectName());
				if (tasks != null) {
					for (Task task : tasks) {
						task.setProject(null);
						taskRepository.delete(task);
					}
				}
//				Logger logger = LogUtil.getLogger(project, null, "删除了项目");
//				loggerRepository.save(logger);
				projectRepository.delete(project);
				result.put("code", 1);
				result.put("data", "项目删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("data", "发生异常，删除失败");
		}

		return result;
	}

	/**
	 * 获取完成进度百分比
	 * 
	 * @return
	 */
	public JSONObject progress(Integer projectId) {
		JSONObject result = new JSONObject();
		try {
			int value = 0;
			int finished = 0;
			Project project = projectRepository.findOne(projectId);
			List<Task> tasks = taskRepository.findByProjectName(project.getProjectName());
			if (tasks.size() > 0) {
				for (int i = 0; i < tasks.size(); i++) {
					if ("2".equals(tasks.get(i).getTaskState())) {
						finished += 1;
					}
				}
				value = (finished * 100) / tasks.size();
			}
			result.put("value", value);
		} catch (Exception e) {
			result.put("value", 0);
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 完成项目
	 * 
	 * @return
	 */
	public JSONObject finshed(Integer projectId) {
		JSONObject result = new JSONObject();
		try {
			Project project = projectRepository.findOne(projectId);
			List<Task> tasks = project.getTasks();
			if (tasks.size() > 0) {
				for (int i = 0; i < tasks.size(); i++) {
					if (!"2".equals(tasks.get(i).getTaskState())) {
						result.put("code", 0);
						result.put("data", "任务没有全部完成，项目不可结束！");
						return result;
					}
				}
			}
			Logger logger = LogUtil.getLogger(project, null, "完成了项目");
			loggerRepository.save(logger);
			project.setOverTime(new Date());
			project.setProjectState(TaskState.FINISH.toString());
			projectRepository.save(project);
			result.put("code", 1);
			result.put("data", "项目已完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
