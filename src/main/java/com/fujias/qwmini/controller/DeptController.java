package com.fujias.qwmini.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fujias.qwmini.redis.RedisService;
import com.fujias.qwmini.results.AjaxResult;
import com.fujias.qwmini.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : YU
 * create at:  2021/7/30  10:02
 * @description: 部门controller
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController{
    /*获取部门列表*/
    private static String GET_DEPT_LIST = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";
    private static String GET_DEPT_LIST2 = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisService redisService;

    /***
     *
     * @description:
     * @author YuYongqiang
     * @date 2021/7/30 10:07
     * @param [java.lang.String] 部门id。获取指定部门及其下的子部门（
     *                           以及及子部门的子部门等等，递归）。
     *                           如果不填，默认获取全量组织架构
     * @return com.fujias.qwmini.results.AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult deptList(@RequestParam(required = false)  String id  ){

        String access_token = tokenService.getToken();
        String jsonStr;
        if(StrUtil.isNotEmpty(id)){
             jsonStr = HttpUtil.get(GET_DEPT_LIST.replaceAll("ACCESS_TOKEN",access_token).replaceAll("ID",id));
        }else{
             jsonStr = HttpUtil.get(GET_DEPT_LIST2.replaceAll("ACCESS_TOKEN",access_token));
        }

        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);

        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put(AjaxResult.CODE_TAG,200);
        ajaxResult.put(AjaxResult.MSG_TAG,"SUCCESS");
        ajaxResult.put(AjaxResult.DATA_TAG,jsonObject);
        redisService.set("dept_list", String.valueOf((jsonObject.get("department"))));

        return ajaxResult;
    }

    /***
     *
     * @description:
     * @author YuYongqiang
     * @date 2021/7/30 10:07
     * @param [java.lang.String] 部门id。获取指定部门及其下的子部门（
     *                           以及及子部门的子部门等等，递归）。
     *                           如果不填，默认获取全量组织架构
     * @return com.fujias.qwmini.results.AjaxResult
     */
    @PostMapping("/list")
    public AjaxResult deptList2( ){

        String access_token = tokenService.getToken();
        String jsonStr;
        jsonStr = HttpUtil.get(GET_DEPT_LIST2.replaceAll("ACCESS_TOKEN",access_token));

        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);

        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put(AjaxResult.CODE_TAG,200);
        ajaxResult.put(AjaxResult.MSG_TAG,"SUCCESS");
        ajaxResult.put(AjaxResult.DATA_TAG,jsonObject);
        redisService.set("dept_list", String.valueOf((jsonObject.get("department"))));

        return ajaxResult;
    }

}


