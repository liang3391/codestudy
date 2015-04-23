package com.xkhouse.erm.erm;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by Administrator on 2015/4/15.
 */
public class HttpUtil {
    // 基础URL
    public static final String BASE_URL="http://erm2.xkhouse.com/index.php/app/user/loginCheck";
    // 获得Get请求对象request
    public static HttpGet getHttpGet(String url){
        HttpGet request = new HttpGet(url);
        return request;
    }
    // 获得Post请求对象request
    public static HttpPost getHttpPost(String url){
        HttpPost request = new HttpPost(url);
        return request;
    }
    // 根据请求获得响应对象response
    public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException {
        HttpResponse response = new DefaultHttpClient().execute(request);
        return response;
    }
    // 根据请求获得响应对象response
    public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
        HttpResponse response = new DefaultHttpClient().execute(request);
        return response;
    }

    // 发送Post请求，获得响应查询结果
    public static String queryStringForPost(String url, String user_name, String user_password,String sec_key){
        // 根据url获得HttpPost对象
        HttpPost request = HttpUtil.getHttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("user_password", user_password));
        params.add(new BasicNameValuePair("sec_key",sec_key));
        String result = null;
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // 获得响应对象
            HttpResponse response = HttpUtil.getHttpResponse(request);
            // 判断是否请求成功
            if(response.getStatusLine().getStatusCode()==200){
                // 获得响应
                result = EntityUtils.toString(response.getEntity());
                System.out.println("HttpUtil result: " + result);
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        }
        return null;
    }
    //获取当前进行密钥处理
   // public static void sec_key(String[] args) throws Exception {
    public static void sec_key() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

        String str = (sdf.format(new Date()));
        char[] dist = new char[str.length()];
        for(int x = str.length() - 1, p = 0; x >= 0; x--){
            dist[p++] = str.charAt(x);
        }
        BigInteger aa =new BigInteger("99999999");
        BigInteger bb= new BigInteger(new String(dist));
        BigInteger sub=aa.subtract(bb);
        System.out.println(sub.toString());
        String s = sub.toString();
        char[] charArray = s.toCharArray();
        String[] strings = new String[charArray.length];
        for (int k = 0; k < charArray.length; k++) {
            String c = String.valueOf(charArray[k]);
            int parseInt = Integer.parseInt(c);
            if (parseInt == 0) {
                strings[k] = "c02";// 这里替换
            } else if (parseInt == 1) {
                strings[k] = "x03";
            } else if (parseInt == 2) {
                strings[k] = "f04";
            } else if (parseInt == 3) {
                strings[k] = "a05";
            } else if (parseInt == 4) {
                strings[k] = "b06";
            } else if (parseInt == 5) {
                strings[k] = "e07";
            } else if (parseInt == 6) {
                strings[k] = "g08";
            } else if (parseInt == 7) {
                strings[k] = "k09";
            } else if (parseInt == 8) {
                strings[k] = "s10";
            } else if (parseInt == 9) {
                strings[k] = "t11";
            } else {
                strings[k] = c;
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int k = 0; k < strings.length; k++) {
            sb.append(strings[k]);
        }
        String result = sb.toString();
        System.out.println(result);
    }

    // 发送Get请求，获得响应查询结果
    public static String queryStringForGet(String url){
        // 获得HttpGet对象
        HttpGet request = HttpUtil.getHttpGet(url);
        String result = null;
        try {
            // 获得响应对象
            HttpResponse response = HttpUtil.getHttpResponse(request);
            // 判断是否请求成功
            if(response.getStatusLine().getStatusCode()==200){
                // 获得响应
                result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        }
        return null;
    }
}