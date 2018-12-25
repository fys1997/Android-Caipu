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

    private Toolbar toolbarFinger;
    private SwitchCompat switchCompat;
    
    private TextView supportCheck;
    private TextView fingerPointSetting;

    private String isFingerPointAuthOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_point);

        fingerPointPresent = new FingerPointPresent(this);

        toolbarFinger = findViewById(R.id.toolbar_fingerPoint);
        switchCompat = findViewById(R.id.switch_fingerPoint);
        supportCheck = findViewById(R.id.support_check);
        fingerPointSetting = findViewById(R.id.fingerPoint_setting);

        setToolbar(toolbarFinger);

        switchCompat.setOnCheckedChangeListener(this);
        supportCheck.setOnClickListener(this);
        fingerPointSetting.setOnClickListener(this);

        initView();

    }

    private void initView(){
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

    private void fingerPointSetting(){
        Intent intent = new Intent("android.settings.SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    
    private String getCacheDataOfFingerPointAuth(){
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

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
    
    private void setCacheDataOfFingerPointAuth(String state){
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked && isFingerPointAuthOpen.equals("false")){
            if(fingerPointPresent.isSupport()){
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
