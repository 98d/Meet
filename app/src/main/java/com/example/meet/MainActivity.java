package com.example.meet;

import android.os.Bundle;
import android.widget.Toast;

import com.example.framework.base.BaseUIActivity;
import com.example.framework.bmob.BmobManager;
import com.example.framework.bmob.IMUser;


public class MainActivity extends BaseUIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IMUser imUser = BmobManager.getInstance().getUser();
        Toast.makeText(this,"imUser:" + imUser.getMobilePhoneNumber(),Toast.LENGTH_SHORT).show();
    }
}