package com.example.meet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framework.base.BaseUIActivity;
import com.example.framework.bmob.BmobManager;
import com.example.framework.bmob.IMUser;
import com.example.framework.entity.Constants;
import com.example.framework.manager.DialogManager;
import com.example.framework.utils.SpUtils;
import com.example.framework.view.DialogView;
import com.example.framework.view.LoadingView;
import com.example.framework.view.TouchPictureV;
import com.example.meet.MainActivity;
import com.example.meet.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * 登录页
 */
public class LoginActivity extends BaseUIActivity implements View.OnClickListener {

    /**
     * 1.点击发送按钮，弹出提示框，图片验证码，验证通过后
     * 2.发送验证码，同时按钮变成不可点击，按钮倒计时，倒计时结束，按钮可点击，文字变成“发送”
     * 3.通过手机号和验证码进行登录
     * 4.登陆成功后获取本地对象
     */

    private EditText et_phone;
    private EditText et_code;
    private Button btn_send_code;
    private Button btn_login;
    private TextView tv_test_login;
    private TextView tv_user_agreement;

    private DialogView mCodeView;
    private TouchPictureV mPictureV;

    private LoadingView mLoadingView;

    private static final int H_TIME = 1001;
    private static int TIME = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case H_TIME:
                    TIME--;
                    btn_send_code.setText(TIME + "s");
                    if (TIME > 0){
                        mHandler.sendEmptyMessageDelayed(H_TIME,1000);
                    }else {
                        btn_send_code.setEnabled(true);
                        btn_send_code.setText(getString(R.string.text_login_send));
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_send_code = (Button) findViewById(R.id.btn_send_code);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_test_login = (TextView) findViewById(R.id.tv_test_login);
        tv_user_agreement = (TextView) findViewById(R.id.tv_user_agreement);

        btn_send_code.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        initDialogView();
        String phone = SpUtils.getInstance().getString(Constants.SP_PHONE,"");
        if (!TextUtils.isEmpty(phone)){
            et_phone.setText(phone);
        }
    }

    private void initDialogView(){

        mLoadingView = new LoadingView(this);
        mCodeView = DialogManager.getInstance().initView(this,R.layout.dialog_code_view);
        mPictureV = mCodeView.findViewById(R.id.mPictureV);
        mPictureV.setViewResultListener(new TouchPictureV.OnViewResultListener() {
            @Override
            public void onResult() {
                sendSMS();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_code:
                DialogManager.getInstance().show(mCodeView);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_test_login:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }

    private void login(){
        //1.判断手机号和验证码不为空
        final String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null), Toast.LENGTH_SHORT).show();
            return;
        }
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, getString(R.string.text_login_code_null), Toast.LENGTH_SHORT).show();
            return;
        }

        //显示Loading
        mLoadingView.show("正在登录...");
        BmobManager.getInstance().signOrLoginByMobilePhone(phone, code, new LogInListener<IMUser>() {
            @Override
            public void done(IMUser imUser, BmobException e) {
                if (e == null){
                    //登陆成功
                    mLoadingView.hide();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //把手机号码保存下来
                    SpUtils.getInstance().putString(Constants.SP_PHONE,phone);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "ERROR:" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /**
     * 发送短信验证码
     */
    private void sendSMS(){
        //1.获取手机号码
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null), Toast.LENGTH_SHORT).show();
            return;
        }
        //2.请求短信验证码
        BmobManager.getInstance().requestSMS(phone, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null){
                    btn_send_code.setEnabled(false);
                    mHandler.sendEmptyMessage(H_TIME);
                    DialogManager.getInstance().hide(mCodeView);
                    Toast.makeText(LoginActivity.this, "短信验证码发送成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "短信验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
