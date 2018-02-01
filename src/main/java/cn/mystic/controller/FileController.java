package cn.mystic.controller;

import cn.mystic.service.FileService;
import cn.mystic.utils.OutputUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by lihao on 2018/1/31.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 实现文件上传
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(MultipartFile file) {
        return fileService.fileUpload(file);
    }

    @RequestMapping(value = "/fileList", method = RequestMethod.POST)
    public void findAll(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = fileService.getFileList();
        OutputUtil.print(response,res);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(HttpServletRequest request, HttpServletResponse response, String filename) {
        return fileService.fileDownload(response, filename);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, String filename) {
        JSONObject res = fileService.deleteFile(filename);
        OutputUtil.print(response,res);
    }
}
