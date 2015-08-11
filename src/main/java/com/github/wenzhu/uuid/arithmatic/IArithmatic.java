package com.github.wenzhu.uuid.arithmatic;

/**
 * description:运算接口
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:58
 */
public interface IArithmatic {
    /**
     * 通过业务类型计算出sncode
     * @param businessType  业务类型
     * @param cacheNum      缓存数量
     * @return
     */
    public String genSnCode(String businessType, int cacheNum);
}

