package com.fujias.qwmini.controller;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.fujias.qwmini.redis.RedisService;
import com.fujias.qwmini.results.Code2SessionResult;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/qw/")
public class QwLoginController  {


    /*获取token地址*/
    public static String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    /*小程序ID*/
    public static String CORP_ID = "ww38955021ca3074fb";
    /*小程序密钥*/
    public static String CORPSECRET = "RkNAe_K6cMM8ojrZIm0Kz2a-sFBQNzHD_VCocihjd-g";

    /*临时登录凭证校验接口*/
    public static String  CODE2SESSION_URL ="https://qyapi.weixin.qq.com/cgi-bin/miniprogram/jscode2session?access_token=ACCESS_TOKEN&js_code=CODE&grant_type=authorization_code";
//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

    /**
     * 测试 小程序获取token
     *
     * @return token
     *
     */
    @GetMapping("access_token")
    public  String getToken(){

        /*if (redisTemplate.hasKey("access_token")){
            return redisTemplate.boundValueOps("access_token").get().toString();
        }*/
        /*先从redis获取token*/
        String key = "access_token";
        if (redisService.havekey(key)){
            return redisService.get(key);
        }

        String token = HttpUtil.get(TOKEN_URL.replaceAll("ID",CORP_ID).replaceAll("SECRET",CORPSECRET));

        JSON json = JSONUtil.parseObj(token);
        System.out.println(json);
        String tk = (String) json.getByPath("access_token");
        int expire = (int) json.getByPath("expires_in");

        /*redisTemplate.boundValueOps("access_token").set(tk);
        redisTemplate.expire("acess_token",expire, TimeUnit.SECONDS);
*/
        redisService.set(key,tk);
        redisService.expire(key,expire);


        return tk;

    }

    /**
     * 企业微信小程序登录
     * @param code  前端回调获得code
     *
     * @return 接口响应参数
     */
    @PostMapping("/code2Session")
    public Code2SessionResult result(@RequestParam String code){
        String key = "access_token";
        String access_token;
        if (redisService.havekey(key)){
            access_token  = redisService.get(key);
        }else{
            String access_token_josn =  HttpUtil.get(TOKEN_URL.replaceAll("ID",CORP_ID).replaceAll("SECRET",CORPSECRET));
            JSON json = JSONUtil.parseObj(access_token_josn);
            access_token = (String) json.getByPath("access_token");
            int expire = (int) json.getByPath("expires_in");
            redisService.set(key,access_token);
            redisService.expire(key,expire);
        }
        /*http请求获取到结果json串*/
        String  jsonString =  HttpUtil.get(CODE2SESSION_URL.replaceAll("ACCESS_TOKEN",access_token).replaceAll("CODE",code));
        /*转为JSONObject*/
        JSONObject jsonObject  = JSONUtil.parseObj(jsonString);
        String sk = String.valueOf(jsonObject.get("session_key"));
        /*转为返回参数对象*/
        Code2SessionResult result  = JSONUtil.toBean( jsonObject,Code2SessionResult.class);

        String session_key = result.getSession_key();
        String user_id = result.getUserid();

        return result;


    }

}