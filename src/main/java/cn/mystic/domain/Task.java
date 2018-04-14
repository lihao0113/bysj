package cn.mystic.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Date;

/**
 * Created by lihao on 2018/2/1.
 */

/**
 * 任务表
 */
@Data
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Project project; // 所属项目id
    
    private String projectName; // 所属项目名称

    private String taskName; // 任务名称
    
    private String remark; // 任务描述

    private Date createTime; // 创建日期

    private String expriyTime;// 截止日期

    private String taskState; // 任务状态 0.未开始 1.进行中 2.已完成

    private String assignName; // 指派人姓名

    private String finishName; // 完成人姓名

    public Task() {}
    
    public Task(Project project, String projectName, String taskName, String remark, Date createTime,
    		String expriyTime, String taskState, String assignName, String finishName) {
		super();
		this.project = project;
		this.projectName = projectName;
		this.taskName = taskName;
		this.remark = remark;
		this.createTime = createTime;
		this.expriyTime = expriyTime;
		this.taskState = taskState;
		this.assignName = assignName;
		this.finishName = finishName;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExpriyTime() {
        return expriyTime;
    }

    public void setExpriyTime(String expriyTime) {
        this.expriyTime = expriyTime;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public String getFinishName() {
        return finishName;
    }

    public void setFinishName(String finishName) {
        this.finishName = finishName;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
