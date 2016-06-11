package com.sds.medialib.model;

import java.util.Date;

/**
 * 媒体文件模型对象
 * @author sds
 *
 */
public class MediaFile {
	public MediaFile(String id,String name,String type,String url){
		this.id = id;
		this.name = name;
		this.type = type;
		this.url = url;
	}
	
	//编号
	private String id;
	//文件名称
	private String name;
	//类型
	private String type;
	//文件url
	private String url;
	
	//分类  
	private String catalog;
	
	//文件创建时间
	private Date createTime;
	
	//文件上传时间
	private Date uploadTime;
	
	//文件上传人
	private String uploader;
	
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	private Dir dir;
	
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public Dir getDir() {
		return dir;
	}
	public void setDir(Dir dir) {
		this.dir = dir;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}
