package com.sds.medialib.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

import com.ibm.websphere.security.WSSecurityException;
import com.ibm.websphere.security.auth.WSSubject;
import com.ibm.ws.util.Base64;
import com.ibm.wsspi.security.token.SingleSignonToken;
import com.sds.core.SystemConfig;
import com.sds.core.util.StringUtil;

public class LCHelper {
	

	/**
	 * 获取AbderaClient对象
	 * @return
	 */
	public static AbderaClient getAbderaClient() {
		
		AbderaClient client = new AbderaClient();
		
		loginByFixedAccount(client);
//		loginByLtpaToken(client); 
		
		client.usePreemptiveAuthentication(true);
		client.setConnectionManagerTimeout(1000);
		client.getDefaultRequestOptions().setNoCache(true);
		client.getDefaultRequestOptions().setAcceptCharset("GBK","utf-8");
		client.getDefaultRequestOptions().setAcceptLanguage("zh-CN","zh");
//		client.getDefaultRequestOptions().setAcceptEncoding("gzip,deflate,sdch");
//		client.getDefaultRequestOptions().setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		AbderaClient.registerTrustManager();
//		AbderaClient.registerTrustManager(8888);
//		AbderaClient.registerTrustManager(9444);
		return client;
	}
	
	
	@SuppressWarnings("unused")
	private static void loginByFixedAccount(AbderaClient client){
		String secureUrl = SystemConfig.getProperty("lc.securityUrl");
		Credentials creds = new UsernamePasswordCredentials(SystemConfig.getProperty("lc.adminuser"),SystemConfig.getProperty("lc.adminpassword"));
		try {
			client.addCredentials(secureUrl, null, null, creds);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void loginByLtpaToken(AbderaClient client){
		try {
			Set credentials = WSSubject.getCallerSubject().getPrivateCredentials();
			for (Object object : credentials) {
				if(object instanceof SingleSignonToken){
					SingleSignonToken token = (SingleSignonToken) object;
					String tokenString = Base64.encode(token.getBytes());
					client.addCookie(SystemConfig.getProperty("medialib.ssoDomainName"), token.getName(), tokenString,"",10000,true);
					break;
				}
			}
		} catch (WSSecurityException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取nonce头
	 * @return
	 */
	public static String getNonce(){
		String nonceUrl = SystemConfig.getProperty("lc.apiUrlPrefix")+"/nonce";
		AbderaClient client = getAbderaClient();
		ClientResponse cr = client.get(nonceUrl);
		String result = null;
		try {
			result = StringUtil.getStringFromStream(cr.getInputStream(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
//	/**
//	 * 获取LTPA Cookie Token
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	public static String getLtpaCookie(){
//		String ltpaCookieString = null;
//		try {
//			Set credentials = WSSubject.getCallerSubject().getPrivateCredentials();
//			for (Object object : credentials) {
//				if(object instanceof SingleSignonToken){
//					SingleSignonToken token = (SingleSignonToken) object;
//					ltpaCookieString = Base64.encode(token.getBytes());
//					break;
//				}
//			}
//		} catch (WSSecurityException e) {
//			e.printStackTrace();
//		}
//		return ltpaCookieString;
//	}
}
