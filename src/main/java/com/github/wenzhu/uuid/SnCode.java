package com.github.wenzhu.uuid;

import com.google.common.base.MoreObjects;

/**
 * description:订单序列号
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:03
 */
public class SnCode {

    /**
     * 最大值
     */
    private int maxCode;
    /**
     * 业务类型，可定义为ENUM
     */
    private String bussinessType;
    /**
     * 日期，格式为yyyy-MM-dd
     */
    private String modifyDate;


    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
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
     * @param modifyDate    日期
     */
    public SnCode(int maxCode, String bussinessType, String modifyDate) {
        this.maxCode = maxCode;
        this.bussinessType = bussinessType;
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("maxCode", maxCode)
                .add("bussinessType", bussinessType)
                .add("modifyDate", modifyDate)
                .toString();
    }

}
