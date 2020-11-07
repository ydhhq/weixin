package com.wx.model.configuration;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class WeiXinUserInfoUtils {
    private static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
 
    /**
     * 获取微信用户账号的相关信息
     * @param opendID  用户的openId，这个通过当用户进行了消息交互的时候，才有
     * @return
     */
    public static JSONObject getUserInfo(String opendID){
        String token = TokenConfig.getTokenurl();
        //获取access_token
        String url = GET_USERINFO_URL.replace("ACCESS_TOKEN" , token);
        url = url.replace("OPENID" ,opendID);
        String tokenStr = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(tokenStr);
        return jsonObject;
//        System.out.println(jsonObject.toString());
//        return jsonObject.toString();
    }
}
