package com.example.framework.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.utils.SystemUI;

public class BaseUIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SystemUI.fixSystemUI(this);
    }
}

