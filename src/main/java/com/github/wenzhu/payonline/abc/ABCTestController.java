package com.github.wenzhu.payonline.abc;

import com.abc.trustpay.client.MerchantConfig;
import com.abc.trustpay.client.TrxException;
import com.abc.trustpay.client.ebus.PaymentRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/testABC")
public class ABCTestController {

	@RequestMapping(value = "test", method = RequestMethod.GET, produces = AbcDicOrder.MEDIA_TYPE_APPLICATION_JSON_VALUE_UTF8)
	@ResponseBody
	public String test(){
		Date date = new Date();
		;
		String formateDate =new SimpleDateFormat(AbcDicOrder.DATE_PATTERNS[9]).format(date);
		AbcDicOrder abcDicOrder = new AbcDicOrder("T20150706B0000000007","0.01", formateDate.split(" ")[0], formateDate.split(" ")[1], "", "备注", "10");
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.dicOrder.putAll(abcDicOrder.bulidDicOrder());
		AbcDicOrderItem abcDicOrderItem = new AbcDicOrderItem("测试","1");
		paymentRequest.orderitems.put(1, abcDicOrderItem.bulidAbcDicretMap());
		AbcDicRequest abcDicRequest = new AbcDicRequest("A", "http://www.abchina.com");
		paymentRequest.dicRequest.putAll(abcDicRequest.bulidAbcDicRequestMap());
		try{
			MerchantConfig tMerchantConfig=MerchantConfig.getUniqueInstance();;
			String sTrustPayIETrxURL=tMerchantConfig.getTrustPayIETrxURL();
			String sErrorUrl=tMerchantConfig.getMerchantErrorURL();
			String tSignature="";
		
			tSignature = paymentRequest.genSignature(1);
			return sTrustPayIETrxURL;
			}catch (TrxException e){    
				return  e.getCode()+"==" + e.getMessage();
			}
//		JSON json = paymentRequest.postRequest();
//		String ReturnCode = json.GetKeyValue("ReturnCode");
//		String ErrorMessage = json.GetKeyValue("ErrorMessage");
		//return ReturnCode + ":" +ErrorMessage;
	}
	
	
}
