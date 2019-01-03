package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.presenter.FingerPointPresent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FingerPointActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    private FingerPointPresent fingerPointPresent;

    private Toolbar toolbarFinger;              //toolbar
    private SwitchCompat switchCompat;          //switch按键
    
    private TextView supportCheck;              //检测是否支持指纹
    private TextView fingerPointSetting;        //检测是否设置指纹

    private String isFingerPointAuthOpen;       //是否开启指纹

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_point);

        fingerPointPresent = new FingerPointPresent(this);

        //获取控件
        toolbarFinger = findViewById(R.id.toolbar_fingerPoint);
        switchCompat = findViewById(R.id.switch_fingerPoint);
        supportCheck = findViewById(R.id.support_check);
        fingerPointSetting = findViewById(R.id.fingerPoint_setting);

        //设置toolbar
        setToolbar(toolbarFinger);

        //设置点击事件
        switchCompat.setOnCheckedChangeListener(this);
        supportCheck.setOnClickListener(this);
        fingerPointSetting.setOnClickListener(this);

        initView();

    }

    //初始化控件
    private void initView(){
        //从cache获取指纹开启状态并设置switch
        isFingerPointAuthOpen = getCacheDataOfFingerPointAuth();
        if(isFingerPointAuthOpen.equals("false")){
            switchCompat.setChecked(false);
        }else if(isFingerPointAuthOpen.equals("true")){
            switchCompat.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.support_check:{
                if(fingerPointPresent.isSupport()){
                    Toast.makeText(this, "此设备支持指纹功能", Toast.LENGTH_SHORT).show(); 
                }else{
                    Toast.makeText(this, "此设备不支持指纹功能", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.fingerPoint_setting:{
                fingerPointSetting();
                break;
            }
            default:
                break;
        }
    }

    //跳转到设置指纹的界面
    private void fingerPointSetting(){
        Intent intent = new Intent("android.settings.SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //从cache中获取是否开启了指纹
    private String getCacheDataOfFingerPointAuth(){
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            //存储是否开启了指纹的文件
            String fingerPointFile = getExternalCacheDir().getAbsolutePath() + File.separator + "isFingerPointAuthOpen.txt";
            File file = new File(fingerPointFile);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(fingerPointFile);
                    outputStream.write("false".getBytes());
                    outputStream.close();
                    return "false";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //读取文件内容
            StringBuilder stringBuilder = new StringBuilder("");

            try {
                FileInputStream fileInputStream = new FileInputStream(fingerPointFile);
                byte[] buffer = new byte[10];
                int len = fileInputStream.read(buffer);
                if(len>0) stringBuilder.append(new String(buffer,0,len));
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }
        return "false";
    }


    //将指纹开启状态存储到cache
    private void setCacheDataOfFingerPointAuth(String state){
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            //存储是否开启了指纹的文件
            String fingerPointFile = getExternalCacheDir().getAbsolutePath() + File.separator + "isFingerPointAuthOpen.txt";
            File file = new File(fingerPointFile);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //将指纹开启状态存储
            try {
                FileOutputStream outputStream = new FileOutputStream(fingerPointFile);
                outputStream.write(state.getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //设置左上角的返回事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //switch按键发生改变
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked && isFingerPointAuthOpen.equals("false")){
            //判断是否支持
            if(fingerPointPresent.isSupport()){
                //判断是否设置指纹
                if (!fingerPointPresent.isHasEnrolledFingerprints()) {
                    Toast.makeText(this, "尚未设置指纹，请设置", Toast.LENGTH_SHORT).show();
                    fingerPointSetting();
                    switchCompat.setChecked(false);
                    return;
                }
                
                setCacheDataOfFingerPointAuth("true");
                Toast.makeText(this, "指纹识别已开启", Toast.LENGTH_SHORT).show();
                
            }else{
                Toast.makeText(this, "此设备不支持指纹功能", Toast.LENGTH_SHORT).show();
                switchCompat.setChecked(false);
            }
        }else{
            //将关闭指纹的信息存储到cache文件
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {

                String fingerPointFile = getExternalCacheDir().getAbsolutePath() + File.separator + "isFingerPointAuthOpen.txt";
                File file = new File(fingerPointFile);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //存储false
                try {
                    FileOutputStream outputStream = new FileOutputStream(fingerPointFile);
                    outputStream.write("false".getBytes());
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
