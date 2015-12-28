package com.github.mysite.common.payonline.ccb;

import com.github.mysite.common.payonline.util.RequestHelper;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * description:封装CCB 支付请求参数 —— 使用的是防钓鱼接口，另外还有MD5接口，密钥接口
 *
 * @author :    jy.chen
 * @version :  1.0
 * @since  : 2015-12-03 17:46
 */
public class CCBSendData {
    
    public static final String CCB_GATE_WAY = "https://ibsbjstar.ccb.com.cn/app/ccbMain";   //建行支付网关地址

	private String		merchantId		= "";		// 商户代码(客户号) CHAR(21)
	private String		b2cPosId		= "";		// 商户柜台代码 CHAR(9)由建行统一分配,千分之五税率 柜员号
	private String		b2bPosId		= "";		// 商户柜台代码 CHAR(9)由建行统一分配,10元手续费用 柜员号
	private String		branchId		= "";		// 分行代码 CHAR(9)
	private String		b2cTxCode		= "520100"; // 由建行统一分配为 520100-千分之五税率
	private String		b2bTxCode		= "690401"; // 由建行统一分配为 690401-10元手续费用
	private String		curCode			= "01";	    // 币种 CHAR(2) 缺省为01－人民币
	private String		orderId;					// 定单号 CHAR(30)由商户提供,最长30位,按实际长度给出
	private BigDecimal	payment;					// 付款金额 NUMBER(16,2)由商户提供，按实际金额给出
    private String      remark1;                    // 备注1 CHAR(32) 网银不处理,直接传到城综网													
	private String		remark2;					// 备注2 CHAR(32)网银不处理,直接传到城综网
	private String		type			= "1";		// 接口类型(不可更改)
	private String		gateWay;					// 网关类型
	private String		clientIp;					// 客户端IP地址
	private String		regInfo;					// 注册信息
	private String		pub32TR2;					// 公钥后30位
	private String		proInfo;					// 商品信息
	private String		referer;					// 商户域名
	private String		strMethod		= "post";	// post 方法提交接入CCB网关
	private String		b2cRsaPublicKey	= "";		// B2C 公钥后30位
	private String		b2bRsaPublicKey	= "";		// B2B 公钥后30位

    /**
     * 加密B2C MAC 校验域,采用标准MD5算法
     *
     * @param payType   交易类型 B2B or B2C
     * @param isCompare 用于区分金额是否大于2000元(即税率大于10元)标志位
     * @return
     */
    public String macB2CUrl(Integer payType, boolean isCompare) {
        // 防钓鱼接口需多添加以下参数 TYPE PUB GATEWAY CLIENTIP REGINFO PROINFO REFERER
        return StringUtils.join("MERCHANTID=", this.merchantId, "&POSID=", 
                isCompare ? this.b2bPosId : this.b2cPosId, "&BRANCHID=", this.branchId, 
                "&ORDERID=", this.orderId, "&PAYMENT=",this.payment.toString(), "&CURCODE=", this.curCode, 
                "&TXCODE=",payType == CCBBackData.CCBPaymentType[0] ? this.b2bTxCode : this.b2cTxCode, 
                "&REMARK1=", this.remark1,"&REMARK2=", this.remark2, "&TYPE=", this.type, 
                "&PUB=", getPub32TR2(isCompare), "&GATEWAY=", this.gateWay, "&CLIENTIP=", this.clientIp, 
                "&REGINFO=", this.regInfo, "&PROINFO=", this.proInfo,"&REFERER=", this.referer);
    }

    /**
     * 封装CCB请求参数
     *
     * @param payType   类型 B2B or B2C
     * @param isCompare 若isCompare 为 true 表示，订单金额手续费超过10元，即 柜员号为 B2B柜员号， 若 isCompare 为
     *                  false 表示，订单金额手续费没超过10元，即柜员号为 B2C 柜员号
     * @return
     */
    public String bulidCCBRequestUrl(int payType, boolean isCompare) {
        Map<String, String> sParaTemp = new LinkedHashMap<>();
        // &TYPE=1&GATEWAY=&CLIENTIP=&REGINFO=&PROINFO=&REFERER=&MAC=2bca98eb642e4bfdebff573afae73884
        sParaTemp.put("MERCHANTID", this.merchantId);
        // isCompare 用于区分金额是否大于2000元
        sParaTemp.put("POSID", isCompare ? this.b2bPosId : this.b2cPosId);
        sParaTemp.put("BRANCHID", this.branchId);
        sParaTemp.put("ORDERID", this.orderId);
        sParaTemp.put("PAYMENT", this.payment.toString());
        sParaTemp.put("CURCODE", this.curCode);
        sParaTemp.put("TXCODE", payType == CCBBackData.CCBPaymentType[0] ? this.b2bTxCode : this.b2cTxCode);
        // 当对象为null时传递的是空字符串而不是null
        sParaTemp.put("REMARK1", Strings.nullToEmpty(this.remark1));
        sParaTemp.put("REMARK2", Strings.nullToEmpty(this.remark2));
        sParaTemp.put("TYPE", this.type);
        sParaTemp.put("GATEWAY", Strings.nullToEmpty(this.gateWay));
        sParaTemp.put("CLIENTIP",Strings.nullToEmpty(this.clientIp));
        sParaTemp.put("REGINFO", Strings.nullToEmpty(this.regInfo));
        sParaTemp.put("PROINFO", Strings.nullToEmpty(this.proInfo));
        sParaTemp.put("REFERER", Strings.nullToEmpty(this.referer));
        sParaTemp.put("MAC", "");
        sParaTemp.put("macSrc", macB2CUrl(payType, isCompare));
        // 重新组装 form 表单数据
        return RequestHelper.buildRequestPara(sParaTemp, strMethod, CCB_GATE_WAY);
    }

    /**
     * B2B —— 构造方法
     *
     * @param orderId 订单号
     * @param payment 金额
     * @param remark1 备注一
     * @param remark2 备注二
     */
    public CCBSendData(String orderId, BigDecimal payment, String remark1, String remark2) {
        this.orderId = orderId;
        this.payment = payment;
        this.remark1 = remark1;
        this.remark2 = remark2;
    }

    /**
     * 防钓鱼接口 —— 构造方法
     *
     * @param orderId  订单号
     * @param payment  金额
     * @param remark1  备注一
     * @param remark2  备注二
     * @param gateWay  网关类型
     * @param clientIp 客户端IP
     * @param regInfo  注册信息
     * @param proInfo  商品信息
     * @param referer  商户域名
     */
    public CCBSendData(String orderId, BigDecimal payment, String remark1, String remark2, String gateWay,
                       String clientIp, String regInfo, String proInfo, String referer) {
        this.orderId = orderId;
        this.payment = payment;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.gateWay = gateWay;
        this.clientIp = clientIp;
        this.regInfo = regInfo;
        this.proInfo = proInfo;
        this.referer = referer;
    }

    /**
     * 获取公钥后30位
     *
     * @param isCompare 区分金额税率是否大于10元的标志位
     * @return
     */
    private String getPub32TR2(boolean isCompare) {
        String key = isCompare ? this.b2bRsaPublicKey : this.b2cRsaPublicKey;
        this.pub32TR2 = StringUtils.substring(key, key.length() - 30, key.length());
        return pub32TR2;
    }
    

}
