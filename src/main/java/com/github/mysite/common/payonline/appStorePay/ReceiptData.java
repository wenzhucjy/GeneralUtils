package com.github.mysite.common.payonline.appStorePay;

import com.google.common.base.MoreObjects;

/**
 * description: App Store 返回收据的验证结果，demo如下<br>
 *<pre>
 {
 "status" : 0,
 "receipt" : {
     "product_id":"com.anyfish.fishing_rod_husband.productid",
     "original_purchase_date_ms":"1435703763224",
     "purchase_date_pst":"2015-06-30 15:36:03 America/Los_Angeles",
     "original_purchase_date":"2015-06-30 22:36:03 Etc/GMT",
     "bvrs":"2.0",
     "transaction_id":"1000000161490215",
     "original_purchase_date_pst":"2015-06-30 15:36:03 America/Los_Angeles",
     "unique_identifier":"0a3d8a90e4e590ab48c86ef1bc83799149937ca1",
     "original_transaction_id":"1000000161490215",
     "item_id":"994842145",
     "version_external_identifier":"705002663",
     "purchase_date_ms":"1435703763224",
     "quantity":"1",
     "purchase_date":"2015-06-30 22:36:03 Etc/GMT",
     "bid":"anyfish.AnyfishApp",
     "unique_vendor_identifier":"26E84DEB-1727-4751-81C3-A65C2E4C70F9"
     }
 }
 </pre>
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-12-23 11:30
 */
public class ReceiptData {

    private String product_id;
    private String original_purchase_date_ms;
    private String purchase_date_pst;
    private String original_purchase_date;
    private String bvrs;
    private String transaction_id;
    private String original_purchase_date_pst;
    private String unique_identifier;
    private String original_transaction_id;
    private String item_id;
    private String version_external_identifier;
    private String purchase_date_ms;
    private Integer quantity;
    private String purchase_date;
    private String bid;
    private String unique_vendor_identifier;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOriginal_purchase_date_ms() {
        return original_purchase_date_ms;
    }

    public void setOriginal_purchase_date_ms(String original_purchase_date_ms) {
        this.original_purchase_date_ms = original_purchase_date_ms;
    }

    public String getPurchase_date_pst() {
        return purchase_date_pst;
    }

    public void setPurchase_date_pst(String purchase_date_pst) {
        this.purchase_date_pst = purchase_date_pst;
    }

    public String getOriginal_purchase_date() {
        return original_purchase_date;
    }

    public void setOriginal_purchase_date(String original_purchase_date) {
        this.original_purchase_date = original_purchase_date;
    }

    public String getBvrs() {
        return bvrs;
    }

    public void setBvrs(String bvrs) {
        this.bvrs = bvrs;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOriginal_purchase_date_pst() {
        return original_purchase_date_pst;
    }

    public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
        this.original_purchase_date_pst = original_purchase_date_pst;
    }

    public String getUnique_identifier() {
        return unique_identifier;
    }

    public void setUnique_identifier(String unique_identifier) {
        this.unique_identifier = unique_identifier;
    }

    public String getOriginal_transaction_id() {
        return original_transaction_id;
    }

    public void setOriginal_transaction_id(String original_transaction_id) {
        this.original_transaction_id = original_transaction_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getVersion_external_identifier() {
        return version_external_identifier;
    }

    public void setVersion_external_identifier(String version_external_identifier) {
        this.version_external_identifier = version_external_identifier;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPurchase_date_ms() {
        return purchase_date_ms;
    }

    public void setPurchase_date_ms(String purchase_date_ms) {
        this.purchase_date_ms = purchase_date_ms;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getUnique_vendor_identifier() {
        return unique_vendor_identifier;
    }

    public void setUnique_vendor_identifier(String unique_vendor_identifier) {
        this.unique_vendor_identifier = unique_vendor_identifier;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("返回receipt参数")
                .omitNullValues()
                .add("product_id", product_id)
                .add("original_purchase_date_ms", original_purchase_date_ms)
                .add("purchase_date_pst", purchase_date_pst)
                .add("original_purchase_date", original_purchase_date)
                .add("bvrs", bvrs)
                .add("transaction_id", transaction_id)
                .add("original_purchase_date_pst", original_purchase_date_pst)
                .add("unique_identifier", unique_identifier)
                .add("original_transaction_id", original_transaction_id)
                .add("item_id", item_id)
                .add("version_external_identifier", version_external_identifier)
                .add("quantity", quantity)
                .add("purchase_date_ms", purchase_date_ms)
                .add("purchase_date", purchase_date)
                .add("bid", bid)
                .add("unique_vendor_identifier", unique_vendor_identifier)
                .toString();
    }


}
