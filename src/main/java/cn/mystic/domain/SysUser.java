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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
}
