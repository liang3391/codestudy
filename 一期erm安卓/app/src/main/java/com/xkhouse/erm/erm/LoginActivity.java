package com.xkhouse.erm.erm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import net.yoojia.asynchttp.AsyncHttpConnection;
import net.yoojia.asynchttp.StringResponseHandler;
import net.yoojia.asynchttp.support.ParamsWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

//import java.lang.string;
public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";

    private CheckBox chkItem;
    private Button login_text;
    private EditText login_label_username, login_label_password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("登录系统");
        setContentView(R.layout.activity_login);

        //cancelBtn = (EditText) findViewById(R.id.cancelButton);
        login_label_username = (EditText) findViewById(R.id.login_label_username);
        login_label_password = (EditText) findViewById(R.id.login_label_password);
        login_text = (Button) findViewById(R.id.login_text);
        chkItem = (CheckBox) findViewById(R.id.chkItem);

       /* cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }); */

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查用户是否输入用户名密码
                if (validate()) {
                    // 检查是否登陆成功

                    login();
                    // here is async ,not in man thread .

/*
                    if (login()) {
                        Toast.makeText(LoginActivity.this, "成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名称或者密码错误，请重新输入！",
                                Toast.LENGTH_SHORT).show();
                    } */
                }
            }
        });
    }

    // 验证用户名密码是否为空
    private boolean validate() {
        String username = login_label_username.getText().toString();
        if (username.equals("")) {
            Toast.makeText(LoginActivity.this, "用户名称是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        String pwd = login_label_password.getText().toString();
        if (pwd.equals("")) {
            Toast.makeText(LoginActivity.this, "用户密码是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }
    public String sec_key() {
        //public static String sb;
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
        return result;
        // System.out.println(result);
    }

    // 登录方法
    private boolean login() {
        // 获得用户名称

        // static int sec_key;
        String user_name = login_label_username.getText().toString().trim();
        // 获得密码
        String user_password = login_label_password.getText().toString().trim();
        String sec_key = sec_key();//密钥
        // 获得登录结果

        AsyncHttpConnection connection = AsyncHttpConnection.getInstance();
        String url = HttpUtil.BASE_URL;
        ParamsWrapper wrapper = new ParamsWrapper();
        wrapper.put("user_name", user_name);
        wrapper.put("user_password", user_password);
        wrapper.put("sec_key", sec_key);
        connection.post(url, wrapper, new StringResponseHandler() {
            @Override
            protected void onResponse(final String content, URL url) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //获取服务器返回的json进行登录判断 如果有false登录失败否则成功
                        //  Toast.makeText(LoginActivity.this, content, Toast.LENGTH_SHORT).show();
                        try {
                        JSONObject json = new JSONObject(content);
                        Boolean flag= json.getBoolean("status") ;
                            System.out.println("status");
                            if( flag == false ){
                                Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT)
                                        .show();
                            }else {
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Log.e(TAG, content);

            }

            @Override
            public void onSubmit(URL url, ParamsWrapper params) {
                Log.d(TAG, "url :" + url);
            }

            @Override
            public void onConnectError(IOException exp) {
                Log.e(TAG, "error", exp);
            }

            @Override
            public void onStreamError(IOException exp) {
                Log.e(TAG, "error", exp);
            }
        });

        return false;
        /*
        String result = query(user_name, user_password,sec_key);
        if (result != null && result.equals("0")) {
            return false;
        } else {
            System.out.println("LoginActivity result: " + result);
            // 将此服务器返回的此用户信息保存起来
            saveUserMsg(result);
            return true;
        }
        */
    }

    // 根据用户名称密码查询
    private String query(String user_name, String user_password, String sec_key) {
        // 组合url
        String url = HttpUtil.BASE_URL + "servlet/LoginServlet";
        // 查询返回结果
        return HttpUtil.queryStringForPost(url, user_name, user_password, sec_key);
    }


    // 将用户信息保存到配置文件
    private void saveUserMsg(String msg) {
        // 用户编号
        String user_id = "";
        // 用户名称
        String user_name = "";
        // 获得信息数组
        String[] msgs = msg.split(";");
        int idx = msgs[0].indexOf("=");
        user_id = msgs[0].substring(idx + 1);
        idx = msgs[1].indexOf("=");
        user_name = msgs[1].substring(idx + 1);
        // 共享信息
        SharedPreferences pre = getSharedPreferences("user_msg",
                MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("user_id", user_id);
        editor.putString("user_name", user_name);
        editor.commit();
    }
}