
## 中国农行网上支付平台

@(ABC)[abc|payOnline]

## **1.简介**

### 1.1 功能描述

> 1.  商户交易网站支持功能包括支付请求并且具备接收网上支付平台支付结果响应的功能。
>2.  接口采用电子证书的方式来保证商户与网上支付平台间的身份验证、中间信息传递的完整性，以便进行电子商务安全当中非常重要的交易身份辨识、不可抵赖、防止篡改等功能。

### 1.2 总体架构图

>总体架构图

```sequence
Title: 时序图
消费者->商户交易网站服务器: 购物
消费者-->农行支付平台: 支付
商户交易网站服务器-->农行支付平台: 交易请求
Note left of 农行支付平台: Internel
农行支付平台-->商户交易网站服务器: 交易响应
商户交易网站服务器->消费者:交易结果
```

## **2.接口包说明**

### 2.1 接口包

>银行提供的接口开发软件包 `TrustPayClient-Java-Vx.x.x.zip`（`x.x.x` 为接口开发软件包的版本号，最新的电子商务接口包为`V3.0.2`）

### 2.2 接口主要文件

|  文件名称 |  说明 |	
|---|---|
| TrustMerchant.properties  | 接口配置文件，存放于`src/resources`目录下  |
|abc.truststore   |  农行根证书 |
| trustpay.cer  | 网上支付平台证书  |
|...|...|

## **3.安装步骤**

### 3.1 接口开发软件包安装

- 将 `TrustPayClient-Vx.x.x.jar` 加入应用服务器的 `CLASSPATH` 中。
- 若为maven项目，则可执行
`mvn install:install-file -Dfile=D:\TrustPayClient-V3.0.2.jar -DgroupId=com.abcchina -DartifactId=TrustPayClient -Dversion=V3.0.2 -Dpackaging=jar`即可把jar添加到本地仓库中。或者配置`pom.xml`文件如下：

```xml
<plugin>			
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-install-plugin</artifactId>
<version>2.5.2</version>
<configuration>
<!-- customer groupId,artifactId,version -->			
<groupId>com.abcchina</groupId>
<artifactId>TrustPayClient</artifactId>
<version>3.0.2</version>	
<packaging>jar</packaging>
<file>${basedir}/TrustPayClient-V3.0.2.jar</file>
<!-- generate pom file -->	
<generatePom>true</generatePom>
<!-- 
<generatePom>false</generatePom>
<pomFile>${basedir}/dependencies/someartifact-1.0.pom</pomFile>
 -->
</configuration>
<executions>
 <execution>
	<id>install-jar-lib</id>
	<goals>
	<goal>install-file</goal>
	</goals>
	<!-- 
	you will need to run the validation phase explicitly: mvn validate.After this step, 
	the standard compilation will work: mvn clean install
	-->				
	<phase>validate</phase>
 </execution>
</executions>
</plugin>
```

>上述的`${basedir}`即项目工程目录，把相应的拷贝到`${basedir}`中，然后执行`mvn:install`，即把jar上传到本地maven仓库中。

### 3.2 接口开发软件包配置

>接口配置文件 `TrustMerchant.properties`，依照银行提供的信息设定相对应的参数。并将 `TrustMerchant.properties` 所在的目录加入应用服务器的 `CLASSPATH` 中。

## **4.配置文件说明**

### 4.1 商户pfx证书下载

> 1.  用管理员登陆[https://www.95599.cn](https://www.95599.cn/ebusmerchant/startUpHtmlSessionAct.ebf)商户后台。
> 2.  填入授权码和参考号（授权码与参考号银行办理业务时由行方柜台人员给出），系统引导生成服务器证书默认存放于IE浏览器中。***（备注：需用XP系统才可）***
> 3.  IE安全选项 —— 选择相应的服务器证书 —— 导出私钥，导出证书提示输入密码即服务器证书密码，编程需用到此证书和密码。

### 4.2 商户配置

>`TrustMerchant.properties` 配置样例如下：

```properties
# 网上支付平台系统配置段 - 生产环境 - 请勿更改            
#网上支付平台通讯方式（http / https）
TrustPayConnectMethod=https
#网上支付平台服务器名
TrustPayServerName=pay.abchina.com
#网上支付平台交易端口
TrustPayServerPort=443
#网上支付平台接口特性
TrustPayNewLine=2
#网上支付平台交易网址
TrustPayTrxURL=/ebus/trustpay/ReceiveMerchantTrxReqServlet
#商户通过浏览器提交网上支付平台交易网址
TrustPayIETrxURL=https://pay.abchina.com/ebus/trustpay/ReceiveMerchantIERequestServlet
#商户通过浏览器提交接收网上支付平台返回错误页面；该页面是商户端页面；路径商户可以根据自己的应用情况自行配置
#如http://www.abc.china/ErrorPage.jsp
MerchantErrorURL=http://www.abcchina.com/ErrorPage.jsp
#====================================
# 网上支付平台系统配置段 - 生产环境 - 更改证书存放路径，使其和本地存放路径相匹配（绝对路径）
#=====================================
#网上支付平台证书,将TrustPay.cer的存放路径填在如下位置
TrustPayCertFile=D:/abchina/TrustPay.cer
#农行根证书文件，将abc.truststore存放的路径填在如下位置
TrustStoreFile=D:/abchina/abc.truststore
#农行根证书文件密码
TrustStorePassword=changeit
#=====================================
# 商户资料段 (请更改)                                                                  
#=====================================
#商户编号，若配置多商户，用逗号隔开且证书与密码需与商户编号顺序一致
MerchantID=
# 商户系统配置段 (请更改)             
#交易日志文件存放目录
LogPath=D:/abchina/logs
#证书储存媒体
#0: File
#1: Hardware
MerchantKeyStoreType=0
#商户证书储存目录文件名（当KeyStoreType=0时，必须设定），根据存放位置自行配置
MerchantCertFile=D:/abchina/xx.pfx
#商户私钥加密密码（当KeyStoreType0时，必须设定）
MerchantCertPassword=
#Sign Server地址（当KeyStoreType=1时，必须设定）
#SignServerIP=如果使用签名服务器，请在此设定签名服务器的IP
#Sign Server端口（当KeyStoreType=1时，必须设定）
#SignServerPort=如果使用签名服务器，请在此设定签名服务器的端口号
#Sign Server密码（当KeyStoreType=1时，选择设定）
#SignServerPassword=如果使用签名服务器，请在此设定签名服务器的密码
```

## **5.交易说明**

>页面通知支付结果方式：

```sequence
Title: 时序图
浏览器->商户交易网站服务器: 1、购物&选择网上支付
商户交易网站服务器-->农行支付平台: 2、提交订单及支付请求
农行支付平台-->商户交易网站服务器: 3、回传支付页面网址
商户交易网站服务器-->浏览器:4、将消费者浏览器页面转至支付页面
浏览器->农行支付平台: 5、进行支付
农行支付平台-->浏览器: 6、响应支付结果，包含需响应给商户的信息（经过银行签名）
浏览器->商户交易网站服务器:7、浏览器自动提交支付结果，验证信息是否经过银行签名
```

>服务器通知支付结果方式：

```sequence
Title: 时序图
浏览器->商户交易网站服务器: 1、购物&选择网上支付
商户交易网站服务器-->农行支付平台: 2、提交订单及支付请求（ServerURL）
农行支付平台-->商户交易网站服务器: 3、回传支付页面网址
商户交易网站服务器-->浏览器:4、将消费者浏览器页面转至支付页面
浏览器->农行支付平台: 5、进行支付
农行支付平台-->商户交易网站服务器: 6、通知商户支付结果
商户交易网站服务器-->农行支付平台:6.1、商户必须将显示给浏览器看的CustomerURL返回给支付平台
农行支付平台-->浏览器:7、将浏览器跳转至6.1步骤制定的CustomerURL，包含需响应给商户的信息
浏览器->商户交易网站服务器:7.1、浏览器自动提交支付结果，跳转CustomerURL
```

## **6.编程样例**
编程样例参照行方给的样例
>`/demo/MerchantPaymentIE.html`：商户通过页面传参数表单提交支付请求范例页面
>`/demo/MerchantPaymentIE.jsp`：商户通过页面传参数表单提交支付请求范例程序
>`/demo/MerchantResult.jsp `：支付结果接收范例程序
>...