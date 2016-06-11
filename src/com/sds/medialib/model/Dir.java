package com.sds.medialib.model;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sds.core.util.TreeNode;

/**
 * 目录层级对象
 * 实现TreeNode接口便于序列化为适合树组件读取的json格式的数据
 * @author sds
 *
 */
@SuppressWarnings("rawtypes")
public class Dir implements TreeNode {
	public Dir(String id,String name){
		this.id = id;
		this.name = name;
	}
	private String id;
	private String name;

	private Collection<Dir> children = null;
	

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildren(Collection<Dir> children) {
		this.children = children;
	}

	@Override
	public String get$ref() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Collection getChildren() {
		return this.children;
	}
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean getExpanded(){
		return true;
	}
	
	public boolean getIsExpanded(){
		return true;
	}

}
