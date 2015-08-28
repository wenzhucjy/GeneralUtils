package com.github.mysite.common.payonline.ccb;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;

/**
 * description:封装CCB交易的返回参数
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:46
 */
public class CCBBackData {

    // 以下是B2C业务返回参数
    private String POSID;            //商户柜台代码	CHAR(9)	从商户传送的信息中获得
    private String BRANCHID;        //分行代码	CHAR(9)	从商户传送的信息中获得	
    private String ORDERID;            //定单号	CHAR(30)	从商户传送的信息中获得
    private String PAYMENT;            //付款金额	NUMBER(16,2)	从商户传送的信息中获得
    private String CURCODE;            //币种	CHAR(2)	从商户传送的信息中获得
    private String REMARK1;            //备注一	CHAR(32)	从商户传送的信息中获得
    private String REMARK2;            //备注二	CHAR(32)	从商户传送的信息中获得
    private String ACC_TYPE;        //帐户类型	CHAR(2)	从银行信息中获得(服务器通知才有此参数)
    private String SUCCESS;            //成功标志	CHAR(1)	成功时返回Y
    private String SIGN;            //数字签名	CHAR(256)	
    private String TYPE;            //接口类型 
    private String REFERER;            //商户域名
    private String CLIENTIP;        //客户端IP地址
    //以上是B2C业务返回参数

    //以下是B2B业务返回参数(包括 REMERK1,REMARK2)
    private String MPOSID;            //商户柜台代码	CHAR(9)	从商户传送的信息中获得
    private String ORDER_NUMBER;    //定单号	CHAR(30)	从商户传送的信息中获得
    private String CUST_ID;            //付款客户号	CHAR(20)	从商户传送的信息中获得
    private String ACC_NO;            //付款账号	CHAR(32)	从网银中心获得
    private String ACC_NAME;        //付款账户名称	CHAR(40)	从网银中心获得
    private String AMOUNT;            //付款金额	NUMBER(16,2)	从网银中心获得
    private String STATUS;            //支付结果	CHAR(1)	从网银中心获得	- 	1：通过 0：不通过,2：支付成功,3：支付成功,4：支付成功,5：交易失败,6：交易不确定
    private String TRAN_FLAG;        //付款方式	CHAR(1)	从网银中心获得 - N：对公账户支付
    private String TRAN_TIME;        //交易时间	CHAR(12)	从网银中心获得
    private String BRANCH_NAME;        //付款分行名称	CHAR(40)	从网银中心获得
    private String SIGNSTRING;        //数字签名加密串	CHAR(256)	从网银中心获得
    private String CHECKOK;            //最后一级复核员是否审核通过	CHAR(1)	从网银中心获得，1：通过 0：不通过
    //以上是B2B业务返回参数

    public static String[] b2bPayStatus = new String[]{"2", "3", "4"};    //B2B交易成功，STATUS 的返回值
    public static String b2cPayStatus = "Y";                            //B2C交易成功，SUCCESS 的返回值
    public static Integer[] CCBPaymentType = new Integer[]{1, 2};        //CCB交易类型，1-B2B 2-B2C

    /**
     * B2B商户验签的域(不含字段名称，字段内容为空的就直接拼接空串)
     *
     * @return
     */
    public String joinOnlineB2BPayParam(CCBBackData backData) {
        String str = StringUtils.join(backData.MPOSID, backData.ORDER_NUMBER, backData.CUST_ID, backData.ACC_NO, backData.ACC_NAME, backData
                        .AMOUNT, backData.STATUS, backData.REMARK1, backData.REMARK2,
                backData.TRAN_FLAG, backData.TRAN_TIME, backData.BRANCH_NAME);
        return str;
    }

    /**
     * B2C商户验签的域(服务器通知，页面通知)
     *
     * @param backData CCB返回参数，需特别注意的是除了签名以外的其他参数都需要验证签名
     * @param isAsync  是否服务器通知,若为服务器通知则多&ACC_TYPE= 参数
     * @return
     */
    public String joinOnlineB2CPayParam(CCBBackData backData, boolean isAsync) {
        String str = StringUtils.join("POSID=", backData.POSID, "&BRANCHID=", backData.BRANCHID, "&ORDERID=", backData.ORDERID, "&PAYMENT=", backData.PAYMENT, "&CURCODE=", backData.CURCODE, "&REMARK1=", backData.REMARK1, "&REMARK2=", backData.REMARK2
                , isAsync ? String.format("&ACC_TYPE=%s", backData.ACC_TYPE) : "", "&SUCCESS=", backData.SUCCESS, "&TYPE=", backData.TYPE, "&REFERER=", backData.REFERER, "&CLIENTIP=", backData.CLIENTIP);
        return str;
    }

    // getter / setter method ..
    public String getMPOSID() {
        return MPOSID;
    }

    public String getPOSID() {
        return POSID;
    }

    public void setPOSID(String pOSID) {
        POSID = pOSID;
    }

    public String getBRANCHID() {
        return BRANCHID;
    }

    public void setBRANCHID(String bRANCHID) {
        BRANCHID = bRANCHID;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String oRDERID) {
        ORDERID = oRDERID;
    }

    public String getPAYMENT() {
        return PAYMENT;
    }

    public void setPAYMENT(String pAYMENT) {
        PAYMENT = pAYMENT;
    }

    public String getCURCODE() {
        return CURCODE;
    }

    public void setCURCODE(String cURCODE) {
        CURCODE = cURCODE;
    }

    public String getSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(String sUCCESS) {
        SUCCESS = sUCCESS;
    }

    public String getACC_TYPE() {
        return ACC_TYPE;
    }

    public void setACC_TYPE(String aCC_TYPE) {
        ACC_TYPE = aCC_TYPE;
    }

    public String getSIGN() {
        return SIGN;
    }

    public void setSIGN(String sIGN) {
        SIGN = sIGN;
    }

    public void setMPOSID(String mPOSID) {
        MPOSID = mPOSID;
    }

    public String getORDER_NUMBER() {
        return ORDER_NUMBER;
    }

    public void setORDER_NUMBER(String oRDER_NUMBER) {
        ORDER_NUMBER = oRDER_NUMBER;
    }

    public String getCUST_ID() {
        return CUST_ID;
    }

    public void setCUST_ID(String cUST_ID) {
        CUST_ID = cUST_ID;
    }

    public String getACC_NO() {
        return ACC_NO;
    }

    public void setACC_NO(String aCC_NO) {
        ACC_NO = aCC_NO;
    }

    public String getACC_NAME() {
        return ACC_NAME;
    }

    public void setACC_NAME(String aCC_NAME) {
        ACC_NAME = aCC_NAME;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        AMOUNT = aMOUNT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }

    public String getREMARK1() {
        return REMARK1;
    }

    public void setREMARK1(String rEMARK1) {
        REMARK1 = rEMARK1;
    }

    public String getREMARK2() {
        return REMARK2;
    }

    public void setREMARK2(String rEMARK2) {
        REMARK2 = rEMARK2;
    }

    public String getTRAN_FLAG() {
        return TRAN_FLAG;
    }

    public void setTRAN_FLAG(String tRAN_FLAG) {
        TRAN_FLAG = tRAN_FLAG;
    }

    public String getTRAN_TIME() {
        return TRAN_TIME;
    }

    public void setTRAN_TIME(String tRAN_TIME) {
        TRAN_TIME = tRAN_TIME;
    }

    public String getBRANCH_NAME() {
        return BRANCH_NAME;
    }

    public void setBRANCH_NAME(String bRANCH_NAME) {
        BRANCH_NAME = bRANCH_NAME;
    }

    public String getSIGNSTRING() {
        return SIGNSTRING;
    }

    public void setSIGNSTRING(String sIGNSTRING) {
        SIGNSTRING = sIGNSTRING;
    }

    public String getCHECKOK() {
        return CHECKOK;
    }

    public void setCHECKOK(String cHECKOK) {
        CHECKOK = cHECKOK;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String tYPE) {
        TYPE = tYPE;
    }

    public String getREFERER() {
        return REFERER;
    }

    public void setREFERER(String rEFERER) {
        REFERER = rEFERER;
    }

    public String getCLIENTIP() {
        return CLIENTIP;
    }

    public void setCLIENTIP(String cLIENTIP) {
        CLIENTIP = cLIENTIP;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("POSID", POSID)
                .add("BRANCHID", BRANCHID)
                .add("ORDERID", ORDERID)
                .add("PAYMENT", PAYMENT)
                .add("CURCODE", CURCODE)
                .add("REMARK1", REMARK1)
                .add("REMARK2", REMARK2)
                .add("ACC_TYPE", ACC_TYPE)
                .add("SUCCESS", SUCCESS)
                .add("SIGN", SIGN)
                .add("TYPE", TYPE)
                .add("REFERER", REFERER)
                .add("CLIENTIP", CLIENTIP)
                .add("MPOSID", MPOSID)
                .add("ORDER_NUMBER", ORDER_NUMBER)
                .add("CUST_ID", CUST_ID)
                .add("ACC_NO", ACC_NO)
                .add("ACC_NAME", ACC_NAME)
                .add("AMOUNT", AMOUNT)
                .add("STATUS", STATUS)
                .add("TRAN_FLAG", TRAN_FLAG)
                .add("TRAN_TIME", TRAN_TIME)
                .add("BRANCH_NAME", BRANCH_NAME)
                .add("SIGNSTRING", SIGNSTRING)
                .add("CHECKOK", CHECKOK)
                .toString();
    }
}
