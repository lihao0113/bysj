package cn.mystic.domain;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
/**
 * Created by lihao on 2018/1/26.
 */
@Data
@Entity
public class SysUser {

    @Id
    private String id;

    private String username;

    private String password;

    private String createTime;

    private Integer role; // 用户权限 0.管理员 1.经理 2.员工

    public SysUser() {}

}
