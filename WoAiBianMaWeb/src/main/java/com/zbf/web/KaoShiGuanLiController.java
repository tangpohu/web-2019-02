package com.zbf.web;

import com.alibaba.fastjson.JSON;
import com.zbf.common.ResponseResult;
import com.zbf.core.CommonUtils;
import com.zbf.enmu.MyRedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequestMapping("kaoshi")
@RestController
public class KaoShiGuanLiController {

    @Autowired
    private RedisTemplate redisTemplate;

    //从redis中获取数据
    @RequestMapping("getShiJuanData")
    public ResponseResult getShiJuanData(HttpServletRequest request){
        //获取responseResult
        ResponseResult responseResult=ResponseResult.getResponseResult();
        //从redis中加载数据
        List<Map<String,Object>> range = redisTemplate.opsForList().range(MyRedisKey.SHI_JUAN.getKey(), 0, -1);
        //将加载的数据加入到responseResult
        responseResult.setResult(range);
        return  responseResult;

    }

    //  试卷根据分数区间段  进行统计数据
    @RequestMapping("getScoreRangData")
    public  ResponseResult getScoreRangData(HttpServletRequest request){
        ResponseResult responseResult=ResponseResult.getResponseResult();
        //获取参数
        Map<String, Object> parameterMap = CommonUtils.getParameterMap(request);
        //区间值的开始部分
        List<Integer> fenshu1 = JSON.parseObject(parameterMap.get("fenshu1").toString(), List.class);
        //区间值的结束部分
        List<Integer> fenshu2 = JSON.parseObject(parameterMap.get("fenshu2").toString(), List.class);
        List<Map<String,Object>> listbingdata=new ArrayList<>();
        List<String> listbingdatatext=new ArrayList<>();
        for (int i=0;i<fenshu1.size();i++){
            Set shijuanid = redisTemplate.opsForZSet().rangeByScore(parameterMap.get("shijuanid").toString(), fenshu1.get(i), fenshu2.get(i));
            Map<String,Object> map=new HashMap<>();
            String name=""+fenshu1.get(i)+"-"+fenshu2.get(i);
            map.put("name",name);
            map.put("value",shijuanid.size());
            listbingdata.add(map);
            listbingdatatext.add(name);
        }
        Map<String,Object> mapdata=new HashMap<>();
        mapdata.put("listbingdata",listbingdata);
        mapdata.put("listbingdatatext",listbingdatatext);
        responseResult.setResult(mapdata);
        return  responseResult;
    }
}
