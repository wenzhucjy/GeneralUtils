package com.github.mysite.web.wenzhu.common.uuid;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * description:订单序列号
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:03
 */
public class SnCode implements Serializable {

    /**
     * 最大值
     */
    private int maxCode;
    /**
     * 业务类型，可定义为ENUM
     */
    private String bussinessType;


    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public int getMaxCode() {
        return maxCode;
    }

    public void setMaxCode(int maxCode) {
        this.maxCode = maxCode;
    }

    /**
     * 带参数构造方法
     * @param maxCode       最大数
     * @param bussinessType 业务类型
     */
    public SnCode(int maxCode, String bussinessType) {
        this.maxCode = maxCode;
        this.bussinessType = bussinessType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("maxCode", maxCode)
                .add("bussinessType", bussinessType)
                .toString();
    }

}
