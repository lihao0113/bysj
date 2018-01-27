package cn.mystic.domain;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
/**
 * Created by lihao on 2018/1/26.
 */
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
