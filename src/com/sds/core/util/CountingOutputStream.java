package com.sds.core.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends FilterOutputStream {
	private long transferred;
	private FileTransformListener transformHandler = null;

	public CountingOutputStream(OutputStream out) {
		super(out);
		transferred = 0;
	}

	public CountingOutputStream(OutputStream out,
			FileTransformListener transformHandler) {
		super(out);
		this.transformHandler = transformHandler;
	}

	@Override
	public void write(byte[] buffer, int offset, int length) throws IOException {
		transferred += length;
		if(this.transformHandler != null)
			this.transformHandler.transformed(transferred);
		super.write(buffer, offset, length);
	}
//
//	@Override
//	public void write(int oneByte) throws IOException {
////		transferred++;
//		super.write(oneByte);
//	}

	@Override
	public void flush() throws IOException {
//		this.flush();
		super.flush();
	}

	@Override
	public void close() throws IOException {
//		this.close();
		System.out.println(this.transferred);
		super.close();
	}
}
