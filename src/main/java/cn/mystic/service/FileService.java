package cn.mystic.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by lihao on 2018/1/31.
 */
@Service
public class FileService {

	public String fileUpload(MultipartFile file) {
		if (file == null) {
			return "请选择文件";
		}
		String fileName = file.getOriginalFilename();

		String path = System.getProperty("user.dir") + "/uploadFile";
		File dest = new File(path + "/" + fileName);
		if (!dest.getParentFile().exists()) { // 判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		try {
			file.transferTo(dest); // 保存文件
			return "上传成功";
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "上传失败";
		} catch (IOException e) {
			e.printStackTrace();
			return "上传失败";
		}
	}

	public String fileDownload(HttpServletResponse response, String filename) {
		String filePath = System.getProperty("user.dir") + "/uploadFile";
		File file = new File(filePath + "/" + filename);
		if (file.exists()) { // 判断文件父目录是否存在
			response.reset();// 重置 响应头
			response.setContentType("application/octet-stream");// 告知浏览器下载文件，而不是直接打开，浏览器默认为打开
			// response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

			byte[] buffer = new byte[1024];
			FileInputStream fis = null; // 文件输入流
			BufferedInputStream bis = null;

			OutputStream os = null; // 输出流
			try {
				os = response.getOutputStream();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer);
					i = bis.read(buffer);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("----------file download" + filename);
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public JSONObject deleteFile(String filename) {
		JSONObject result = new JSONObject();
		String filePath = System.getProperty("user.dir") + "/uploadFile";
		File file = new File(filePath + "/" + filename);
		if (file.exists() && file.isFile()) {
			file.delete();
			result.put("code", 1);
			result.put("data", "删除成功");
		} else {
			result.put("code", 0);
			result.put("data", "删除失败");
		}
		return result;
	}

	public JSONObject getFileList() {
		JSONObject result = new JSONObject();
		ArrayList<String> filelist = new ArrayList<String>();
		String path = System.getProperty("user.dir") + "/uploadFile";
		filelist = getFiles(path);
		result.put("code", 1);
		result.put("data", filelist);
		return result;
	}

	public ArrayList<String> getFiles(String filePath) {
		ArrayList<String> filelist = new ArrayList<String>();
		File root = new File(filePath);
		File[] files = root.listFiles();
		if (files != null) {
			for (File file : files) {
				// if (file.isDirectory()) { // 递归调用
				// getFiles(file.getAbsolutePath());
				// filelist.add(file.getName());
				// System.out.println("显示" + filePath + "下所有文件夹" +
				// file.getName());
				// } else {
				filelist.add(file.getName());
				// }
			}

		}
		return filelist;
	}
}
