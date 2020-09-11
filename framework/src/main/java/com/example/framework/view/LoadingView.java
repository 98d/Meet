package com.example.framework.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framework.R;
import com.example.framework.manager.DialogManager;
import com.example.framework.utils.AnimUtils;

/**
 * 加载提示框
 */
public class LoadingView {

    private DialogView mLoadingView;
    private ImageView iv_loading;
    private TextView tv_lodaing_text;
    private ObjectAnimator mAnim;

    public LoadingView(Context mContext){
        mLoadingView = DialogManager.getInstance().initView(mContext, R.layout.dialog_loading);
        iv_loading = mLoadingView.findViewById(R.id.iv_loading);
        tv_lodaing_text = mLoadingView.findViewById(R.id.tv_lodaing_text);
        mAnim = AnimUtils.rotation(iv_loading);
    }

    public void setLoadingText(String text){
        if (!TextUtils.isEmpty(text)){
            tv_lodaing_text.setText(text);
        }
    }

    public void show(){
        mAnim.start();
        DialogManager.getInstance().show(mLoadingView);
    }

    public void show(String text){
        mAnim.start();
        setLoadingText(text);
        DialogManager.getInstance().show(mLoadingView);
    }

    public void hide(){
        mAnim.pause();
        DialogManager.getInstance().hide(mLoadingView);
    }
}
