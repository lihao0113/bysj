package cn.mystic.service;

import cn.mystic.dao.SysUserRepository;
import cn.mystic.domain.SysUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
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
            result.put("data", userList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 0);
            return result;
        }
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
