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

    private String projectName; // 任务名称

    private String createUser; // 创建者（经理）

    @OneToMany
    private List<SysUser> users; // 团队人员

    private Date createTime; // 创建日期

    private String projectState; // 项目状态 0.未开始 1.进行中 2.已完成
    
    private Date overTime; // 完成日期
    
    private Date updateTime; // 修改日期

    public Project() {}
}
