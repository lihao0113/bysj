package cn.mystic.service;

import cn.mystic.dao.SysUserRepository;
import cn.mystic.domain.SysUser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao on 2018/1/26.
 */
@Service
public class SysUserService {

	@Autowired
	private SysUserRepository userRepository;
	
	/**
	 * 分页获取所有用户
	 * 
	 * @return
	 */
	public JSONObject pageAll(SysUser user, String searchPhrase, String current, String rowCount) {
		JSONObject result = new JSONObject();
		try {
			int pageNumber = Integer.valueOf(current);
			int pageSize = Integer.valueOf(rowCount);
			int first = (pageNumber - 1) * pageSize;
			int last = pageNumber * pageSize;
			List<SysUser> userList = userRepository.findAll();
			List<SysUser> users = userRepository.findUsers(searchPhrase, first, last);
			JSONArray array = new JSONArray();
			for (SysUser item : users) {
				array.add(item);
			}
			int total = userList.size();
			result.put("current", current);
			result.put("rowCount", rowCount);
			result.put("rows", array);
			result.put("total", total);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 获取一个用户
	 * 
	 * @return
	 */
	public JSONObject findOne(String id) {
		JSONObject result = new JSONObject();
		try {
			Integer userId = Integer.valueOf(id);
			SysUser sysUser = userRepository.findOne(userId);
			result.put("code", 1);
			result.put("data", sysUser);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}
	
	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 */
	public JSONObject findMyself(SysUser currentUser) {
		JSONObject result = new JSONObject();
		try {
			result.put("code", 1);
			result.put("data", currentUser);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public JSONObject findAll() {
		JSONObject result = new JSONObject();
		try {
			List<SysUser> userList = userRepository.findAll();
			result.put("code", 1);
			result.put("data", userList);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	public JSONObject add(SysUser user) {
		JSONObject result = new JSONObject();
		user.setCreateTime(new Date());
		SysUser checkUser = userRepository.findByUsername(user.getUsername());
		if (checkUser == null) {
			try {
				userRepository.save(user);
				result.put("code", 1);
				result.put("data", "用户添加成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("code", 0);
				result.put("data", "发生异常，添加失败");
				return result;
			}
		} else {
			result.put("code", 0);
			result.put("data", "用户名已存在");
		}

		return result;
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	public JSONObject update(SysUser user) {
		JSONObject result = new JSONObject();
		SysUser checkUser = userRepository.findByUsername(user.getUsername());
		if (checkUser != null) {
			try {
				userRepository.save(user);
				result.put("data", "用户修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", "发生异常，修改失败");
				return result;
			}
		} else {
			result.put("data", "用户不存在");
		}

		return result;
	}
	
	/**
	 * 修改用户
	 * 
	 * @return
	 */
	public JSONObject updatePass(SysUser currentUser, String oldPassword, String newPassword) {
		JSONObject result = new JSONObject();
		SysUser checkUser = userRepository.findByUsername(currentUser.getUsername());
		if (checkUser != null) {
			try {
				if (checkUser.getPassword().equals(oldPassword)) {
					checkUser.setPassword(newPassword);
					userRepository.save(currentUser);
					result.put("code", 1);
					result.put("data", "密码修改成功");
				} else {
					result.put("code", 0);
					result.put("data", "原密码输入错误");
				}		
			} catch (Exception e) {
				e.printStackTrace();
				result.put("code", 0);
				result.put("data", "发生异常，修改失败");
				return result;
			}
		} else {
			result.put("code", 0);
			result.put("data", "用户不存在");
		}
		return result;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	public JSONObject delete(Integer userId) {
		JSONObject result = new JSONObject();
		try {
			userRepository.delete(userId);
			result.put("data", "用户删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", "发生异常，删除失败");
		}

		return result;
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public JSONObject signin(String username, String password) {
		JSONObject result = new JSONObject();
		try {
			SysUser loginUser = userRepository.findByUsername(username);
			if (loginUser != null) {
				if (loginUser.getPassword().equals(password)) {
					result.put("code", 1);
					result.put("info", "登录成功");
					result.put("data", loginUser);
				} else {
					result.put("code", 0);
					result.put("info", "用户名或密码错误");
				}
			} else {
				result.put("code", 0);
				result.put("info", "用户不存在");
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}
}
