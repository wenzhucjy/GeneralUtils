
##快钱人民币网关接入文档

----------
## **1.快钱人民币网关简介**


### **1.1 流程详解**

> 1.   消费者选择商品，商品参数传递给 send 页面。
> 2.   Send 页面将请求提交到快钱。
> 3.   Send 提交后，跳转到快钱银行列表页面，快钱测试环境的银行是模拟的银行，支付不需要用真实银行卡做测试。
> 4.   支付完毕后，快钱会将支付结果反馈给 receive 页面，该功能在 send 页面中的 bgUrl参数设置的，该地址要求是外网能访问到的地址，不能是 localhost 地址，商户技术可以参考 send 页面所给的例子。
> 5.  receive 页面接收到快钱支付结果后，商户先做业务逻辑处理，比如更新数据库。之后再通知快钱已经接收到支付结果。通知快钱接收到支付结果的功能由<result>标签实现，该标签的值一定要为 1， 表示商户接收到通知。 如果该值不为 1， 快钱会不停的向该 receive页面发送支付结果， 所以要求商户只要接收到快钱的支付结果， 不管交易结果是成功还是失败， <result>的值都要返回 1。
> 6.   receive 页面接收到快钱支付结果后， 会自动将参数传递给 show 页面并跳转到 show 页面，该功能是由 receive 页面中的<redirecturl>标签实现，该标签中的值便是 show 页面的地址。 代码中的<redirecturl>标签已经赋值， 商户需要根据自己的实际情况做修改。 同样，show 页面不能是 localhost 的本地地址。

### **1.2 快钱支付环境**

> 快钱提供两套环境，一套是测试环境，另一套是生产环境。一般建议商户先在快钱的测试环境做测试， 熟悉快钱的人民币网关之后再迁移到生产环境做测试。如果商户熟悉快钱的人民币网关接口和流程，可以直接在快钱的生产环境做测试。

## **2.快钱生产环境联调步骤**
### **2.1 证书生成**
>  从快钱的java代码包中，可以看到`99bill.cert.rsa.20340630.cer` 和 `99bill-rsa.pfx`两个证书，其中pfx证书用于send页面对提交的参数组成串后的签名，主要作用是防止商户提交的数据传输过程中被篡改。cer证书用于receive页面中对快钱返回的参数进行验签，主要作用是验证快钱返回给商户的参数没有被篡改。

### **2.1.1 证书生成软件安装**

> `Win32OpenSSL_Light-0_9_8k.exe`一直点 next 即
可完成软件的安装，该软件用于生成证书。软件只要安装在商户技术本地电脑即可。

### **2.1.2 证书生成**

>  1. 打开`openssl.exe`
>  2.  输入 `genrsa -out private-rsa.key 1024`，按 enter 即可。
>  3. 输入 `req -new -x509 -key private-rsa.key -days 750 -out public-rsa.cer`，按 enter，然后按照第二个图片提示进行填写。
>  4.  输入 `pkcs12 -export -name test-alias -in public-rsa.cer -inkey private-rsa.key -out 99bill-rsa.pfx`，按enter，然后按照第二个图输入密码，密码输入时不会显示出来。**商户必须记住该密码，在代码中需要用到**。
>  5. 进入 openssl 的 bin 目录，可以看到执行以上命令后生成的两个证书 `public-rsa.cer` 上传到快钱，而`99bill-rsa.pfx`放于项目中，代码会用到。
>  6.  如果是使用 PHP 的商户，需要将“99bill-rsa.pfx”证书转换为“99bill-rsa.pem” ，输
入转换命令 pkcs12 -in 99bill-rsa.pfx -passinpass:此处输入商户第 4 步设置的密码 -nodes
-out 99bill-rsa.pem，按 enter 即可。进入 openssl 的 bin 目录，此时可以看到 pem 格式
证书已经生成。
### **2.2 证书上传下载**
>  1. 上传证书，登陆[www.99bill.com](http://www.99bill.com)，进入安全设置-商户证书-商户证书上传（选择产品或功能：人民币网关；悬着上传文件：上传生成的`public-rsa.cer`证书），证书的有效期为750天。
>  2.  证书下载，登陆[www.99bill.com](http://www.99bill.com)，进入安全设置-商户证书-快钱证书下载，选择`RSA`证书，若下载不成功，可直接向快钱技术支持索取。

## **3.接口开发**
### **3.1 功能说明及流程**
>  整体流程如下：
用户在商户选择产品戒服务，在下订单完成后，商户网站等系统会将该订单号及对庒订单金额以及收款方信息等相关资料提交到快钱，然后跳转到快钱页面进行支付。当支付完成后，快钱通知商户支付结果，并且根据商户指定的地址跳转到商户指定页面。商户在接收到支付结果之后，可以对数据进行相应更新，然后在显示给用户的页面中作出相应提示。

### **3.2 开发准备**

 - 商户在快钱的用亍收款的人民币账号
 - 商户的人民币网关密钥
 - 如果是使用PKI方式，接入前要准备好商户证书，可以用 OPENSSL 工具生成，具体生成命令可以参考 OPENSSL 命令集，OPENSSL工具可以从快钱后台下载，也可以从技术支持处获得。
 
### **3.3 参数说明**
### **3.3.1 商户提交到快钱**
> 快钱人民币网关提交地址 ： [https://www.99bill.com/gateway/recvMerchantInfoAction.htm](https://www.99bill.com/gateway/recvMerchantInfoAction.htm)<br/>
移动网关支付：[https://www.99bill.com/mobilegateway/recvMerchantInfoAction.htm](https://www.99bill.com/mobilegateway/recvMerchantInfoAction.htm)
|  参数名称  |参数含义  |  为空   |  参数说明 |
|---|---|---|---|
| inputCharset   |  字符集 | 否  |  固定选择值：1代表 UTF-8; 2 代表 GBK; 3 代表 GB2312 |
| pageUrl   |  接受支付结果页面地址 |   是|  需要是绝对地址，与bgUrl 不能同时为空，当 bgUrl 为空时，快钱直接将支付结果 GET 到pageUrl，当 bgUrl 不为空时，按照 bgUrl 的方式返回 |
| bgUrl  |   服务器接受支付结果的地址| 是  | 需要是绝对地址，与pageUrl不能同时为空快钱将支付结果发送到bgUrl对应的地址，并且获取商户按照约定格式输出的地址，显示页面给用户  |
| version  |网关版本   |否   | 人民币网关：v2.0，注意大小写；移动网关：mobile1.0  |
| **mobileGateway**  |**移动网关版本**   |否   | **针对客户端访问，移动网关版本，当version= mobile1.0时有效phone代表手机版移动网关，pad代表平板移动网关，默认为phone** |
| language   |网关页面显示语言种类   |否  | 1代表中文   |
| signType  | 签名类型  |否   |4代表DSA或RSA签名方式   |
| merchantAcctId  |  人民币账号 |否   |指定接收款项的人民币账号   |
| payerName |  支付人姓名   |是   | 英文或中文字符  |
| payerContactType   |  支付人联系方式类型 | 是  | 1代表邮件，2代表手机联系方式  |
| payerContact  | 支付人联系方式  | 是  |根据payerContactType的方式填写对应的信息   |
| payerIdType | 指定付款人  |是   | 0代表不指定，1代表通过商户方ID，2代表通过快钱账户，3代表付款方在商户方的会员编号(当需要支持保存信息功能的快捷支付时,需上送此项) ，4代表企业网银的交通银行直连，为空代表不需要指定 |
| payerId   |付款人标识   | 是  |  当企业网银中的交通银行直连，此值不能为空。此参数需要传入交行企业网银的付款方银行账号，当需要支持保存信息功能的快捷支付时，此值不能为空，此参数需要传入付款方在商户方的会员编号 |
| payerIP   |付款人IP   |是   |高钓鱼风险商户必填，商户传递获取到客户端IP   |
| orderId  |商户订单号   |否   |只允许使用字母、数字、-、_、并以字母或数字开头，唯一值   |
| orderAmount | 商户订单金额  |否   |***以分为单位，整形数字***   |
| orderTime  |商户订单提交时间   |否   |数字串，格式yyyyMMddHHmmss   |
| orderTimestamp  | 快钱时间戳  |是   |高钓鱼风险商户必填，格式yyyyMMddHHmmss   |
| productName  | 商品名称  |是   |英文或中文   |
| productNum  | 商品数量  |是   |整形数字   |
| productId  | 商品代码  |是  |只可填写一个代码，如优惠券信息   |
| productDesc  | 商品描述  | 是  | 英文或中文  |
| ext1  | 扩展字段1  | 是  |支付成功后原样返回给商户   |
| ext2  | 扩展字段2  |  是 |支付成功后原样返回给商户   |
| payType  | 支付方式  | 否  |  支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10（只显示银行卡支付方式），必填 |
|bankId   |银行代码   |是|银行代码，如果payType为00，该值可以为空；如果payType为10，该值必须填写，具体请参考银行列表 |
|cardIssuer   | 发卡机构  |是   |99BILL：支持预付卡和快运通；99BILL_EAP：支持快运通，包括采购卡   |
|cardNum   |卡号   |是   |整形数字，提交给快钱的支付卡号，页面上不可修改，仅当payType为15（信用卡支付）、17（储值卡支付）有效   |
|remitType   | 线 下 汇 款 类型  | 是  |  1代表银行柜台汇款，2代表邮局汇款，当payType=13且submitType=01时（后台提交的线下支付方式）时不为空 |
| remitCode  |汇款识别码   |是   |后台提交汇款识别码格式`M****M****`   |
|  redoFlag | 禁止重复提交标志  |是   |默认0，1代表只允许提交一次，0代表没支付成功前提下可重复提交，建议实物购物车结算类商户采用0；虚拟产品类商户采用1   |
| pid   |合作伙伴在快钱的用户编号   |是   |仅适用于快钱合作伙伴中系统及平台提供商   |
| submitType  | 提交方式  | 是  |默认00，表示前台提交；01表示后台提交   |
| orderTimeOut  |交易超时时间   | 是  |单位秒，正整数0~2592000（30天），默认为空，表示无超时时间，超时计算规则为支付成功时间 扣去 提交时间 大于超时时间，若超时会自动发起退款   |
| extDataType  | 附加信息类型  |是   |字符串   |
| extDataContent  | 附加信息  | 是  | XML格式  |
| signMsg  | 签名字符串  | 否  |param1={value1}&param2={value2}...&paramn={valuen}，然后进行商户私钥证书加密形成密文后进行1024位BASE64转码  |
### **3.3.2 快钱返回到商户**
> 返回地址 pageUrl 戒 bgUrl 对应的地址只填写 pageUrl 时，或者bgUrl所指定的地址不可用时，快钱会将支付结果直接以 GET 方式发送到 pageUrl 对应的地址
|  参数名称  |参数含义 |  参数说明 |
|---|---|---|---|
| merchantAcctId  |  人民币账号  |接收款项的人民币账号   |
| version  |网关版本或移动网关版本  | 与订单提交时候保持一致  |
| language   |网关页面显示语言种类    | 1代表中文|
| signType  | 签名类型  |4代表DSA或RSA签名方式   |
| payType|支付方式|固定选择值：00、10、11、12、13、14、15、17、19、21、22，与提交订单时保持一致|
|bankId|银行代码|返回用户在实际支付时所使用的银行代码|
|orderId|商户订单号|与提交订单时保持一致|
| orderAmount | 商户订单金额  | 整数，以分为单位|
| orderTime  |商户订单提交时间|数字串，格式yyyyMMddHHmmss，与提交订单时保持一致   |
|bindCard|已绑短号卡|可为空，信用卡快捷支付绑定卡信息后返回前六后四位信用卡号|
|bindMobile|已绑短号手机尾号|可为空，信用卡快捷支付绑定卡信息后返回前三位后四位手机号码|
|dealId  |快钱交易号|数字串，该交易在快钱系统中对应的交易号|
|bankDealId|银行交易号|数字串，该交易在银行支付时对庒的交易号，如果丌是通过银行卡支付，则为空|
|dealTime|快钱交易时间|快钱对交易进行处理时间，格式yyyyMMddHHmmss|
|payAmount|订单实际支付金额|整数，以分为单位|
|fee|费用|快钱收取商户的手续费|
|ext1|扩展字段1|与订单提交时保持一致|
|ext2|扩展字段2|与订单提交时保持一致|
|payResult|处理结果|10：支付成功|
|errCode|错误代码|失败时返回的错误代码，可以为空|
|signMsg|签名字符串|对亍所有值不为空的参数及对应值， 按照如上顺序及如下规则组成字符串，DSA或RSA方式：param1={value1}&...&paramn={valuen}，然后进行快钱证书加密形成密文后进行1024位BASE64转码|
### **3.4 开发提示**
### **3.4.1 签名字符串**
>  支付网关接口，在商户提交订单到快钱时和快钱返回结果给商户时都含有签名字符串signMsg，但两个签名字符串并无直接联系。`DSA或RSA方式都是非对称的加密方式`，商户提交用商户私钥证书（`99bill-rsa.pfx`，详见openssl工具生成商户私钥证书）加密，快钱通过商户的公钥证书来解密；快钱返回时是通过快钱私钥证书
加密，商户用快钱公钥证书（`99bill.cert.rsa.20340630.cer`）解密来验签。

 - 当 signType=4时，加密串为：
```bash
inputCharset={inputCharset}&pageUrl={pageUrl}&bgUrl={bgUrl}&version={version}&language={language}&signType={signType}&merchantAcctId={merchantAcctId}&payerName={payerName}&payerContactType={payerContactType}&payerContact={payerContact}&payerIdType={payerIdType}&payerId={payerId}&payerIP={payerIP}&orderId={orderId}&orderAmount={orderAmount}&orderTime={orderTime}&orderTimestamp={orderTimestamp}&productName={productName}&productNum={productNum}&productId={productId}&productDesc={productDesc}&ext1={ext1}&ext2={ext2}&payType={payType}&bankId={bankId}&cardIssuer={cardIssuer}&cardNum={cardNum}&remitType={remitType}&remitCode={remitCode}&redoFlag={redoFlag}&pid={pid}&submitType={submitType}&orderTimeOut={orderTimeOut}&extDataType={extDataType}&extDataContent={extDataContent}
#商户使用商户生成的商户pfx私钥进行加密。
```

 - 快钱返回给商户时的组成加密串的示例如下（假定全部参数值都不为空） ：
```bash
#当signType=4时，加密串为：
merchantAcctId={merchantAcctId}&version={version}&language={language}&signType={signType}&payType={payType}&bankId={bankId}&orderId={or
derId}&orderTime={orderTime}&orderAmount={orderAmount}&bindCard={bindCard}&bindMobile={bindMobile}&dealId={dealId}&bankDealId={bankDealId}&dealTime={dealTime}&payAmount={payAmount}&fee={fee}&ext1={ext1}&ext2={ext2}&payResult={payResult}&errCode={errCode}
#注：商户使用快钱生成的快钱公钥进行解密
```
所有参与签名的参数及其值的大小写必须与示例保持一致。

### **3.4.2 通知支付结果**

快钱提供了两个参数用于商户指定接收支付结果页面。 一个是 pageUrl， 一个是 bgUrl，两个参数不能同时为空。如果两个参数同时填写，会优先按照 bgUrl 的方式。现分别描述如下：

 - 只填写pageUrl时，快钱会将支付结果直接以GET方式发送到pageUrl对应的地址，确定连接到接受地址后只发送一次通知。商户接收到支付结果之后，根据支付结果进行相应的商户业务逻辑处理，并且且给支付人显示页面提示信息。商户仍然需要对重复接收支付结果
进行判断，以防止因刷新引起重复接收支付结果而进行误处理。

 - 如果只填写了bgUrl，快钱会将支付结果以GET方式发送到bgUrl对应的地址，商户接收到支付结果并且进行相应的商户业务逻辑处理之后，需要按照指定的方式输出内容，告诉快钱已经成功接收并处理完毕，示例如下
```java

    <result>1</result>
    <redirecturl>
      http://www.yoursite.com/showSuccess
    </redirecturl>

```
> 
如果 result 标签里面的值为数字 1 时，快钱会认为商户已经接收到支付结果并成功处理，快钱会按照 redirecturl标签里面提供的地址，跳转到新的页面，同时把支付信息参数再次带过来。用户可以在新的页面里面看到商户给出的页面提示信息。如果 result 标签里面的值丌为数字 1 时，快钱会在 18 小时内，**不断按照如上方式给商户发送支付结果并判断 result 的值，直到 result 的值为 1 为止**。在历次重复发送中，初期快钱发送时间间隔为5秒，以后发送的时间间隔会逐步加长。快钱可能会根据需要调整支付结果通知的频率和次数。

### **3.4.3 商户对支付结果的处理**

> 1.  商户在接收到快钱的支付结果后，***务必判断同一笔订单是否已经针对支付成功的情况进行过处理***
> 2.  因网络戒银行服务器原因，快钱可能第一次发送支付失败的结果给商户，但后来会对同一订单号法送支付成功的信息给商户。商户应在保证对加密验证串等信息严格验证的前提下，对相同订单号按照支付成功的业务逻辑进行处理。即商户收到的支付结果通知有失败也有成功，以成功信息为准迚行支付成功相关业务处理。
> 3.  可能会由亍网络或者用户刷新页面的原因，商户的页面可能短时间内会多次接收到支付结果信息，而支付人看到的只能是最后的页面。商户最好能在后续的提示中，给出让支付人不易误解的提示信息。

### **3.5 特殊定制**

### **3.5.1 防钓鱼接口**

> 快钱系统绑定了商户的域名或IP，指定非此类域名或IP提交的数据位非法的数据，若需要修改需向快钱技术人员申请。

### **3.5.2 绑定快钱服务器IP**

> 对亍高风险性商户（如提供虚拟服务及数字卡的商户） ，快钱建议商户在保管好人民币网关密钥的同时，在接收快钱支付结果通知页面对数据来源服务器 IP 进行判断，拒收非快钱IP源的通知数据，避免因虚假数据造成误处理。绑定快钱服务器 IP 时，推荐使用 bgUrl 方式接收快钱支付结果通知。快钱服务器 IP 地址请向快钱技术支持人员索取。


详见《快钱人民币网关支付接口文档_V3.0.3.pdf》、《快钱人民币网关自助接入文档.pdf》