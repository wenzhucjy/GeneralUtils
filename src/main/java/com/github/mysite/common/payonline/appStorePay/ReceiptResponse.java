package com.github.mysite.common.payonline.appStorePay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;

/**
 * description:  App Store 验证结果封装对象
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-12-23 11:30
 */
public class ReceiptResponse {
    //是否成功，0表示成功
    private boolean success;

    //返回的receipt 封装数据
    private ReceiptData receiptData;

    public ReceiptData getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(ReceiptData receiptData) {
        this.receiptData = receiptData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public static ReceiptResponse parseResponse(JSONObject jsonObject) {
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setSuccess(jsonObject.getInteger("status") == 0);

        JSONObject object = jsonObject.getJSONObject("receipt");
        //把JSON对象转换为ReceiptData对象
        ReceiptData receiptData = JSON.parseObject(object.toJSONString(), ReceiptData.class);
        receiptResponse.setReceiptData(receiptData);
        return receiptResponse;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Receipt response")
                .omitNullValues()
                .add("success", this.success)
                .add("receipt data", receiptData)
                .toString();
    }

}
