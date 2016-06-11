package com.sds.controler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyControler {

	@RequestMapping("/admin/aaa")
	public void getXieTiaoList(HttpServletResponse response){
		try {
			response.getWriter().print("{a:'123bbbb333'}");
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
