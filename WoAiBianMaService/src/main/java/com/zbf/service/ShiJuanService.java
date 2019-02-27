package com.zbf.service;

import com.zbf.core.page.Page;
import com.zbf.enmu.MyRedisKey;
import com.zbf.mapper.ShiJuanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class ShiJuanService {
    @Autowired
    private ShiJuanMapper shiJuanMapper;

    @Transactional
    public int addShiJuanInfo(Map<String,Object> map, RedisTemplate redisTemplate){
        redisTemplate.opsForList().rightPush(MyRedisKey.TIKU.getKey(),map);
        return shiJuanMapper.addShiJuanInfo(map);
    }

    public  void getShijuanList(Page<Map<String,Object>> page){
        List<Map<String,Object>> list=shiJuanMapper.getShijuanList(page);
        page.setResultList(list);
    }

    public void deleteShijuan(Map<String, Object> map) {
        shiJuanMapper.deleteShijuan(map);
    }

    @Transactional
    public  void  updateShijuan(Map<String,Object> map){
        shiJuanMapper.updateShijuan(map);

    }



}
