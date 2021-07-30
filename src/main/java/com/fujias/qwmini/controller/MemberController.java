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

import java.util.Map;

/**
 * @author : YU
 * create at:  2021/7/30  9:21
 * @description: 企业微信成员controller
 */
@RestController
@RequestMapping("/member")
public class MemberController extends BaseController{

    /*读取成员接口地址*/
    private static String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";

    /*获取部门成员接口地址*/
    private  static String GET_DEPT_MEMBERS = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
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
    @PostMapping("/read")
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
    /***
     * @description:获取部门成员
     * @date 2021/7/30 10:41
     * @param department_id 获取的部门id
     * @param fetch_child 是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
     * @return com.fujias.qwmini.results.AjaxResult
     * @author YuYongqiang
     */

    @PostMapping("/deptmembers")
    public AjaxResult getDeptMembers (@RequestBody Map<String,String> map ) {

        String access_token = tokenService.getToken();
        String DEPARTMENT_ID = map.get("DEPARTMENT_ID");
        String FETCH_CHILD  = map.get("FETCH_CHILD");
        String jsonStr;
        if (StrUtil.isEmpty(FETCH_CHILD)){
            FETCH_CHILD = "1";
        }
        jsonStr = HttpUtil.get(GET_DEPT_MEMBERS.replaceAll("ACCESS_TOKEN", access_token).replaceAll("DEPARTMENT_ID",DEPARTMENT_ID).replaceAll("FETCH_CHILD", FETCH_CHILD));

        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put(AjaxResult.CODE_TAG,200);
        ajaxResult.put(AjaxResult.MSG_TAG,"SUCCESS");
        ajaxResult.put(AjaxResult.DATA_TAG,jsonObject);


        return ajaxResult;
    }

}
