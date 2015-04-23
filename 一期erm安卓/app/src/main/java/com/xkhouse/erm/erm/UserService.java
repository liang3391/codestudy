package com.xkhouse.erm.erm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    /**
     * 验证用户登录是否合法
     * 返回值：请求是否成功
     */
    public static boolean check(String user_name, String user_password,String sec_key) {
        String path="http://erm2.xkhouse.com/index.php/app/user/loginCheck";
        //将用户名和密码放入HashMap中
        Map<String,String> params=new HashMap<String,String>();
        params.put("user_name", user_name);
        params.put("user_password", user_password);
        params.put("sec_key", sec_key);
        try {
            return sendGETRequest(path,params,"UTF-8");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    private static boolean sendGETRequest(String path,
                                          Map<String, String> params,String encode) throws MalformedURLException, IOException {
        StringBuilder url=new StringBuilder(path);
        url.append("?");
        for(Map.Entry<String, String> entry:params.entrySet())
        {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encode));
            url.append("&");
        }
        //删掉最后一个&
        url.deleteCharAt(url.length()-1);
        HttpURLConnection conn=(HttpURLConnection)new URL(url.toString()).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode()==200)
        {
            return true;
        }
        return false;
    }
}