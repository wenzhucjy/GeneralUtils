package com.github.wenzhu.uuid.arithmatic;

/**
 * description:算法接口
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
     * @return  返回运算结果的字符串
     */
    public String genMaxCode(String businessType, int cacheNum);
}

