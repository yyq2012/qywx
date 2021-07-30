package com.fujias.qwmini.controller;

//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.ruoyi.common.constant.HttpStatus;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.common.core.page.PageDomain;
//import com.ruoyi.common.core.page.TableDataInfo;
//import com.ruoyi.common.core.page.TableSupport;
//import com.ruoyi.common.utils.DateUtils;
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.common.utils.sql.SqlUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fujias.qwmini.constants.HttpStatus;
import com.fujias.qwmini.page.PageDomain;
import com.fujias.qwmini.page.TableDataInfo;
import com.fujias.qwmini.page.TableSupport;
import com.fujias.qwmini.results.AjaxResult;
import com.fujias.qwmini.utils.MyUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;



import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理
 * 
 * @author ruoyi
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtil.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (ObjectUtil.isNotNull(pageNum) && ObjectUtil.isNotNull(pageSize))
        {
            String orderBy = MyUtils.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     * 
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 页面跳转
     */
//    public String redirect(String url)
//    {
//        return StringUtils.format("redirect:{}", url);
//    }
}
