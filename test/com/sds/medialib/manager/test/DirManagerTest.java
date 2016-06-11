package com.sds.medialib.manager.test;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sds.medialib.manager.DirManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class DirManagerTest {
	
	@Resource
	private DirManager dirManager;
	
	@Test
	public void testAAA(){
		dirManager.findMyDirList();
	}

	public static void main(String[] args) throws IOException {
//		Abdera abdera = new Abdera();
//		Factory factory = abdera.getFactory();
//		Entry entry = factory.newEntry();
//		URL url = new URL("file:c:/ltpa.key.file");
//		URLDataSource ds = new URLDataSource(url);
//		DataHandler dh = new DataHandler(ds);
//		entry.setContent(dh);
//		
//		entry.writeTo(System.out);
		
//		Abdera abdera = new Abdera();
//		Factory factory = abdera.getFactory();
//		Entry entry = factory.newEntry();
//		entry.setId(FOMHelper.generateUuid());
//		entry.setUpdated(new java.util.Date());
//		entry.addAuthor("James");
//		entry.setTitle("额滴神啊，神奇的喔");
//		entry.setContentAsXhtml("<p>额滴神啊，神奇的喔</p>");
//
//		entry.addExtension(new QName("gd","transparency")).setAttributeValue("value",	"http://schemas.google.com/g/2005#event.opaque");
//		entry.addExtension(new QName("gd","eventStatus")).setAttributeValue("value",	"http://schemas.google.com/g/2005#event.confirmed");
//		entry.addExtension(new QName("gd","where")).setAttributeValue("valueString","额滴神啊，神奇的喔");
//		Element el = entry.addExtension(new QName("gd","when"));
//		el.setAttributeValue("startTime", AtomDate.valueOf(new Date()).toString());
//		el.setAttributeValue("endTime", AtomDate.valueOf(new Date()).toString());
//		
//		entry.writeTo(System.out);

		
	}
	
	
}
