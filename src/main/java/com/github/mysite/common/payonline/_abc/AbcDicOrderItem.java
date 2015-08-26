package com.github.mysite.common.payonline._abc;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * description:中国农业银行支付订单项 —— 新电子商务接口V3.0.2
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:38
 */
public class AbcDicOrderItem {

	private String SubMerName;							//二级商户名称 非必须
	private String SubMerId;							//二级商户 MCC 码 非必须
	private String SubMerMCC;							//二级商户 MCC 码 非必须
	private String SubMerchantRemarks;					//二级商户备注项  非必须
	private String ProductID;							//商品代码  非必须
	private String ProductName;							//商品名称  必须设定
	private String UnitPrice;							//商品总价  非必须
	private String Qty;									//商品数量  非必须
	private String ProductRemarks;						//商品备注项  非必须
	private String ProductType;							//商品类型  非必须
	private String ProductDiscount;						//商品折扣  非必须
	private String ProductExpiredDate;					//商品有效期  非必须
	
	
	public String getSubMerName() {
		return SubMerName;
	}
	public void setSubMerName(String subMerName) {
		SubMerName = subMerName;
	}
	public String getSubMerId() {
		return SubMerId;
	}
	public void setSubMerId(String subMerId) {
		SubMerId = subMerId;
	}
	public String getSubMerMCC() {
		return SubMerMCC;
	}
	public void setSubMerMCC(String subMerMCC) {
		SubMerMCC = subMerMCC;
	}
	public String getSubMerchantRemarks() {
		return SubMerchantRemarks;
	}
	public void setSubMerchantRemarks(String subMerchantRemarks) {
		SubMerchantRemarks = subMerchantRemarks;
	}
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		UnitPrice = unitPrice;
	}
	public String getQty() {
		return Qty;
	}
	public void setQty(String qty) {
		Qty = qty;
	}
	public String getProductRemarks() {
		return ProductRemarks;
	}
	public void setProductRemarks(String productRemarks) {
		ProductRemarks = productRemarks;
	}
	public String getProductType() {
		return ProductType;
	}
	public void setProductType(String productType) {
		ProductType = productType;
	}
	public String getProductDiscount() {
		return ProductDiscount;
	}
	public void setProductDiscount(String productDiscount) {
		ProductDiscount = productDiscount;
	}
	public String getProductExpiredDate() {
		return ProductExpiredDate;
	}
	public void setProductExpiredDate(String productExpiredDate) {
		ProductExpiredDate = productExpiredDate;
	}
	
	/**
	 * 带参数构造方法
	 * @param productName  产品名称
	 * @param qty	产品数量
	 */
	public AbcDicOrderItem(String productName, String qty) {
		ProductName = productName;
		Qty = qty;
	}
	/**
	 * 默认的构造方法
	 */
	public AbcDicOrderItem() {
		super();
	}
	public Map<String,String> bulidAbcDicretMap(){
		Map<String,String> retMap = Maps.newLinkedHashMap();
		retMap.put("SubMerName", Strings.nullToEmpty(this.SubMerName));    //设定二级商户名称
		retMap.put("SubMerId", Strings.nullToEmpty(this.SubMerId));    //设定二级商户代码
		retMap.put("SubMerMCC", Strings.nullToEmpty(this.SubMerMCC));   //设定二级商户MCC码 
		retMap.put("SubMerchantRemarks", Strings.nullToEmpty(this.SubMerchantRemarks));   //二级商户备注项
		retMap.put("ProductID", Strings.nullToEmpty(this.ProductID));//商品代码，预留字段
		retMap.put("ProductName",Strings.nullToEmpty(this.ProductName));//商品名称
		retMap.put("UnitPrice", Strings.nullToEmpty(this.UnitPrice));//商品总价
		retMap.put("Qty", Strings.nullToEmpty(this.Qty));//商品数量
		retMap.put("ProductRemarks", Strings.nullToEmpty(this.ProductRemarks)); //商品备注项
		retMap.put("ProductType", Strings.nullToEmpty(this.ProductType));//商品类型
		retMap.put("ProductDiscount", Strings.nullToEmpty(this.ProductDiscount));//商品折扣
		retMap.put("ProductExpiredDate", Strings.nullToEmpty(this.ProductExpiredDate));//商品有效期
		return retMap;
	}
}
