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

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String createTime;

    @Column
    private String role;

    public SysUser() {}
}
