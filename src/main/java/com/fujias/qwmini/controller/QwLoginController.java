package com.fujias.qwmini.controller;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.fujias.qwmini.constants.HttpStatus;
import com.fujias.qwmini.redis.RedisService;
import com.fujias.qwmini.results.AjaxResult;
import com.fujias.qwmini.results.Code2SessionResult;

import com.fujias.qwmini.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qw/")
public class QwLoginController  {


    /* 获取token地址 */

    public static String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    /* 小程序ID */

    public static String CORP_ID = "ww38955021ca3074fb";
    /* 小程序密钥 */

    public static String CORPSECRET = "RkNAe_K6cMM8ojrZIm0Kz2a-sFBQNzHD_VCocihjd-g";

    /*临时登录凭证校验接口*/

    public static String  CODE2SESSION_URL ="https://qyapi.weixin.qq.com/cgi-bin/miniprogram/jscode2session?access_token=ACCESS_TOKEN&js_code=CODE&grant_type=authorization_code";

    /*固定token*/
    public static String TOKEN = "qIWPiDYsiTcOHwl9B08snJZ4XiIVuXC8bpALloeNu_GlES2rIGYBaDIMeRqEgF2jTOrtm4XvkVYsZIrKqJIF3AnO0okB60ur5gJDnRe1huaJZZTtZTAcI5WJ88Hjdt9P4GglwRmh5Vgtu_Jjn_4Ta0sC-M-EZ1QpuwJ52Pqq3XF0XNMUT0L-dPSnghJ5IK4qLYGImqQpzBs_uCtt_2BcRQ";

    /** redis服务 */
    @Autowired
    private RedisService redisService;

    /** token服务*/
    @Autowired
    private TokenService tokenService;


    /**
     * 企业微信小程序登录
     * @param code  前端回调获得code
     *
     * @return 接口响应参数
     */
    @PostMapping("/code2Session")
    public AjaxResult AjaxResult(@RequestParam String code){

        /*获取access_token*/
        String access_token = tokenService.getToken();
        /* http请求获取到结果json串*/
        String  jsonString =  HttpUtil.get(CODE2SESSION_URL.replaceAll("ACCESS_TOKEN",access_token).replaceAll("CODE",code));
        /* 转为JSONObject*/
        JSONObject jsonObject  = JSONUtil.parseObj(jsonString);
        String sk = String.valueOf(jsonObject.get("session_key"));
        redisService.set("session_key",sk);
        /* 转为返回参数对象*/
        Code2SessionResult result  = JSONUtil.toBean( jsonObject,Code2SessionResult.class);

        String session_key = result.getSession_key();
        String user_id = result.getUserid();


        AjaxResult ajaxResult = new AjaxResult();
        int errcode  = (int) jsonObject.get("errcode");
        if (errcode == 0){
            ajaxResult.put(AjaxResult.CODE_TAG, HttpStatus.SUCCESS);
            ajaxResult.put(AjaxResult.MSG_TAG, "SUCCESS");
            ajaxResult.put(AjaxResult.DATA_TAG,jsonObject);
            /*固定token*/
            ajaxResult.put(AjaxResult.TOKEN_TAG,TOKEN);
        }else{
            ajaxResult.put(AjaxResult.CODE_TAG,jsonObject.get("errcode"));
            ajaxResult.put(AjaxResult.MSG_TAG,jsonObject.get("errmsg"));
        }

//        return result;
        return  ajaxResult;

    }

}