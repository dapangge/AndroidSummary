### MD5加密模版

```
public class MD5 extends AndroidTestCase {

    //MD5加密
    public  void testMD5() throws NoSuchAlgorithmException {
        //创建一个加密器
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String password = "112335sss";
        byte[] digest = md5.digest(password.getBytes());
        StringBuffer buffe  = new StringBuffer();
        for (byte b: digest){
            String str = Integer.toHexString(b & 0xff);
            if (str.length() == 1){
                buffe.append('0');
            }
            buffe.append(str);
        }
        //添加自定义的字符串加密
        System.out.print(buffe.toString() +"abc");
    }
}
```