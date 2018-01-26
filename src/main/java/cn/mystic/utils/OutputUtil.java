package cn.mystic.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
public class OutputUtil {

	public static void print(HttpServletResponse res, Object obj) {
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			out.print(obj);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
}
