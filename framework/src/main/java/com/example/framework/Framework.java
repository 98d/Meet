package com.example.framework;

import android.content.Context;

import com.example.framework.bmob.BmobManager;
import com.example.framework.utils.LogUtils;
import com.example.framework.utils.SpUtils;

/**
 *
 * Framework 入口
 */
public class Framework {

    private volatile static Framework mFramework;

    private Framework(){

    }

    public static Framework getFramework(){
        if (mFramework == null){
            synchronized (Framework.class){
                if (mFramework == null){
                    mFramework = new Framework();
                }
            }
        }
        return mFramework;
    }

    /**
     * 初始化框架 Model
     *
     * @param mContext
     */
    public void initFramework(Context mContext) {
        LogUtils.i("initFramework");
        SpUtils.getInstance().initSp(mContext);
        BmobManager.getInstance().initBmob(mContext);
    }
}
