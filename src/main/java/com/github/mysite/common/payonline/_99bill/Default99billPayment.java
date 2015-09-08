package com.github.mysite.common.payonline._99bill;

import com.github.mysite.common.jodatime.DateHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * description: 快钱支付测试用例
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-09-08 17:05
 */
public class Default99billPayment extends _99billBaseAction {

    /**
     * 时间的格式
     */
    private static final String DATE_PATTERNS = "YyyyMMddHHmmss";

    /**
     * 快钱支付测试用例
     * @param request HttpServletRequest
     * @param type 订单业务类型 
     * @return 快钱支付网关页面
     */
    public String accessGateWayPage(HttpServletRequest request,String type) {

        String orderId = System.currentTimeMillis() + "";
        String orderAmount = "100";//以分为单位
        _99BillSendData sendData = new _99BillSendData(orderId, orderAmount, DateHelper.formateDate( new Date(), DATE_PATTERNS), "测试消费清单", "");
        return super.access99BillGateway(request, sendData, !StringUtils.equals("mobile", type));
    }


    @Override
    protected String bussinessDealCode(_99BillBackData backData) {
        return "";
    }
}
