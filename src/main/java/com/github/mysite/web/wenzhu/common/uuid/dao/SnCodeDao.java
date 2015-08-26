package com.github.mysite.web.wenzhu.common.uuid.dao;

import com.github.mysite.web.wenzhu.common.uuid.SnCode;

import java.util.Map;

/**
 * description:订单序列号接口
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:44
 */
public interface SnCodeDao {

    /**
     * 获取SnCode
     * @param businessType 业务类型
     * @return  SnCode
     */
    public SnCode getByBusinessType(String businessType);

    /**
     * 更新SnCode
     * @param snCode SnCode
     */
    public void updateSnCode(SnCode snCode);

    /**
     * 创建SnCode
     * @param snCode SnCode
     */
    public void createSnCode(SnCode snCode);


    public Map<String, Integer> getAll();
}
