package com.sds.medialib.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.core.util.JSONUtil;
import com.sds.core.util.WebUtils;
import com.sds.medialib.manager.DirManager;
import com.sds.medialib.model.Dir;


@Controller
public class DirController {
	
	@Autowired
	private DirManager dirManager;
	
	
	public DirManager getDirManager() {
		return dirManager;
	}


	public void setDirManager(DirManager dirManager) {
		this.dirManager = dirManager;
	}


	/**
	 * 获取组织结构根节点JSON串
	 * @return
	 */

	@RequestMapping(value="/dir/dirtree/rootNodes",method=RequestMethod.GET)
	public void getMyDirRootAction(@RequestParam(defaultValue="") String parentId,HttpServletResponse response){
		Collection<Dir> list = dirManager.findDirByParentId(null);
		String json = JSONUtil.listToJson(list, new String[]{"id","isExpanded","expanded","name","children","$ref"});
		WebUtils.outputJson(response, json);
	}
	
	/**
	 * 根据父亲节点ID获取目录集合
	 * @param parentId
	 * @param response
	 */
	@RequestMapping(value="/dir/dirtree/{parentId}",method=RequestMethod.GET)
	public void getMyDirByParentAction(@PathVariable String parentId,HttpServletResponse response){
		Collection<Dir> list = dirManager.findDirByParentId(parentId);
		String json = JSONUtil.listToJson(list, new String[]{"id","name","children","$ref","isExpanded","expanded"});
		WebUtils.outputJson(response, json);
	}
	

}
