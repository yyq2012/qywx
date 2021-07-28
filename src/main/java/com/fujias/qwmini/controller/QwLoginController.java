package com.fujias.qwmini.controller;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/qw/")
public class QwLoginController  {


    /*获取token地址*/
    public static String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    /*小程序ID*/
    public static String CORP_ID = "wx5429af15260896b3";
    /*小程序密钥*/
    public static String CORPSECRET = "c42b59b13a7b7fc3d53c266f733f49c6";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 小程序获取token
     *
     * @return token
     * @throws Exception Exception
     */
    @GetMapping("token")
    public String getToken(){

        if (redisTemplate.hasKey("access_token")){
            return redisTemplate.boundValueOps("access_token").get().toString();
        }
        String token = HttpUtil.get(TOKEN_URL.replaceAll("ID",CORP_ID).replaceAll("SECRET",CORPSECRET));

        JSON json = JSONUtil.parseObj(token);
        System.out.println(json.toString());
        String tk = (String) json.getByPath("access_token");
        int expire = (int) json.getByPath("expires_in");

        redisTemplate.boundValueOps("access_token").set(tk);
        redisTemplate.expire("acess_token",expire, TimeUnit.SECONDS);



        return tk;

    }

}
