package com.xkhouse.erm.erm;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
/*public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 6000; // 延迟六秒
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
} */
public class SplashActivity extends Activity {
    final private int SPLASH_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 设置全屏
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*
         * 设置隐藏标题栏
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*
         * 2秒后跳转到主界面
         */
        new Handler().postDelayed(new Runnable() {
            public void run() {
                launchMainActivity();
            }
        }, SPLASH_TIME);
        setContentView(R.layout.activity_splash);
    }
    /*
     * 利用Intent切换到主Activity
     */
    private void launchMainActivity() {
        /*
     * 创建一个intent，从当前Activity指向要跳转的Activity
     */
        Intent intent = new Intent(this, LoginActivity.class);
    /*
     * 启动目标Activity
     */
        startActivity(intent);
    /*
     * 启动画面只需要程序开始时显示一次，显示完后即可退出
     */
        finish();
    }
}
 