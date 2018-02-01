package cn.mystic.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lihao on 2018/1/31.
 */
@Service
public class FileService {

    public String fileUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return "false";
        }
        String fileName = file.getOriginalFilename();

        String path = System.getProperty("user.dir") + "/uploadFile";
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "上传失败";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    public JSONObject getFileList() {
        JSONObject result = new JSONObject();
        ArrayList<String> filelist = new ArrayList<String>();
        String path = System.getProperty("user.dir") + "/uploadFile";
        filelist = getFiles(path);
        result.put("data", filelist);
        return result;
    }

    static ArrayList<String> getFiles(String filePath) {
        ArrayList<String> filelist = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
//            if (file.isDirectory()) { // 递归调用
//                getFiles(file.getAbsolutePath());
//                filelist.add(file.getName());
//                System.out.println("显示" + filePath + "下所有文件夹" + file.getName());
//            } else {
            filelist.add(file.getName());
            System.out.println("显示" + filePath + "下所有文件名" + file.getName());
//            }
        }
        return filelist;
    }
}
