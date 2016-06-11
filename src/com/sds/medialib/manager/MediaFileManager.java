package com.sds.medialib.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.namespace.QName;

import org.apache.abdera.i18n.text.UrlEncoding;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.parser.ParseException;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import com.sds.core.SystemConfig;
import com.sds.core.exceptions.LCFileUploadException;
import com.sds.core.util.CustomFileEntity;
import com.sds.core.util.FileTransformListener;
import com.sds.core.util.Page;
import com.sds.core.util.StringUtil;
import com.sds.medialib.model.MediaFile;
import com.sds.medialib.utils.LCHelper;

/**
 * 媒体文件管理器
 * @author T420
 *
 */
@Component
public class MediaFileManager {

	
	/**
	 * 文件上传方法之2
	 * @param file
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws LCFileUploadException 
	 */
	public void uploadMediaFileToLC2(File file) throws HttpException, IOException, LCFileUploadException{
		HttpClient client = new HttpClient();
		//测试用例中执行时需要设置证书,该证书为执行InstallCert类获取的
//		System.setProperty("javax.net.ssl.trustStore", "C:/jssecacerts");
		
		Credentials creds = new UsernamePasswordCredentials(SystemConfig.getProperty("lc.adminuser"),SystemConfig.getProperty("lc.adminpassword"));
		
		client.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM), creds);
		String nonce = LCHelper.getNonce();
		String feedUrl = SystemConfig.getProperty("lc.apiUrlPrefix")+"/myuserlibrary/feed?label="+UrlEncoding.encode(file.getName());
		PostMethod post = new PostMethod(feedUrl);

		post.setRequestHeader("X-Update-Nonce", nonce);
		post.setRequestHeader("Slug", file.getName()); // / 设置文件名
		String mimeType = new MimetypesFileTypeMap().getContentType(file);
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
		int status = client.executeMethod(post);
		System.out.println(status);
		if(status != 201){
			InputStream is = post.getResponseBodyAsStream();
			String string = StringUtil.getStringFromStream(is, "utf-8");
			throw new LCFileUploadException(status,string);
		}
		
		
	}
	
	
	/**
	 * 上传文件至LC服务器
	 * @param file
	 * @throws IOException 
	 * @throws LCFileUploadException 
	 * @throws FileNotFoundException 
	 * @throws ParseException 
	 */
	public void uploadMediaFileToLC(File file) throws LCFileUploadException, FileNotFoundException{
		AbderaClient client = LCHelper.getAbderaClient();
		String mimeType = new MimetypesFileTypeMap().getContentType(file);
		String fileName = file.getName();
		InputStream is = new FileInputStream(file);
		InputStreamRequestEntity isre = new InputStreamRequestEntity(is, mimeType);
		String nonce = LCHelper.getNonce();
		
		String feedUrl = SystemConfig.getProperty("lc.apiUrlPrefix")+"/myuserlibrary/feed?label="+UrlEncoding.encode(fileName)+"&X-Update-Nonce="+nonce;

		RequestOptions requestOptions = client.getDefaultRequestOptions();
		requestOptions.setContentType("application/zip");
		ClientResponse cr  = client.post(feedUrl, isre);
		System.out.println(cr.getStatus());
		if(cr.getStatus() == 409 || cr.getStatus() == 401 || cr.getStatus() == 403 || cr.getStatus() == 404 || cr.getStatus() == 415){
			throw new LCFileUploadException(cr.getStatus(),cr.getDocument().getRoot().toString());
		}
	}
	
	
	/**
	 * 获取我的文件列表
	 * @return
	 * @throws IOException
	 */
	public Page findMyFiles(int pageNo,int pageSize){
		Collection<MediaFile> result = new ArrayList<MediaFile>();
		AbderaClient client = LCHelper.getAbderaClient();
		RequestOptions options = client.getDefaultRequestOptions();
		// options.setIfModifiedSince(new java.util.Date());
		options.setNoCache(true);
		String uri = SystemConfig.getProperty("lc.apiUrlPrefix")+"/myuserlibrary/feed?page="+pageNo+"&pageSize="+pageSize;
		ClientResponse cr = client.get(uri,options);
		
		Document<Feed> document = cr.getDocument();
		Feed feed = document.getRoot();
		Element totalElement = feed.getExtension(new QName("http://a9.com/-/spec/opensearch/1.1/","totalResults","opensearch"));
		long total = Long.parseLong(totalElement.getText());
		Collection<Entry> entrys = feed.getEntries();
		for (Entry entry : entrys) {
			MediaFile mf = new MediaFile(entry.getId().toASCIIString(), entry.getTitle()
					, entry.getContentElement().getAttributeValue("type"), entry.getContentSrc().toASCIIString());
			result.add(mf);
		}
		Page page = new Page(pageNo,total,pageSize,result);
		cr.release();
		return page;
	}
	
	/**
	 * 根据目录导航获取我的文件夹中的图片文件
	 * @param pageNo
	 * @param pageSize
	 * @param dirId
	 * @return
	 */
	public Page findMyFilesByDir(int pageNo,int pageSize,String dirId){
		
		Collection<MediaFile> result = new ArrayList<MediaFile>();
		AbderaClient client = LCHelper.getAbderaClient();
		RequestOptions options = client.getDefaultRequestOptions();
		// options.setIfModifiedSince(new java.util.Date());
		options.setNoCache(true);
		String uri = SystemConfig.getProperty("lc.apiUrlPrefix")+"/collection/"+dirId+"/feed?page="+pageNo+"&pageSize="+pageSize;
		System.out.println(uri);
		ClientResponse cr = client.get(uri,options);
		
		Document<Feed> document = cr.getDocument();
	
		Feed feed = document.getRoot();
		Element totalElement = feed.getExtension(new QName("http://a9.com/-/spec/opensearch/1.1/","totalResults","opensearch"));
		long total = Long.parseLong(totalElement.getText());
		Collection<Entry> entrys = feed.getEntries();
		for (Entry entry : entrys) {
			MediaFile mf = new MediaFile(entry.getId().toASCIIString(), entry.getTitle()
					, entry.getContentElement().getAttributeValue("type"), entry.getContentSrc().toASCIIString());
			result.add(mf);
		}
		Page page = new Page(pageNo,total,pageSize,result);
		cr.release();
		return page;
	}
}
