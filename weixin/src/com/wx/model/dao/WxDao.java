package com.wx.model.dao;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wx.model.configuration.TextTemplate;
import com.wx.model.configuration.TokenConfig;
import com.wx.utils.ImageUtil;
import com.wx.utils.QrcodeUtils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class WxDao {
 static	Scanner sc = new Scanner(System.in);

	//处理xml信息
	public static Map<String,String> handleMap(HttpServletRequest request){
		try {
			InputStream in=request.getInputStream();
			//准备一个map
			Map<String,String> xmlMap=new HashMap<String,String>();
			//dom4j
			SAXReader reader=new SAXReader();
			//获取到整个xml内容
			Document document=reader.read(in);
			//获取到第一个节点，所有节点的父节点
			Element root=document.getRootElement();
			//拿到所有子节点
			List<Element> elements=root.elements();
			for(Element e:elements) {
				String tagName=e.getName();
				String value=e.getStringValue();
				xmlMap.put(tagName, value);
			}
			return xmlMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//回送微信端字符串
	public static String getResponseStr(Map<String, String> xmlMap) {
		String msgType=xmlMap.get("MsgType");
		String resultXml="";
		switch(msgType) {
		case "text":resultXml=handleTextMessage(xmlMap);
		break;
		case "event":resultXml=handleEventSubscribe(xmlMap);
		break;
		case "image":resultXml=handleImageSubscribe(xmlMap);
		break;
		}
		return resultXml;
	}
	
	//处理文本内容
	private static String handleTextMessage(Map<String, String> xmlMap) {
		//xmlMap.put("FromUserName", "openid");  这个位置可以增加业务
				//增加一个客服回复功能
		
			
				//调用客服接口
				String url=TokenConfig.getCustomerUrl();
				System.out.println(xmlMap.get("Content"));
//				System.out.println("请输入");
				String count = sc.nextLine();
//				System.out.println(count);
//				for(int i=0;i<10;i++) {
					String result=TextTemplate.getCustomer(count, xmlMap);
					HttpUtil.post(url,result);
//				}
				
				return TextTemplate.getTextTemplate(xmlMap);
	}
	// 处理关注
	private static String handleEventSubscribe(Map<String, String> xmlMap) {
		//xmlMap.put("FromUserName", "openid");  这个位置可以增加业务
		// 测试
		String fromUserName = xmlMap.get("FromUserName");
//		System.out.println(fromUserName);
			String codeurl=QrcodeUtils.getQrcode(fromUserName);
			// 获取用户信息
			JSONObject usJson=user(xmlMap);
			String headimgurl=usJson.getStr("headimgurl");
			// 获取二维码信息
			String str = xmlMap.get("EventKey");
			if(null!=str&&str!="") {
				
				str = str.substring(8);
				String result= TextTemplate.getHelpTemplate(xmlMap, str);
				String url=TokenConfig.getCustomerUrl();
				HttpUtil.post(url,result);
			}
			// 助力回复
			System.out.println(str);
			ImageUtil.dowloadImg(headimgurl, codeurl, "dfd");
			
//			System.out.println(xmlMap.get("EventKey"));
		return TextTemplate.getEventTemplate(xmlMap);
	}
	// 图片消息处理
	private static String handleImageSubscribe(Map<String, String> xmlMap) {
		//xmlMap.put("FromUserName", "openid");  这个位置可以增加业务
		return TextTemplate.getImageTemplate(xmlMap);
		
	}
	// 获取用户信息
	private static JSONObject user(Map<String, String> xmlMap) {
		String userBaseInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		userBaseInfoUrl = userBaseInfoUrl.replace("ACCESS_TOKEN", TokenConfig.getTokenurl()).replace("OPENID", xmlMap.get("FromUserName"));
		String jsonString = HttpUtil.get(userBaseInfoUrl);
		JSONObject userjson=JSONUtil.parseObj(jsonString);
		System.out.println(userjson.toString());
		return userjson;

	}
	
}
