package com.github.mysite.common.payonline.appStorePay;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;

/**
 * description:  App Store 验证 ReceiptData
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-12-23 11:30
 */
public class InAppPurchase {
	// 生产环境
	public static final String PRODUCTION_URL = "https://buy.itunes.apple.com/verifyReceipt";
	// 调试环境
	public static final String ENVIRONMENT_URL = "https://sandbox.itunes.apple.com/verifyReceipt";

	private static final Logger LOG = LoggerFactory.getLogger(InAppPurchase.class);

	/**
	 * Validating Receipts With the App Store
	 * 
	 * @param url
	 *            验证地址
	 * @param receiptData
	 *            交易信息
	 * @return JSON格式{"status":0,receipt:{...}}
	 */
	public static ReceiptResponse receipt(String url, String receiptData) {
		try {
			// 建立连接
			URL client = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) client.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("content-type", "text/json");
			con.setRequestProperty("Proxy-Connection", "Keep-Alive");
			con.setDoOutput(true);
			con.setDoInput(true);

			// 请求参数:JSON格式{"receipt-data":"The base64 encoded receipt data"}
			String text = "{\"receipt-data\":\""+receiptData+"\"}";
			
			// 发送请求
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			out.write(text);
			out.flush();
			out.close();

			// 响应请求
			//BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            //String str;
            //StringBuilder sb = new StringBuilder();
            //while ((str = br.readLine()) != null) {
            //	sb.append(str);
            //}
            //br.close();
            //
            //// 返回参数：JSON格式{"status":0,receipt:{...}}
            //String response = sb.toString();

            // ################ Use guava lib way ####################
            //ByteSource byteSource = new ByteSource() {
            //    @Override
            //    public InputStream openStream() throws IOException {
            //        return con.getInputStream();
            //    }
            //};
            //
            //String response = byteSource.asCharSource(Charsets.UTF_8).read();

            String response;
            //Using Guava And Java7 Convert InputStream To String
            try (final Reader reader = new InputStreamReader(con.getInputStream())) {
                response = CharStreams.toString(reader);
            }
            return ReceiptResponse.parseResponse(JSONObject.parseObject(response));
		} catch (Exception e) {
			LOG.error("IOS Valid ReceiptData fail , {}",e.getMessage());
		}
		return null;
		
	}
	
	//进行Base64编码
	public static ReceiptResponse receipt(String url,byte[] receiptData){
		return receipt(url, Base64.encodeBase64String(receiptData));
	}
}
