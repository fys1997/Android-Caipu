package com.example.ado.cookbookuser.model;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.example.ado.cookbookuser.view.BaseActivity;

import java.util.List;

public class LightSensorUtil implements SensorEventListener {

    private BaseActivity baseActivity;
    private SensorManager manager;
    private Context mContext;

    public LightSensorUtil(Context context, BaseActivity baseActivity){
        this.mContext = context;
        this.baseActivity = baseActivity;



        init();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            if(event.values[0] < 400.0f) {
                baseActivity.onLightSensorChanged();
                manager.unregisterListener(this);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void init(){
        manager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (isSupportLightSensor()){
            registerLightSensor();
        }else{
            Toast.makeText(mContext, "该设备不支持光线传感器", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isSupportLightSensor(){
        List<Sensor> sensorList = manager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor:sensorList){
            if(Sensor.TYPE_LIGHT == sensor.getType()){
                return true;
            }
        }
        return false;
    }

    private void registerLightSensor(){
        if(manager != null) {
            Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if(sensor != null && isSupportLightSensor()){
                manager.registerListener( this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
            }
        }
    }


}
