package com.sds.core.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.httpclient.methods.FileRequestEntity;

public class CustomFileEntity extends FileRequestEntity {
	private FileTransformListener transformHandler;

	public CustomFileEntity(File file,String contentType,FileTransformListener transformHandler){
		this(file,contentType);
		this.transformHandler = transformHandler;
	}
	
	public CustomFileEntity(File file, String contentType) {
		super(file, contentType);
	}

	@Override
	public void writeRequest(OutputStream out) throws IOException {
		super.writeRequest(new CountingOutputStream(out,transformHandler));
	}
	

}
