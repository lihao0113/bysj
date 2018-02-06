package cn.mystic.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private Integer projectId; // 所属项目id

    private String taskName; // 任务名称

    private Date createTime; // 创建日期

    private Date expriyTime;// 截止日期

    private String taskState; // 任务状态 0.未开始 1.进行中 2.已完成

    private String assignName; // 指派人姓名

    private String finishName; // 完成人姓名

    public Task() {}

}
