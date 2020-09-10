package com.example.framework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Bomb 管理类
 */
public class BmobManager {

    private static final String BMOB_SDK_ID = "7b50c270a00e4f15858e50b71906bc49";

    private volatile static BmobManager mInstantce = null;

    private BmobManager(){

    }

    public static BmobManager getInstance(){
        if (mInstantce == null){
            synchronized (BmobManager.class){
                if (mInstantce == null){
                    mInstantce = new BmobManager();
                }
            }
        }
        return mInstantce;
    }

    /**
     * 初始化Bmob
     * @param mContext
     */
    public void initBmob(Context mContext){
        Bmob.initialize(mContext,BMOB_SDK_ID);
    }

    /**
     * 发送短信验证码
     * @param phone 手机号码
     * @param listener 回调
     */
    public void requestSMS(String phone, QueryListener<Integer> listener){
        BmobSMS.requestSMSCode(phone,"",listener);
    }

    /**
     * 通过手机号注册或登录
     * @param phone 手机号码
     * @param code 短信验证码
     * @param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone, String code, LogInListener<IMUser> listener){
        BmobUser.signOrLoginByMobilePhone(phone,code,listener);
    }

    /**
     * 获取本地对象
     * @return
     */
    public IMUser getUser(){
        return BmobUser.getCurrentUser(IMUser.class);
    }
}
