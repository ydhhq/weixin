package com.wx.model.configuration;

import com.wx.bean.AccessToken;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * Token的配置类
 * @author Administrator
 *
 */
public class TokenConfig {
	private static String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String APPID="wx0aa254b92c5a3386";
	private static String APPSECRET="07617e1e642a418e0968f14779f04f44";
	private static  AccessToken token=null;
	//客服的url
		private static String customerUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 初始化url
	private static void  initToken() {
		// 替换url中的APPID和APPSECRET
		url=url.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		// 从微信服务器获取对应权限
		String tokenStr = HttpUtil.get(url);
		System.out.println(tokenStr);
		// 解析微信服务器发来的JSON请求
		JSONObject jsonObject = JSONUtil.parseObj(tokenStr);
		// 获取里面的值
		String appid = jsonObject.getStr("access_token");
		String secret = jsonObject.getStr("expires_in");
		System.out.println(secret);
		// 实例化一个AccessToken对象
		token = new AccessToken(appid, secret);
	}
	// 获取token的方法
	public static String getTokenurl() {
		if(token==null||token.isOverdue()) {
			initToken();
		}
		return token.getAppid();
	}
	// 获取客服调用接口
	public static String getCustomerUrl() {
	 customerUrl = customerUrl.replace("ACCESS_TOKEN", getTokenurl());
	 return customerUrl;
	}
}
