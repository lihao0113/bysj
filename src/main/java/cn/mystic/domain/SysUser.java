package cn.mystic.domain;

import lombok.Data;

import java.util.Date;

import javax.persistence.*;
/**
 * Created by lihao on 2018/1/26.
 */
@Data
@Entity
public class SysUser {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    private Date createTime;

    private String role; // 用户权限 0.管理员 1.经理 2.员工

    public SysUser() {}

}
