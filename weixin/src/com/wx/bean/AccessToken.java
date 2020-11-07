package com.wx.bean;
/**
 * 
 * @author Administrator
 *
 */
public class AccessToken {
	private String appid;
	private long secret;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public long getSecret() {
		return secret;
	}
	public void setSecret(long secret) {
		this.secret = secret;
	}
	// 构造方法
	public AccessToken(String appid, String secret) {
		super();
		this.appid = appid;
		this.secret = System.currentTimeMillis()+Integer.parseInt(secret)*1000;
	}
	// 判断是否token是否过期
	public  boolean isOverdue() {
		// 如果是true过期
		return System.currentTimeMillis()>secret;
	}
	
}
