package cn.mystic.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by lihao on 2018/2/1.
 */

/**
 * 动态日志表
 */
@Data
@Entity
public class Logger {

    @Id
    @GeneratedValue
    private Integer id;

    private String describtion; // 日志描述

    private Date createTime; // 创建时间

    public Logger() {
    }
}
