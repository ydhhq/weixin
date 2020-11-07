package com.wx.model.configuration;

import java.util.Map;

import com.wx.utils.StringUtils;

import cn.hutool.json.JSONObject;

public class TextTemplate {
	//1 关注回复  2自动回复  3其他回复
	public static String getContent(int type) {
		switch(type) {
		case 2:return "text";
		case 3:return "欢迎关注";
		}
		return null;
	}

	public static String getTextTemplate(Map<String,String> xmlMap) {
		String template="<xml>\r\n" + 
					"  <ToUserName><![CDATA["+xmlMap.get("FromUserName")+"]]></ToUserName>\r\n" + 
					"  <FromUserName><![CDATA["+xmlMap.get("ToUserName")+"]]></FromUserName>\r\n" + 
					"  <CreateTime>"+StringUtils.getWxCreateTime()+"</CreateTime>\r\n" + 
					"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
					"  <Content><![CDATA["+getContent(2)+"]]></Content>\r\n" + 
					"</xml>";
		return template;
	}
	
	
	
	
	// 助力回复
	public static String getHelpTemplate(Map<String,String> xmlMap,String str) {
		JSONObject json=WeiXinUserInfoUtils.getUserInfo(xmlMap.get("FromUserName"));
		// 获取昵称
		String nickname =json.getStr("nickname");
		System.out.println(xmlMap.get("ToUserName"));
		String result = "{\r\n" + 
				"    \"touser\":\""+str+"\",\r\n" + 
				"    \"msgtype\":\"text\",\r\n" + 
				"    \"text\":\r\n" + 
				"    {\r\n" + 
				"         \"content\":\""+nickname+"帮你助力"+"\"\r\n" + 
				"    }\r\n" + 
				"}";
		return result;
	}
	
	
	
	public static String getEventTemplate(Map<String,String> xmlMap) {
		JSONObject json=WeiXinUserInfoUtils.getUserInfo(xmlMap.get("FromUserName"));
		// 获取昵称
		String nickname =json.getStr("nickname");
		String template="<xml>\r\n" + 
					"  <ToUserName><![CDATA["+xmlMap.get("FromUserName")+"]]></ToUserName>\r\n" + 
					"  <FromUserName><![CDATA["+xmlMap.get("ToUserName")+"]]></FromUserName>\r\n" + 
					"  <CreateTime>"+StringUtils.getWxCreateTime()+"</CreateTime>\r\n" + 
					"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
					"  <Content><![CDATA["+"欢迎"+nickname+"关注公众号/左哼哼/::)"+"]]></Content>\r\n" + 
					"</xml>";
		return template;
	}
	
	public static String getImageTemplate(Map<String,String> xmlMap) {
		String template="<xml>\r\n" + 
					"  <ToUserName><![CDATA["+xmlMap.get("FromUserName")+"]]></ToUserName>\r\n" + 
					"  <FromUserName><![CDATA["+xmlMap.get("ToUserName")+"]]></FromUserName>\r\n" + 
					"  <CreateTime>"+StringUtils.getWxCreateTime()+"</CreateTime>\r\n" + 
					"  <MsgType><![CDATA[image]]></MsgType>\r\n" + 
					"   <Image>\r\n" + 
					"    <MediaId><![CDATA["+xmlMap.get("MediaId")+"]]></MediaId>\r\n" + 
					"  </Image>\r\n" + 
					"</xml>";
		return template;
	}
	public static String getCustomer(String content,Map<String,String> xmlMap) {
		String result = "{\r\n" + 
				"    \"touser\":\""+xmlMap.get("FromUserName")+"\",\r\n" + 
				"    \"msgtype\":\"text\",\r\n" + 
				"    \"text\":\r\n" + 
				"    {\r\n" + 
				"         \"content\":\""+content+"\"\r\n" + 
				"    }\r\n" + 
				"}";
		return result;
	} 
	
}
