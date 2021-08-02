package com.fujias.qwmini.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fujias.qwmini.results.AjaxResult;
import com.fujias.qwmini.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : YuYongqiang
 * @description: 消息推送
 * create at:  2021/7/30  16:06
 */
@RestController
@RequestMapping("/message/send")
public class MessageController extends BaseController{

    /**发送应用消息接口*/
    private static String POST_SEND_MESSAGE ="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    /* 小程序ID */

    public static String CORP_ID = "ww38955021ca3074fb";


    @Autowired
    private TokenService tokenService;

    @PostMapping("text")
    public AjaxResult sendText(){

        Map<String,Object> map = new HashMap<>();
        map.put("touser","DingLei");
        map.put("toparty"," ");
        map.put("totag"," ");
        map.put("msgtype","text");
        map.put("agentid",CORP_ID);
        map.put("content","测试消息发送!");
        map.put("safe","0");
        map.put("enablle_id_trans","0");
        map.put("enable_duplicate_check","0");
        map.put("duplicate_check_interval","1800");

        String access_token  = tokenService.getToken();
        JSONObject jsonObject  = JSONUtil.parseObj(map);

        String jsonStr = HttpUtil.post(POST_SEND_MESSAGE.replaceAll("ACCESS_ID",access_token),map);
        String jsonStr2 = HttpUtil.post(POST_SEND_MESSAGE.replaceAll("ACCESS_ID",access_token),jsonObject);
        AjaxResult ajaxResult  = new AjaxResult();
        ajaxResult.put(AjaxResult.CODE_TAG,200);
        ajaxResult.put(AjaxResult.MSG_TAG,"SUCCESS");
        ajaxResult.put(AjaxResult.DATA_TAG, JSONUtil.parseObj(jsonStr));

        return AjaxResult.success(JSONUtil.parseObj(jsonStr));

//        return ajaxResult;




    }



}
