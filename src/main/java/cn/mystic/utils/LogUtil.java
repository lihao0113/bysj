package cn.mystic.utils;

import java.util.Date;

import cn.mystic.domain.Logger;
import cn.mystic.domain.Project;
import cn.mystic.domain.SysUser;
import cn.mystic.domain.Task;

public class LogUtil {

	public static Logger getLogger(SysUser user, String info){
		Logger logger = new Logger();
		StringBuffer describtion = new StringBuffer();
		
		describtion.append(user.getUsername() + " ");
		describtion.append(info + " ");

		logger.setTime(new Date());
		logger.setDescribtion(describtion.toString());
		return logger;
	}
	
	public static Logger getLogger(Project project, Task task, String info){
		Logger logger = new Logger();
		StringBuffer describtion = new StringBuffer();
		if (project != null) {
			describtion.append(project.getCreateUser() + "");
			describtion.append(info + " ");
			describtion.append(project.getProjectName());
			logger.setTime(project.getCreateTime());
		}
		if (task != null) {
			describtion.append(task.getFinishName() + "");
			describtion.append(info + " ");
			describtion.append(task.getTaskName());
			logger.setTime(task.getCreateTime());
		}

		logger.setDescribtion(describtion.toString());
		return logger;
	}
}
