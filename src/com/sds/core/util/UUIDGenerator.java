/*
 * 创建日期 2006-3-24
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.sds.core.util;

import java.util.UUID;



/**
 * @author 谭畅
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class UUIDGenerator {
	private static int count=0;
	public static String javaId(){
		count++;
		String resultId=""+System.currentTimeMillis()+""+count;
		if(count>=90){
			count=0;
		}
		return resultId;
	}
	
	
	/**
	 * 生成UUID 32位
	 * @return
	 */
	public static String uuid(){
		String id = UUID.randomUUID().toString();
		id = id.replaceAll("-", "");
		return id;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(uuid());
		}
	}
}
