### 安卓原生网络请求(map封装参数)

```
//网络请求工具类
public class NetworkUtil {

    //get请求
    public  static  String doGet(String urlstr, HashMap<String,String> map){
        //创建一个请求
        try {
            String parseParams = parseParams(map);
            URL url =  new URL(urlstr +"?" + parseParams);
            //打开请求
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方法
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200){
                InputStream in = conn.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                //返回数据
                return  buffer.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //post请求
    public  static String doPost(String urlstr,HashMap<String,String> map){
        //创建一个请求
        try {
            URL url =  new URL(urlstr);
            //打开请求
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方法
            conn.setRequestMethod("POST");
            //解析集合
            String parseParams = parseParams(map);
            //设置开启输出流
            conn.setDoOutput(true);
            //获取输出流,发送数据
            conn.getOutputStream().write(parseParams.getBytes());
            if (conn.getResponseCode() == 200){
                InputStream in = conn.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                //返回数据
                return  buffer.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //把一个map集合转化为字符串
    private static String parseParams(HashMap<String, String> map) {
      String strString = "";
        if (map != null){
        for (Map.Entry<String, String> entry: map.entrySet()){
            strString += entry.getKey() + "=" + entry.getValue() + "&";
        }
     strString =    strString.substring(0,strString.length()-1);
    return  strString;
    }
    return  strString;
    }

}
```