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

    private Toolbar toolbarSetting;
    private LinearLayout fingerPointSetting;
    private LinearLayout lightSensorSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbarSetting = findViewById(R.id.toolbar_setting);
        fingerPointSetting = findViewById(R.id.finger_point_setting);
        lightSensorSetting = findViewById(R.id.light_sensor_setting);

        fingerPointSetting.setOnClickListener(this);
        lightSensorSetting.setOnClickListener(this);

        setToolbar(toolbarSetting);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.finger_point_setting:{
                Intent intent = new Intent(this,FingerPointActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.light_sensor_setting:{
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
