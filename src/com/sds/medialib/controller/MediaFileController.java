package com.sds.medialib.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.core.SystemConfig;
import com.sds.core.util.FileUtil;
import com.sds.core.util.Page;
import com.sds.core.util.StringUtil;
import com.sds.medialib.manager.MediaFileManager;

@Controller
@RequestMapping("/media")
public class MediaFileController {

	@Autowired
	private MediaFileManager mediaFileManager;
	
	/**
	 * 获取媒体文件列表，指定页号
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("/list/{pageNo}")
	public @ResponseBody
	Page getMediaFileList(@PathVariable int pageNo,@RequestParam(defaultValue="") String dirId) {
		Page page = null;
		if(StringUtil.isEmpty(dirId)){
			page = mediaFileManager.findMyFiles(pageNo, 100);
		}else{
			String[] idsplit = dirId.split(":");
			dirId = idsplit[idsplit.length-1];
			page = mediaFileManager.findMyFilesByDir(pageNo, 100, dirId);
		}
		return page;
	}
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void uploadFile2(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String fileName = new String(request.getHeader("X_FILENAME").getBytes("ISO-8859-1"), "UTF-8");
			String fn = fileName;
			File tmpPath = new File(SystemConfig.getProperty("medialib.tmpfilepath"));
			if(!tmpPath.exists()){
				tmpPath.mkdirs();
			}
			File file = new File(tmpPath,fn);
			FileUtil.writeToFile(file, request.getInputStream());
			
			//文件上传到服务器后，需要上传到LC服务器
			mediaFileManager.uploadMediaFileToLC(file);
			System.out.println("delete tmp file:"+file.getAbsolutePath());
			file.delete();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	

//	@RequestMapping(value = "/upload.single", method = RequestMethod.POST)
//	public @ResponseBody
//	String handleImport(
//			@RequestParam(value = "fileselect[]", required = false) MultipartFile file,
//			HttpServletResponse response) throws IOException {
//
//		if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
//			// System.out.println(file.getOriginalFilename());
//
//			System.out.println(file.getOriginalFilename());
//			System.out.println(file.getSize());
//			System.out.println(file.getContentType());
//		}
//
//		return null;
//
//	}

//	public void uploadFile(HttpServletRequest request, HttpServletResponse response){
//		System.out.println("进来了");
//		
//		
//		
//		Enumeration enums = request.getHeaderNames();
//		while(enums.hasMoreElements()){
//			String name = (String) enums.nextElement();
//			System.out.println(name+ ":" +request.getHeader(name));
//		}
//	    
//	    
////		System.out.println(multipartRequest.getClass().toString());
////		if (multipartRequest != null) {  
////            Iterator iterator = multipartRequest.getFileNames();  
////            while (iterator.hasNext()) {  
////            	MultipartFile multifile =  
////                        multipartRequest.getFile((String) iterator.next());  
////  
////                if (StringUtils.hasText(multifile.getOriginalFilename())) {  
//////                    System.out.println(multifile.getOriginalFilename());  
////                      
////                    System.out.println(multifile.getOriginalFilename());  
////                    System.out.println(multifile.getSize());  
//////                    String path = uploadService.saveFileToServer(multifile, uploadHome);  
//////                    fileModel.setPath(path);  
//////                    list.add(fileModel);  
////                }  
////            }  
////            
////        }  
//	}

	
}
