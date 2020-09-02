package com.example.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * 沉浸式实现
 */
public class SystemUI {

    public static void fixSystemUI(Activity mActivity) {
        //Android 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //获取最顶层的View
            mActivity.getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            mActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
