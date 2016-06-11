package com.sds.core.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sds.core.SystemConfig;


/**
 * @author tantyou
 */
public class WebUtils {

	private WebUtils() {
	}
	
	/**
	 *  向客户端输出json字符串
	 * @param response
	 * @param json
	 */
	public static void outputJson(HttpServletResponse response,String json){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重载Spring WebUtils中的函数,作用如函数名所示 加入泛型转换,改变输入参数为request 而不是session
	 *
	 * @param name  session中变量名称
	 * @param clazz session中变量的类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrCreateSessionAttribute(HttpServletRequest request, String name, Class<T> clazz) {
		return (T) org.springframework.web.util.WebUtils.getOrCreateSessionAttribute(request.getSession(), name, clazz);
	}
	
	/**
	 * 取得request参数值,根据是否需要转码配置进行相应的转码工作
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getRequestParam(HttpServletRequest request,String name){
		String method = request.getMethod().trim().toLowerCase();
		String ret = request.getParameter(name);
		if(StringUtil.isNotEmpty(ret) && method.equals("get")){
			String encode = SystemConfig.getProperty("JTEAP_SYSTEM_REQUEST_ENCODE");
			if(StringUtil.isNotEmpty(encode) && encode.toLowerCase().equals("true")){
				String charset = SystemConfig.getProperty("JTEAP_SYSTEM_REQUEST_ENCODE_CHARSET");
				try {
					if (ret != null && !ret.equals("")) {
						byte[] byteStr = ret.getBytes("ISO-8859-1");
						ret = new String(byteStr,charset);
					}
				} catch (Exception e) {
				}
				
			}
		}
		return ret;
	}
	
	/**
	 * 得到request的queryString
	 * @param request
	 * @return
	 */
	public static String getQueryString(HttpServletRequest request){
		String ret = request.getQueryString();
		if(StringUtil.isNotEmpty(ret)){
			String encode = SystemConfig.getProperty("JTEAP_SYSTEM_REQUEST_ENCODE");
			if(StringUtil.isNotEmpty(encode) && encode.toLowerCase().equals("true")){
				String charset = SystemConfig.getProperty("JTEAP_SYSTEM_REQUEST_ENCODE_CHARSET");
				try {
					if (ret != null && !ret.equals("")) {
						byte[] byteStr = ret.getBytes("ISO-8859-1");
						ret = new String(byteStr,charset);
					}
				} catch (Exception e) {
				}
				
			}
		}
		return ret;
	}
}
