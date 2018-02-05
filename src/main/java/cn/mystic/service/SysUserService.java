package cn.mystic.service;

import cn.mystic.dao.SysUserRepository;
import cn.mystic.domain.SysUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lihao on 2018/1/26.
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserRepository userRepository;

    /**
     * 获取所有用户
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
     * 分页获取所有用户
     * @return
     */
    public JSONObject pageAll(int pageNumber, int pageSize) {
        JSONObject result = new JSONObject();
        try {
            //模糊查询操作
//            ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
//            Pageable pageable = new PageRequest(Example.of(user, exampleMatcher)pageNumber -1, pageSize, new Sort(Sort.Direction.DESC, "id"));
            Pageable pageable = new PageRequest(pageNumber -1, pageSize, new Sort(Sort.Direction.DESC, "id"));
            Page<SysUser> users = userRepository.findAll(pageable);
            result.put("code", 1);
            result.put("data", users);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 0);
            return result;
        }
    }
    
    /**
     * 获取所有用户
     * @return
     */
    public JSONObject add(SysUser user) {
        JSONObject result = new JSONObject();
        user.setCreateTime(new Date());
        SysUser checkUser = userRepository.findByUsername(user.getUsername());
        if (checkUser == null) {
        	try {
        		user.setUsername("hhh");
        		user.setPassword("111");
        		userRepository.save(user);
        		result.put("data", "用户添加成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", "发生异常");
				return result;
			}
		} else {
			result.put("data", "用户名已存在");
		}
        
       return result;
    }


    /**
     * 登录
     * @return
     */
    public JSONObject signin(String username, String password) {
        JSONObject result = new JSONObject();
        try {
            SysUser loginUser = userRepository.findByUsername(username);
            if (loginUser != null){
                if (loginUser.getPassword().equals(password)){
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
