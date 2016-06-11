package com.sds.core.exceptions;

/**
 * 文件上传异常
 * 		HTTP/1.1 401 Unauthorized
		HTTP/1.1 403 Forbidden
		HTTP/1.1 404 Not Found
		HTTP/1.1 409 Conflict
		HTTP/1.1 415 Unsupported media type
 * @author T420
 *
 */
public class LCFileUploadException extends Exception {
	private int status;

	public LCFileUploadException(int status,String string) {
		super(string);
		this.status = status;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5749181170915509398L;

	public int getStatus() {
		return status;
	}

	
}
