package cn.mystic.service;

import cn.mystic.domain.SysUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * Created by lihao on 2018/1/31.
 */
@Service
public class FileService {
    public JSONObject upload(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        return result;
    }

}
