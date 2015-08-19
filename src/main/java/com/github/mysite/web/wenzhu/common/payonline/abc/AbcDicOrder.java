package com.github.mysite.web.wenzhu.common.payonline.abc;

import com.abc.trustpay.client.Constants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * description:中国农业银行B2C支付订单
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:35
 */
public class AbcDicOrder {
	
	/**
	 * JSON 
	 */
	public static final String MEDIA_TYPE_APPLICATION_JSON_VALUE_UTF8 = "application/json;charset=UTF-8";
	/**
	 * 时间的格式
	 */
	public static final String[] DATE_PATTERNS = { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };


	private String PayTypeID = "ImmediatePay";				//交易类型  必须设定  ，ImmediatePay：直接支付   PreAuthPay：预授权支付 DividedPay：分期支付
	private String OrderDate;								//订单日期 必须设定 ， YYYY/mm/DD 
	private String OrderTime;								//订单时间 必须设定 ， HH:MM:SS 
	private String ExpiredDate;								//设定订单保存时间,精确到秒YYYYmmDDHHMMSS  非必须
	private String CurrencyCode = "156";					//交易币种 必须设定， 156： 人民币 
	private String OrderNo;									//订单编号 必须设定
	private String OrderAmount;								//交易金额 必须设定
	private String Fee;										//手续费金额 非必须
	private String OrderURL;								//订单说明 非必须 
	private String ReceiverAddress;							//收货地址 非必须 
	private String InstallmentMark = "0";					//分期标识 必须设定， 1：分期；0：不分期 
	private String InstallmentCode;							//分期代码 分期标识为“1”时必须设定
	private String InstallmentNum;							//分期期数  分期标识为“1”时必须设定， 0-99
	private String CommodityType = "0101";					//商品种类  必须设定
																//充值类 0101:支付账户充值
																//消费类 0201:虚拟类,0202:传统类,0203:实名类
																//转账类 0301:本行转账,0302:他行转账
																//缴费类 0401:水费,0402:电费,0403:煤气费,0404:有线电视费,0405:通讯费,
																//0406:物业费,0407:保险费,0408:行政费用,0409:税费,0410:学费,0499:其他
																//理财类 0501:基金,0502:理财产品,0599:其他
	private String BuyIP;				   				    //客户 IP 非必须
	private String OrderDesc;								//订单说明  非必须
	private String orderTimeoutDate;						//订单有效期  非必须
	
	public String getPayTypeID() {
		return PayTypeID;
	}
	public void setPayTypeID(String payTypeID) {
		PayTypeID = payTypeID;
	}
	public String getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}
	public String getOrderTime() {
		return OrderTime;
	}
	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}
	public String getExpiredDate() {
		return ExpiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		ExpiredDate = expiredDate;
	}
	public String getCurrencyCode() {
		return CurrencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getOrderAmount() {
		return OrderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		OrderAmount = orderAmount;
	}
	public String getFee() {
		return Fee;
	}
	public void setFee(String fee) {
		Fee = fee;
	}
	public String getOrderURL() {
		return OrderURL;
	}
	public void setOrderURL(String orderURL) {
		OrderURL = orderURL;
	}
	public String getReceiverAddress() {
		return ReceiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		ReceiverAddress = receiverAddress;
	}
	public String getInstallmentMark() {
		return InstallmentMark;
	}
	public void setInstallmentMark(String installmentMark) {
		InstallmentMark = installmentMark;
	}
	public String getInstallmentCode() {
		return InstallmentCode;
	}
	public void setInstallmentCode(String installmentCode) {
		InstallmentCode = installmentCode;
	}
	public String getInstallmentNum() {
		return InstallmentNum;
	}
	public void setInstallmentNum(String installmentNum) {
		InstallmentNum = installmentNum;
	}
	public String getCommodityType() {
		return CommodityType;
	}
	public void setCommodityType(String commodityType) {
		CommodityType = commodityType;
	}
	public String getBuyIP() {
		return BuyIP;
	}
	public void setBuyIP(String buyIP) {
		BuyIP = buyIP;
	}
	public String getOrderDesc() {
		return OrderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		OrderDesc = orderDesc;
	}
	public String getOrderTimeoutDate() {
		return orderTimeoutDate;
	}
	public void setOrderTimeoutDate(String orderTimeoutDate) {
		this.orderTimeoutDate = orderTimeoutDate;
	}
	
	/**
	 * 带参数构造方法
	 * @param orderNo	订单号
	 * @param orderAmount	金额
	 * @param orderDate		日期
	 * @param orderTime		时间
	 * @param orderURL		URL
	 * @param orderDesc		备注
	 * @param orderTimeoutDate	过期时间
	 */
	public AbcDicOrder(String orderNo, String orderAmount, String orderDate, String orderTime, String orderURL, String orderDesc,
			String orderTimeoutDate) {
		this.OrderNo = orderNo;
		this.OrderAmount = orderAmount;
		this.OrderDate = orderDate;
		this.OrderTime = orderTime;
		this.OrderURL = orderURL;
		this.OrderDesc = orderDesc;
		this.orderTimeoutDate = orderTimeoutDate;
	}
	
	/**
	 * 默认的构造方法
	 */
	public AbcDicOrder() {
		super();
	}
	
	public Map<String,String> bulidDicOrder(){
		Map<String,String> retMap = Maps.newLinkedHashMap();
		retMap.put("PayTypeID", Strings.nullToEmpty(this.PayTypeID));                   //设定交易类型
		retMap.put("OrderDate", Strings.nullToEmpty(this.OrderDate));                   //设定订单日期 （必要信息 - YYYY/MM/DD）
		retMap.put("OrderTime", Strings.nullToEmpty(this.OrderTime));                   //设定订单时间 （必要信息 - HH:MM:SS）
		retMap.put("orderTimeoutDate", Strings.nullToEmpty(this.orderTimeoutDate));     //设定订单有效期
		retMap.put("OrderNo", Strings.nullToEmpty(this.OrderNo));                       //设定订单编号 （必要信息）
		retMap.put("CurrencyCode", Strings.nullToEmpty(this.CurrencyCode));             //设定交易币种
		retMap.put("OrderAmount", Strings.nullToEmpty(this.OrderAmount));      		   //设定交易金额
		retMap.put("Fee", Strings.nullToEmpty(this.Fee));                               //设定手续费金额
		retMap.put("OrderDesc", Strings.nullToEmpty(this.OrderDesc));                   //设定订单说明
		retMap.put("OrderURL", Strings.nullToEmpty(this.OrderURL));                     //设定订单地址
		retMap.put("ReceiverAddress", Strings.nullToEmpty(this.ReceiverAddress));       //收货地址
		retMap.put("InstallmentMark", Strings.nullToEmpty(this.InstallmentMark));       //分期标识
		if (StringUtils.equals(this.InstallmentMark, "1") && StringUtils.equals(Constants.PAY_TYPE_INSTALLMENTPAY, this.PayTypeID)) {
		    retMap.put("InstallmentCode", Strings.nullToEmpty(this.InstallmentCode));   //设定分期代码
		    retMap.put("InstallmentNum", Strings.nullToEmpty(this.InstallmentNum));     //设定分期期数
		}
		retMap.put("CommodityType", Strings.nullToEmpty(this.CommodityType));           //设置商品种类
		retMap.put("BuyIP", Strings.nullToEmpty(this.BuyIP));                           //IP
		retMap.put("ExpiredDate", Strings.nullToEmpty(this.ExpiredDate));               //设定订单保存时间
		return retMap;
	}
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("orderNo", this.OrderNo)
				.add("OrderAmount", this.OrderAmount)
				.add("OrderDate", this.OrderDate)
				.add("OrderTime", OrderTime)
				.add("OrderURL", OrderURL)
				.add("OrderDesc", OrderDesc)
				.add("orderTimeoutDate", orderTimeoutDate)
				.toString();
	}
	
}
