package com.example.meet.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.example.framework.base.BaseUIActivity;
import com.example.framework.view.TouchPictureV;
import com.example.meet.R;

public class TestActivity extends BaseUIActivity {


    private TouchPictureV test_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initView();
    }

    private void initView() {
        test_view = (TouchPictureV) findViewById(R.id.test_view);
        test_view.setViewResultListener(new TouchPictureV.OnViewResultListener() {
            @Override
            public void onResult() {
                Toast.makeText(TestActivity.this,"通过",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
