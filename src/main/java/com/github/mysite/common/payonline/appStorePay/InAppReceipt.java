package com.github.mysite.common.payonline.appStorePay;


/**
 * description:  Redis 缓存receiptData数据，用于验证二次提交相同receiptData，若Redis存在对应的receiptData，直接返回success
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-12-23 12:30
 */
public class InAppReceipt {

	private int id;				//
	private String timestamp;	//时间戳
	private String orderSn;		//关联订单号
	private String receiptData;	//receiptData数据
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getReceiptData() {
		return receiptData;
	}
	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}
	/**
	 * 带参数构造方法
	 * @param timestamp	时间戳
	 * @param orderSn	关联订单号
	 * @param receiptData	receipt 数据
	 */
	public InAppReceipt(String timestamp, String orderSn, String receiptData) {
		this.timestamp = timestamp;
		this.orderSn = orderSn;
		this.receiptData = receiptData;
	}
	/**
	 * 默认构造方法
	 */
	public InAppReceipt() {
		
	}
	
}
