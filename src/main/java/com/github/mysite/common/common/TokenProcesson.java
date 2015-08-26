package com.github.mysite.common.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.bouncycastle.util.encoders.Base64;

/**
 * 名称: TokenProcesson.java<br>
 * 描述: 令牌发生器，产生唯一令牌，单例<br>
 * 类型: JAVA<br>
 * @since  2015年7月14日
 * @author jy.chen
 */
public class TokenProcesson {

	/**
	 * 1.构造方法私有
	 * 2.自己创建一个
	 * 3.对外暴露一个方法，允许获取上面创建的对象
	 */
	private TokenProcesson(){}
	private static final TokenProcesson instance = new TokenProcesson();
	public static TokenProcesson getInstance(){
		return instance;
	}
	public String generateToken(){
		String token = System.currentTimeMillis() + new Random().nextInt() + "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] MD5 = md.digest(token.getBytes());
			byte[] encode = Base64.encode(MD5);
			return new String(encode);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 判断表单号 token 是否有效
	 * @param httpsession
	 * @return
	 */
	public boolean isTokenVaid(String client_token,HttpSession session) {
		if (client_token == null) {
			return false;
		}
		String server_token = (String) session.getAttribute(
				"token");
		if (server_token == null) {
			return false;
		}
		if (!server_token.equals(client_token)) {
			return false;
		}
		return true;
	}
}
