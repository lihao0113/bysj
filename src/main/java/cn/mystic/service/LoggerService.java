package cn.mystic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.mystic.dao.LoggerRepository;
import cn.mystic.domain.Logger;

@Service
public class LoggerService {
	
	@Autowired
	private LoggerRepository loggerRepository;
	
	/**
	 * 分页获取所有日志信息
	 * 
	 * @return
	 */
	public JSONObject pageAll(int pageNumber, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
			Page<Logger> loggers = loggerRepository.findAll(pageable);
			result.put("code", 1);
			result.put("data", loggers);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}
	
	/**
	 * 获取最近20条动态
	 * 
	 * @return
	 */
	public JSONObject findTop20() {
		JSONObject result = new JSONObject();
		try {
			List<Logger> loggers = loggerRepository.findTop20();
			result.put("code", 1);
			result.put("data", loggers);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 0);
			return result;
		}
	}

}
