package com.sds.medialib.manager.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collection;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.abdera.i18n.text.UrlEncoding;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sds.core.exceptions.LCFileUploadException;
import com.sds.core.util.CustomFileEntity;
import com.sds.core.util.FileTransformListener;
import com.sds.core.util.Page;
import com.sds.core.util.StringUtil;
import com.sds.medialib.manager.MediaFileManager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
@SuppressWarnings("rawtypes")
public class MediaFileManagerTest {
	
	@Resource
	private MediaFileManager mediaFileManager;
	
	@Test
	public void testFindMyFile(){
		Page page = mediaFileManager.findMyFiles(1,2);
		Assert.assertNotNull(page.getResult());
		Assert.assertTrue(((Collection)page.getResult()).size()>0);
	}

	@Test
	public void testFindMyFileByDir(){
		Page page = mediaFileManager.findMyFilesByDir(1, 3, "643b7029-0b2d-43f8-bc7b-271589b847ec");
		Assert.assertNotNull(page.getResult());
		Assert.assertTrue(((Collection)page.getResult()).size()==3);
	}
	
	@Test
	public void testUploadFile(){
			File file = new File("D:\\tail-for-windows.zip");
			try {
				mediaFileManager.uploadMediaFileToLC(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (LCFileUploadException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testUploadFileForIBMSuppoert() throws HttpException, IOException, LCFileUploadException{
		File file= new File("d:/sds/我百天.JPG");
		String mimeType = new MimetypesFileTypeMap().getContentType(file);
//		Abdera abdera = new Abdera();
		HttpClient client = new HttpClient();
		System.setProperty("javax.net.ssl.trustStore", "C:/jssecacerts");
		GetMethod get = new GetMethod("https://rhel58.sds.com:9444/files/basic/api/nonce");
		Credentials creds = new UsernamePasswordCredentials("wasadmin","password");
		client.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM), creds);
		int status = client.executeMethod(get);
		String nonce = get.getResponseBodyAsString(); // 获得nonce

		System.out.println(nonce);

		PostMethod post = new PostMethod("https://rhel58.sds.com:9444/files/basic/api/myuserlibrary/feed?label="+UrlEncoding.encode("中文"));

		post.setRequestHeader("X-Update-Nonce", nonce);
		post.setRequestHeader("Slug", file.getName()); // / 设置文件名
		post.setRequestHeader("Label", file.getName()); // / 设置文件名
		
//		FileInputStream fis = new FileInputStream(file);
		final long total = file.length();
		System.out.println(total);
		post.setRequestEntity(new CustomFileEntity(file, mimeType,new FileTransformListener() {
			
			@Override
			public void transformed(long transformed) {
				float percent = ((float)transformed/(float)total);
				NumberFormat nf = NumberFormat.getPercentInstance();
				nf.setMinimumFractionDigits(3);
				System.out.println(nf.format(percent));
			}
		}));
		status = client.executeMethod(post);
		String s = StringUtil.getStringFromStream(post.getResponseBodyAsStream(),"utf-8");
		System.out.println(s);
		if(status == 409 || status == 401 || status == 403 || status == 404 || status == 415){
			throw new LCFileUploadException(status,"失败");
		}		
	}
	

}

