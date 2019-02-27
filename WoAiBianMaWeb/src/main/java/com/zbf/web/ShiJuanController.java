package com.zbf.web;


import com.zbf.common.ResponseResult;
import com.zbf.core.CommonUtils;
import com.zbf.core.page.Page;
import com.zbf.core.utils.UID;
import com.zbf.service.ShiJuanService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
 *
 * sajfkljalkf;jaojfioahuiahfsihaiufbsaibi
 *
 * */

@RequestMapping("shijuan")
@RestController
public class ShiJuanController {
    @Autowired
    private ShiJuanService shiJuanService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("addShiJuanInfo")
    public ResponseResult addShiJuanInfo(HttpServletRequest request) {
        ResponseResult responseResult = ResponseResult.getResponseResult();
        Map<String, Object> parameterMap = CommonUtils.getParamsJsonMap(request);
        try {
            parameterMap.put("id", UID.getUUIDOrder());
            shiJuanService.addShiJuanInfo(parameterMap, redisTemplate);
            responseResult.setSuccess("ok");
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setError("error");
        }
        return responseResult;
    }

    @RequestMapping("getShijuanList")
    public ResponseResult getShijuanList(HttpServletRequest httpServletRequest) {
        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap(httpServletRequest);
        Page<Map<String, Object>> page = new Page<>();
        ResponseResult responseResult = ResponseResult.getResponseResult();
        Page.setPageInfo(page, paramsJsonMap);
        //设置查询参数
        page.setParams(paramsJsonMap);
        Page.setPageInfo(page, paramsJsonMap);
        shiJuanService.getShijuanList(page);
        responseResult.setResult(page);
        return responseResult;
    }

    //删除试卷分类
    @RequestMapping("deleteShijuan")
    public ResponseResult deleteShijuan(HttpServletRequest request) {
        ResponseResult responseResult = ResponseResult.getResponseResult();
        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap(request);
        try {
            shiJuanService.deleteShijuan(paramsJsonMap);
            responseResult.setSuccess("ok");
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setError("error");
        }
        return responseResult;
    }

    //修改试卷分类
    @RequestMapping("updateShijuan")
    public ResponseResult updateShijuan(HttpServletRequest request) {
        ResponseResult responseResult = ResponseResult.getResponseResult();
        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap(request);
        shiJuanService.updateShijuan(paramsJsonMap);
        responseResult.setResult("ok");
        return responseResult;
    }

    //获取试卷信息

}