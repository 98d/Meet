package com.example.framework.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类
 * 所有的统一功能： 语言切换， 请求权限
 */
public class BaseActivity extends AppCompatActivity {

    //声明所需权限组
    private String[] mStrPermission = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    //保存没有同意的权限
    private List<String> mPerList = new ArrayList<>();
    //保存没有同意的失败权限
    private List<String> mPerNoList = new ArrayList<>();
    private int requestCode;
    private OnPermissionResult permissionResult;

    /**
     * 一个方法请求权限
     * @param requestCode
     * @param permissionResult
     */
    protected void request(int requestCode,OnPermissionResult permissionResult){
        if (!checkedPermissionAll()){
            requestPermissionAll(requestCode, permissionResult);
        }
    }
    /**
     * 判断单个权限
     * @param permissions
     * @return
     */
    protected boolean checkedPermissions(String permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(permissions);
            return check == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * 判断是否需要申请权限
     * @return
     */
    protected boolean checkedPermissionAll(){
        mPerList.clear();
        for (int i = 0; i < mStrPermission.length; i ++){
            boolean check = checkedPermissions(mStrPermission[i]);
            //如果不同意则请求
            if (!check){
                mPerList.add(mStrPermission[i]);
            }
        }
        return mPerList.size() > 0 ? false : true;
    }

    /**
     * 请求权限
     * @param mPermissions
     * @param requestCode
     */
    protected void requestPermission(String[] mPermissions,int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(mPermissions,requestCode);
        }
    }

    /**
     * 申请所有权限
     * @param requestCode
     */
    protected void requestPermissionAll(int requestCode,OnPermissionResult permissionResult){
        this.requestCode = requestCode;
        this.permissionResult = permissionResult;
        requestPermission((String[]) mPerList.toArray(new String[mPerList.size()]),requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPerNoList.clear();
        if (requestCode == requestCode){
            if (grantResults.length > 0){
                for (int i = 0; i < grantResults.length; i ++){
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                        //你有失败的权限
                        mPerNoList.add(permissions[i]);
                    }
                }
                if (permissionResult != null){
                    if (mPerNoList.size() == 0){
                        permissionResult.OnSuccess();
                    }else {
                        permissionResult.OnFail(mPerNoList);
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected interface OnPermissionResult{
        void OnSuccess();
        void OnFail(List<String> noPermissons);
    }

    /**
     * 判断窗口权限
     * @return
     */
    protected boolean checkedWindowPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        }
        return true;
    }

    /**
     * 请求窗口权限
     */
    protected void requestWindowPermissons(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }
}
