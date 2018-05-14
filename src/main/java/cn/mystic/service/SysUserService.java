package cn.mystic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mystic.dao.LoggerRepository;
import cn.mystic.dao.SysUserRepository;
import cn.mystic.domain.Logger;
import cn.mystic.domain.SysUser;
import cn.mystic.utils.LogUtil;

/**
 * Created by lihao on 2018/1/26.
 */
@Service
public class SysUserService {

	@Autowired
	private SysUserRepository userRepository;
	@Autowired
	private LoggerRepository loggerRepository;
	
	/**
	 * 分页获取所有用户
	 * 
	 * @return
	 */
	public JSONObject pageAll(SysUser user, String searchPhrase, String current, String rowCount) {
		JSONObject result = new JSONObject();
		try {
			List<SysUser> users = new ArrayList<SysUser>();
			int pageNumber = Integer.valueOf(current);
			int pageSize = Integer.valueOf(rowCount);
			int first = (pageNumber - 1) * pageSize;
			List<SysUser> userList = userRepository.findAll();
			if ("-1".equals(rowCount)) {
				users = userRepository.findAllList(searchPhrase);
			} else {
				users = userRepository.findUsers(searchPhrase, first, pageSize);
			}
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
			result.put("data", "获取用户信息失败");
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
			List<SysUser> userList = userRepository.findAllStaff();
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
	@Transactional
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
	@Transactional
	public JSONObject update(SysUser user, String username) {
		JSONObject result = new JSONObject();
		SysUser checkUser = userRepository.findByUsername(username);
		if (checkUser != null) {
			try {
				user.setId(checkUser.getId());
				user.setCreateTime(checkUser.getCreateTime());
				userRepository.save(user);
				result.put("code", 1);
				result.put("data", "用户修改成功");
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				result.put("code", 0);
				result.put("data", "发生异常，修改失败");
				return result;
			}
		} else {
			result.put("code", 0);
			result.put("data", "用户不存在");
			return result;
		}
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @return
	 */
	@Transactional
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
	@Transactional
	public JSONObject delete(Integer userId) {
		JSONObject result = new JSONObject();
		try {
			userRepository.delete(userId);
			result.put("code", 1);
			result.put("data", "用户删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			result.put("data", "发生异常，删除失败");
		}

		return result;
	}
	
	public void exit(SysUser sysUser) {
		Logger logger = LogUtil.getLogger(sysUser, "退出系统");
		loggerRepository.save(logger);
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
					Logger logger = LogUtil.getLogger(loginUser, "登录系统");
					loggerRepository.save(logger);
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
