package com.xkhouse.erm.erm;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 8/18/2014.
 */
public class SharedService {

    private Context context;

    public SharedService(Context context)
    {
        this.context = context;
    }

    //设置保存数据的方法
    public void save(String user_name,String user_password) {
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_WORLD_READABLE);
        Editor editor = sp.edit();
        editor.putString("user_name", user_name);
        editor.putString("user_password", user_password);
        editor.commit();
            }


    //设置读取Shareference文件的方法
    public Map<String,String> read()
    {
        Map<String,String> data = new HashMap<String, String>();
        SharedPreferences sp = context.getSharedPreferences("mysp",Context.MODE_WORLD_READABLE);
        data.put("user_name",sp.getString("user_name",""));
        data.put("user_password",sp.getString("user_password",""));
        return data;
    }
}
