package cn.mystic.domain;

import lombok.Data;

import javax.persistence.*;

import cn.mystic.utils.state.TaskState;

import java.util.Date;
import java.util.List;

/**
 * Created by lihao on 2018/2/1.
 */

/**
 * 项目表
 */
@Data
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Integer id;

    private String projectName; // 项目名称

    private String createUser; // 创建者（经理）
    
    private String remark; // 备注

    @OneToMany
    private List<Task> tasks; // 任务

    private Date createTime; // 创建日期

    private String projectState; // 项目状态 0.未开始 1.进行中 2.已完成
    
    private Date overTime; // 完成日期
    
    private Date updateTime; // 修改日期

    public Project() {}

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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
