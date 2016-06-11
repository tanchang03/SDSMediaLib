package com.sds.medialib.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Service;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.springframework.stereotype.Component;

import com.sds.core.SystemConfig;
import com.sds.medialib.model.Dir;
import com.sds.medialib.utils.LCHelper;
/**
 * 目录对象管理类
 * @author sds
 *
 */
@Component
public class DirManager {

	/**
	 * 获取目录集合对象
	 * 如果parentId为空，则获取顶层目录集合
	 * @param parentId
	 * @return
	 */
	public Collection<Dir> findDirByParentId(String parentId){ 
//		/javax.xml.transform.TransformerFactory
//		Collection<Dir> result = new ArrayList<Dir>();
//		if(StringUtil.isEmpty(parentId)){
//			Dir dir[] = new Dir[]{new Dir("00001","目录001"),new Dir("00002","目录002")
//					,new Dir("003","目录003")
//					,new Dir("004","目录004")
//					,new Dir("005","目录005")
//					,new Dir("006","目录006")
//					,new Dir("007","目录007")
//					,new Dir("008","目录008")
//					,new Dir("009","目录009")
//					,new Dir("010","目录010")
//					,new Dir("011","目录011")
//					,new Dir("012","目录012")
//					,new Dir("013","目录013")
//			};
//			result = Arrays.asList(dir);
//		}else{
//			
//		}
//		return result; 
		Collection<Dir> result = new ArrayList<Dir>();
		 try{
			AbderaClient client = LCHelper.getAbderaClient();
			RequestOptions options = client.getDefaultRequestOptions();
			// options.setIfModifiedSince(new java.util.Date());
			options.setNoCache(true);
//			options.setHeader("Cookie", LCHelper.getLtpaCookie());
			ClientResponse response = client.get(SystemConfig.getProperty("lc.introspectionUrl"),options);
			Document<Service> doc = response.getDocument();
			Service service = doc.getRoot();
			org.apache.abdera.model.Collection collection = service.getCollection(SystemConfig.getProperty("lc.introspection.key.Folders"), SystemConfig.getProperty("lc.introspection.key.MyFolders"));
			String myDirCollectionUrl = collection.getHref().toASCIIString();
			response.release(); 
			
			response = client.get(myDirCollectionUrl+"&pageSize=100",options);
			Document<Feed> feedDoc = response.getDocument();
			Feed feed = feedDoc.getRoot();
			List<Entry> entrys = feed.getEntries();
			for (Entry entry : entrys) {
				Dir dir = new Dir(entry.getId().toASCIIString(),entry.getTitle());
				result.add(dir);
			}
			response.release();
		 }catch(Exception ex){
			 
		 }
		return result;
		
	}
	
	public Collection<Dir> findMyDirList(){
		Collection<Dir> result = new ArrayList<Dir>();
		 try{
			AbderaClient client = LCHelper.getAbderaClient();
//			RequestOptions options = client.getDefaultRequestOptions();
			// options.setIfModifiedSince(new java.util.Date());
			
			String url = SystemConfig.getProperty("lc.introspectionUrl");
			System.out.println(url);
			ClientResponse response = client.get(url);

			Document<Service> doc = response.getDocument();
			Service service = doc.getRoot();
			System.out.println(service);
			org.apache.abdera.model.Collection collection = service.getCollection(SystemConfig.getProperty("lc.introspection.key.Folders"), SystemConfig.getProperty("lc.introspection.key.MyFolders"));
			String myDirCollectionUrl = collection.getHref().toASCIIString();
			response.release(); 
			
			response = client.get(myDirCollectionUrl);
			Document<Feed> feedDoc = response.getDocument();
			Feed feed = feedDoc.getRoot();
			List<Entry> entrys = feed.getEntries();
			for (Entry entry : entrys) {
				Dir dir = new Dir(entry.getId().toASCIIString(),entry.getTitle());
				result.add(dir);
			}
			response.release();
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		return result;
	}
	
	
	
}
