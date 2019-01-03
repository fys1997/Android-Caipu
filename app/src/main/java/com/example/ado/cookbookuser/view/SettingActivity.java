package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbarSetting;                 //toolbar
    private LinearLayout fingerPointSetting;        //开启指纹功能对应的布局
    private LinearLayout lightSensorSetting;        //光感功能对应的布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //获取控件
        toolbarSetting = findViewById(R.id.toolbar_setting);
        fingerPointSetting = findViewById(R.id.finger_point_setting);
        lightSensorSetting = findViewById(R.id.light_sensor_setting);

        //设置点击事件
        fingerPointSetting.setOnClickListener(this);
        lightSensorSetting.setOnClickListener(this);

        //设置toolbar
        setToolbar(toolbarSetting);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.finger_point_setting:{
                //跳转到指纹设置界面
                Intent intent = new Intent(this,FingerPointActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.light_sensor_setting:{
                //光感功能
                isLightSensorOpen = !isLightSensorOpen;
                if(isLightSensorOpen){
                    Toast.makeText(this, "光照感应已开启", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "光照感应已关闭", Toast.LENGTH_SHORT).show();
                }
                break;
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
}
