package cn.mystic.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

    private String taskName; // 任务名称
    
    private String remark; // 任务描述

    private Date createTime; // 创建日期

    private Date expriyTime;// 截止日期

    private String taskState; // 任务状态 0.未开始 1.进行中 2.已完成

    private String assignName; // 指派人姓名

    private String finishName; // 完成人姓名

    public Task() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getExpriyTime() {
        return expriyTime;
    }

    public void setExpriyTime(Date expriyTime) {
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
