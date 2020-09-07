package com.example.framework.base;

import android.os.Bundle;

import com.example.framework.utils.SystemUI;

/**
 * 单一功能：沉浸式
 */
public class BaseUIActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SystemUI.fixSystemUI(this);
    }
}

