### AndroudHttps介绍以及集成

# https

http  ip/tcp  socket:封装了TCP的一个api：

协议：双方达成的一个约定，如果不遵守这个规则，谁都不认识谁

http：封装数据，明文，不安全。

 一、HTTP和HTTPS的基本概念

HTTP：是互联网上应用最为广泛的一种网络协议，是一个客户端和服务器端请求和应答的标准（TCP），用于从WWW服务器传输超文本到本地浏览器的传输协议，它可以使浏览器更加高效，使网络传输减少。  

HTTPS：是以安全为目标的HTTP通道，简单讲是HTTP的安全版，即HTTP下加入SSL 3.0层，HTTPS的安全基础是SSL，因此加密的详细内容就需要SSL。 TLS 

HTTPS协议的主要作用可以分为两种：一种是建立一个信息安全通道，来保证数据传输的安全；另一种就是确认网站的真实性。  
 二、HTTP与HTTPS有什么区别？

url防篡改。

HTTP协议传输的数据都是未加密的，也就是明文的，因此使用HTTP协议传输隐私信息非常不安全，为了保证这些隐私数据能加密传输，于是网景公司设计了SSL（Secure Sockets Layer）协议用于对HTTP协议传输的数据进行加密，从而就诞生了HTTPS。  

 简单来说，HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议，要比http协议安全。

HTTPS和HTTP的区别主要如下：

1、https协议需要到CA申请证书(验证身份，保证安全)，一般免费证书较少，因而需要一定费用。

2、http是超文本传输协议，信息是明文传输，https则是具有安全性的ssl加密传输协议。

3、http和https使用的是完全不同的连接方式，用的端口也不一样，前者是80，后者是443，tomcat 8443。

4、http的连接很简单，是无状态的；HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议，比http协议安全。

过程：  

1. 访问服务器，带上一个随机数  
2. 服务器返回信息，带上一个随机数（根据客户端生成的随机数，服务器用此来生成随机数）和公钥  
3. 客户端根据服务器的随机数，生成一个key 和 秘钥，将key传递给服务器  
4. 服务器收到key，因为知道了第一次握手的随机数，所以可以根据证书按照相同的计算方式生成秘钥  
   至此，得到秘钥之后，就可以进行沟通了。  

身份认证：  

1. 服务器认证客户端，只要客户端有证书，才能是合法的用户。  
2. 客户端认证服务器，就算服务器被劫持，有证书存在，也会提示用户，该访问的地址不是本来要访问的地址。

几款免费SSL证书，比如：CloudFlare SSL、StartSSL、Wosign沃通SSL、NameCheap等。  

三、https的缺点  
安全是优点，也是缺点，会导致一定的性能低和流量多，但是这个缺点远远低于https带来的安全保证。  

Https  原理： http://www.ruanyifeng.com/blog/2014/02/ssl_tls.html



*  Https集成代码示例
   ```java

   /**

   - 设置证书
   - @param certificates
      */
     public static void setCertificates(InputStream... certificates)
     {
      try
      {
   // 创建一个证书工厂类，这个类用来读取证书信息，参数为证书标准
    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
   // 创建一个证书库，用来存储证书信息
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(null);
    int


    index = 0;
    for (InputStream certificate : certificates)
    {
   	
        String certificateAlias = Integer.toString(index++);
   	// 保存证书信息到证书库中
        keyStore.setCertificateEntry(certificateAlias, 
   	// 读取证书
   	certificateFactory.generateCertificate(certificate));

        try
        {
            if (certificate != null)
                certificate.close();
        } catch (IOException e)
        {
        }
    }
   // 创建一个安全上下文
    SSLContext sslContext = SSLContext.getInstance("TLS");
   // 创建一个可信任的工厂
    TrustManagerFactory trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
   // 通过证书库，初始化安全工厂
    trustManagerFactory.init(keyStore);
   // 根据安全工厂，强随机数，初始化安全上下文
    sslContext.init
            (
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
   // 为httpsClient设置安全上下文
    okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
   // 设置host校验
    okHttpClient.setHostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            if("47.93.30.78".equals(hostname)){
                return true;
            }else{
                return false;
            }
   		// 返回true，表示信任
        }
    });
     } catch (Exception e)

    {

        e.printStackTrace();

    }

   }
    
   ```
   ​