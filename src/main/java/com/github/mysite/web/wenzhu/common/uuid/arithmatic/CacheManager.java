package com.github.mysite.web.wenzhu.common.uuid.arithmatic;

import com.github.mysite.web.wenzhu.common.uuid.dao.SnCodeFactory;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * description:用于管理缓存订单序列号与DB序列号的最大值
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 17:21
 */
public class CacheManager {

    private Map<String, Integer> mapDB = Maps.newHashMap();
    private Map<String, Integer> mapNow = Maps.newHashMap();

    private static CacheManager cm = new CacheManager();

    private CacheManager() {
        //init data
        mapDB = SnCodeFactory.generateSnCodeDao().getAll();
        mapNow = SnCodeFactory.generateSnCodeDao().getAll();
    }

    public static CacheManager getInstance() {
        return cm;
    }

    public Map<String, Integer> getMapDB() {
        return mapDB;
    }

    public Map<String, Integer> getMapNow() {
        return mapNow;
    }
}
