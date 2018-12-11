package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.presenter.FingerPointPresent;

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
                Toast.makeText(this, "已开启指纹识别", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "此设备不支持指纹功能", Toast.LENGTH_SHORT).show();
                switchCompat.setChecked(false);
            }
        }else{

        }
    }
}
