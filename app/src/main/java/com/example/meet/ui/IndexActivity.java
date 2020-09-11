package com.example.meet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.bmob.BmobManager;
import com.example.framework.entity.Constants;
import com.example.framework.utils.SpUtils;
import com.example.meet.MainActivity;
import com.example.meet.R;


/**
 * 启动页
 */
public class IndexActivity extends AppCompatActivity {

    /**
     *  1.把启动页全屏
     *  2.延迟进入主页
     *  3.根据具体逻辑是进入主页还是引导页还是登录页
     *  4.适配刘海屏
     */

    private static final int SKIP_MAIN = 1000;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case SKIP_MAIN:
                    startMain();
            }
            return false;
        }
    });

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mHandler.sendEmptyMessageDelayed(SKIP_MAIN,2 * 1000);
    }

    /**
     * 进入主页
     */
    private void startMain(){
        //判断App是否第一次启动 install -> first run
        boolean isFirstApp = SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP,true);
        Intent intent = new Intent();
        if (isFirstApp){
            //跳转到引导页
            intent.setClass(this,GuideActivity.class);
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP,false);
        }else {
            //如果非第一次启动，判断是否曾经登陆过
            String token = SpUtils.getInstance().getString(Constants.SP_TOKEN,"");
            if (TextUtils.isEmpty(token)){
                //判断Bmob是否登录
                if (BmobManager.getInstance().isLogin()){
                    //跳转主页
                    intent.setClass(this, MainActivity.class);
                }else {
                    //跳转登录页
                    intent.setClass(this,LoginActivity.class);
                }
            }else {
                //跳转主页
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }

    /**
     * 优化
     * 冷启动经过步骤：
     * 1.第一次安装，加载应用程序启动
     * 2.启动后显示一个空白窗口
     * 3.创建/启动应用进程
     *
     * App内部：
     * 1.创建App对象/Application对象
     * 2.启动主线程（Main/UI Thread）
     * 3.创建应用入口/LAUNCHER
     * 4.填充ViewGroup中的View
     * 5.绘制View measure -> layout -> draw
     *
     * 优化手段：
     * 1.视图优化
     *      1.设置主题透明
     *      2.设置启动图片
     * 2.代码优化
     *      1.优化Application
     *      2.布局优化，不需要繁琐布局
     *      3.阻塞UI线程的操作
     *      4.加载Bitmap/大图
     *      5.其他的占用主线程操作
     *
     * 检测App Activity的启动时间
     * 1.Shell
     *   ActivityManager -> adb shell an start -S -W com.example.meet/com.example.meet.ui.IndexActivity
     * 2.Log
     */
}
