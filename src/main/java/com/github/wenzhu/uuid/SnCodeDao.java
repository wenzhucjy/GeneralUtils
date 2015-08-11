package com.github.wenzhu.uuid;

/**
 * description:订单序列号接口
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:44
 */
public interface SnCodeDao {

    public SnCode getByBusinessType(String businessType,String modifyDate);

    public void updateUuid(SnCode snCode);

    public void createUuid(SnCode snCode);
}
