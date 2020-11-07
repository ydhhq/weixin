package com.wx.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.wx.model.configuration.TokenConfig;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
	 * 生成二维码的工具
	 * @author Administrator
	 *
	 */
public class QrcodeUtils {
	private static String ticketurl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	private static String url ="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	public static JSONObject getTicket(String cotent) {
		// 获取token
		String token = TokenConfig.getTokenurl();
		String ur = url.replace("TOKEN", token);
		String json ="{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+cotent+"\"}}}";
		String tokenStr = HttpUtil.post(ur, json);
		JSONObject ticket = JSONUtil.parseObj(tokenStr);
//		System.out.println(ticket.toString());
		return ticket;
	}
	// 获取二维码的路径
	public static String getQrcode(String formid) {
		JSONObject ticket = getTicket(formid);
		String tic = ticket.getStr("ticket");
		try {
		tic = URLEncoder.encode(tic, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取二维码的路径
		String ticketur = ticketurl.replace("TICKET", tic);
//		System.out.println(ticketur);
		// 下载二维码
		HttpUtil.downloadFile(ticketur, "E:\\tst/yd.jpg");
		return ticketur;
	}
}
