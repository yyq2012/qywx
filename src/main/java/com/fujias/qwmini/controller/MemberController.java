package com.fujias.qwmini.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fujias.qwmini.redis.RedisService;
import com.fujias.qwmini.results.AjaxResult;
import com.fujias.qwmini.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : YU
 * create at:  2021/7/30  9:21
 * @description: 企业微信成员类
 */
@RestController
@RequestMapping("/member")
public class MemberController extends BaseController{

    /*读取成员接口地址*/
    private static String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;
    /***
     *
     * @description: 读取成员信息
     * @author YuYongqiang
     * @date 2021/7/30 10:01
     * @param [java.lang.String] userid
     * @return com.fujias.qwmini.results.AjaxResult
     */
    @GetMapping("/read")
    public AjaxResult getMemberList(@RequestParam String userid){
        String access_token = tokenService.getToken();

        String json =  HttpUtil.get(url.replaceAll("ACCESS_TOKEN",access_token).replaceAll("USERID",userid));
        JSONObject jsonObject = JSONUtil.parseObj(json);


        AjaxResult ajaxResult = new AjaxResult();

        ajaxResult.put(AjaxResult.CODE_TAG,200);
        ajaxResult.put(AjaxResult.MSG_TAG,"SUCCESS");
        ajaxResult.put(AjaxResult.DATA_TAG,jsonObject);

        return ajaxResult;

    }
}
