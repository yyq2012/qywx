package com.fujias.qwmini.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fujias.qwmini.redis.RedisService;
import com.fujias.qwmini.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : YU
 * create at:  2021/7/30  9:36
 * @description: impl
 */
@Service
public class TokenServiceImpl implements TokenService {


    /* 获取token地址 */

    public static String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    /* 小程序ID */

    public static String CORP_ID = "ww38955021ca3074fb";
    /* 小程序密钥 */

    public static String CORPSECRET = "RkNAe_K6cMM8ojrZIm0Kz2a-sFBQNzHD_VCocihjd-g";

    /*临时登录凭证校验接口*/

    public static String  CODE2SESSION_URL ="https://qyapi.weixin.qq.com/cgi-bin/miniprogram/jscode2session?access_token=ACCESS_TOKEN&js_code=CODE&grant_type=authorization_code";

    @Autowired
    private RedisService redisService;

    /***
     *
     * @description: 获取小程序的 access_token
     * @author YuYongqiang
     * @date 2021/7/30 9:43
     * @param []
     * @return java.lang.String
     */
    @Override
    public String getToken() {
        String key = "access_token";
        if (StrUtil.isNotEmpty(redisService.get(key))){
            System.out.print(redisService.get(key));
            return redisService.get(key);
        }

        String token = HttpUtil.get(TOKEN_URL.replaceAll("ID",CORP_ID).replaceAll("SECRET",CORPSECRET));

        JSON json = JSONUtil.parseObj(token);
//        System.out.println(json);
        String tk = (String) json.getByPath("access_token");
        int expire = (int) json.getByPath("expires_in");
        redisService.set(key,tk);
        redisService.expire(key,expire);

        return tk;
    }
}
