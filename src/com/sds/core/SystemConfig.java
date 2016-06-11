package com.sds.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.sds.core.exceptions.ConfigFileNotExistException;
import com.sds.core.util.PropertiesUtil;
import com.sds.core.util.StringUtil;

/**
 * 读取系统配置的工具方法
 * @author tanchang
 *
 */
public class SystemConfig {
	private static PropertiesUtil pp;
	static{
		URL url = SystemConfig.class.getClassLoader().getResource(Constants.DEFAULT_CONFIG_FILE);
		String file = url.getFile();
		file = file.replaceAll("%20", " ");
		pp = new PropertiesUtil(file,Constants.DEFAULT_CONFIG_FILE_ENCODING);
	}
	
	/**
	 *描述：重载全局配置
	 *时间：2010-1-11
	 *作者：谭畅
	 *参数：无
	 *返回值:无
	 *抛出异常：无
	 */
	public static void reload(){
		URL url = SystemConfig.class.getClassLoader().getResource("config.properties");
		String file = url.getFile();
		file = file.replaceAll("%20", " ");
		pp = new PropertiesUtil(file,Constants.DEFAULT_CONFIG_FILE_ENCODING);
	}
	

	
	/**
	 *描述：取得配置文件中的所有配置项，并以MAP形式返回
	 *时间：2010-1-11
	 *作者：谭畅
	 *参数：无
	 *返回值:无
	 *抛出异常：
	 */
	public static Map<String,String> getAllPropertiesInMap(){
		Map<String,String> resultMap = new HashMap<String,String>();
		for (Object key : pp) {
			String sKey = key.toString();
			resultMap.put(sKey, pp.getProperties(sKey));
		}
		return resultMap;
	}
	
	/**
	 * 
	 *描述：设置并保存Map中指定的配置信息
	 *时间：2010-1-12
	 *作者：谭畅
	 *参数：
	 *	@param propMap 参数键值对集合
	 *返回值:
	 *	无
	 *抛出异常：
	 * @throws Exception 
	 */
	public static void setAndSaveProperties(Map<String,String> propMap) throws Exception{
		URL url = SystemConfig.class.getClassLoader().getResource(Constants.DEFAULT_CONFIG_FILE);
		if(url==null)
			throw new ConfigFileNotExistException();
		String file = url.getFile();
		file = file.replaceAll("%20", " ");
		PropertiesUtil pu = new PropertiesUtil(new File(file), Constants.DEFAULT_CONFIG_FILE_ENCODING);
		for (String key : propMap.keySet()) {
			pu.setProperties(key, propMap.get(key));
		}
		pu.save();
		reload();
	}

	public static void setAndSaveProperties(String propertName, String propertValue) throws Exception{
		URL url = SystemConfig.class.getClassLoader().getResource("config.properties");
		if(url==null)
			throw new ConfigFileNotExistException();
		PropertiesUtil pu = new PropertiesUtil(new File(url.getFile()),Constants.DEFAULT_CONFIG_FILE_ENCODING);
		pu.setProperties(propertName, propertValue);
		pu.save();
		reload();
	}
	
	
	/**
	 * 
	 *描述：取得指定属性的值
	 *时间：2010-1-12
	 *作者：谭畅
	 *参数：
	 *	@param name 参数名称
	 *返回值:
	 *	@return 返回对应属性的值
	 *抛出异常：
	 */
	public static String getProperty(String name){
		return getProperty(name, null);
	}
	
	/**
	 * 
	 *描述：取得指定属性的值,如果没有配置该值，则给出一个默认的配置值
	 *时间：2010-1-12
	 *作者：谭畅
	 *参数：
	 *	@param name 参数名称
	 *返回值:
	 *	@return 返回对应属性的值
	 *抛出异常：
	 */
	public static String getProperty(String name,String defaultValue){
		String value = pp.getProperties(name);
		if(StringUtil.isNotEmpty(value)){
			value = value.trim();
		}else{
			value = defaultValue;
		}
		return value;
	}
	/**
	 * 设置配置
	 * @param name
	 * @param value
	 */
	public static void setProperty(String name,String value){
		pp.setProperties(name, value);
	}
	
	/**
	 * 保存配置文件
	 * @throws IOException
	 */
	public static void save() throws IOException{
		pp.save();
	}
	
	public static void main(String[] args) throws Exception {
		//Map map = new HashMap<String, String>();
		//map.put("aa", "bb");
		//SystemConfig.setAndSaveProperties(map);
//		String xx = SystemConfig.getProperty("JTEAP_SYSTEM_POPUP_WINDOW");
		
//		System.out.println(xx);
		
		
	}
}
