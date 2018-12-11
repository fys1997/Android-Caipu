package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.presenter.FingerPointPresent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FingerPointActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{

    private FingerPointPresent fingerPointPresent;

    private Toolbar toolbarFinger;
    private SwitchCompat switchCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_point);

        fingerPointPresent = new FingerPointPresent(this);

        toolbarFinger = findViewById(R.id.toolbar_fingerPoint);
        switchCompat = findViewById(R.id.switch_fingerPoint);

        //设置toolbar
        setSupportActionBar(toolbarFinger);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        switchCompat.setOnCheckedChangeListener(this);

        initView();

    }

    private void initView(){
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
                    return;
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

            String isFingerPointAuthOpen = stringBuilder.toString();

            if(isFingerPointAuthOpen.equals("false")){
                switchCompat.setChecked(false);
            }else if(isFingerPointAuthOpen.equals("true")){
                switchCompat.setChecked(true);
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
        if(isChecked){
            if(fingerPointPresent.isSupport()){
                if (!fingerPointPresent.isHasEnrolledFingerprints()) {
                    Toast.makeText(this, "尚未设置指纹，请设置", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("android.settings.SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    switchCompat.setChecked(false);
                    return;
                }


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
                        outputStream.write("true".getBytes());
                        outputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Toast.makeText(this, "指纹识别", Toast.LENGTH_SHORT).show();
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
