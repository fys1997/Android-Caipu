package com.example.ado.cookbookuser.view;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.presenter.StepPresenter;
import com.example.ado.cookbookuser.view.Interface.StepViewI;

import java.util.ArrayList;

public class StepActivity extends BaseActivity implements StepViewI,SensorEventListener {
    private LinearLayout linearLayout;
    private Intent intent;
    private String menu;//记录保存要查看的菜谱名
    private TextView textView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private StepPresenter stepPresenter;//该activity对应的present接口
    private Toolbar toolbarStep;
    private int type;
    private SensorManager sensorManager;
    //private TextView textView;
    private Sensor sensor;

    private static final int INTERVAL_TIME = 70;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclickview);

        toolbarStep = findViewById(R.id.toolbar_step);

        //设置toolbar
        setSupportActionBar(toolbarStep);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent=getIntent();
        menu=intent.getStringExtra("name");//此处可能为menu与id
        type=Integer.parseInt(intent.getStringExtra("type"));
        stepPresenter=new StepPresenter(this);
        imageView=(ImageView)findViewById(R.id.itemClickImageView);
        textView=(TextView)findViewById(R.id.RItemText);
        //recyclerView=(RecyclerView)findViewById(R.id.menuRecyclerView);
        if(type==0)
        stepPresenter.operation(menu);
        else
        stepPresenter.anotherOperation(Integer.parseInt(menu));

        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastTime = System.currentTimeMillis();
    }

    //初始化这个新界面，当可以找到相关数据的时候
    @Override
    public void initStepUI(ArrayList<RecyclerItem> datas,String imageurl){
        Glide.with(this.getBaseContext()).load(imageurl).into(imageView);
        if(type==0)
        textView.setText(menu);
        else
            textView.setText(datas.get(0).getTitlle());
        linearLayout=findViewById(R.id.linerlayout);
        for(int i=0;i<datas.size();i++){
            View Sview= getLayoutInflater().inflate(R.layout.menu_item,null).findViewById(R.id.line);
            TextView StextView=getLayoutInflater().inflate(R.layout.menu_item,null).findViewById(R.id.menuItemTextView);
            ImageView SimageView=getLayoutInflater().inflate(R.layout.menu_item,null).findViewById(R.id.menuItemImageview);

            ViewGroup.LayoutParams layoutParams;
            layoutParams=Sview.getLayoutParams();
            //灰线
            View Mview=new View(this);
            Mview.setLayoutParams(layoutParams);
           Mview.setBackground(Sview.getBackground());
           //imageview
            ImageView Mimageview=new ImageView(this);
            layoutParams=SimageView.getLayoutParams();
            Mimageview.setLayoutParams(layoutParams);
            Mimageview.setMaxHeight(SimageView.getMaxHeight());
            Mimageview.setMaxWidth(SimageView.getMaxWidth());
            Glide.with(this.getBaseContext()).load(datas.get(i).getImgsrc()).into(Mimageview);
            //textview
            TextView Mtextview=new TextView(this);
            layoutParams=StextView.getLayoutParams();
            Mtextview.setLayoutParams(layoutParams);
            Mtextview.setTextColor(StextView.getTextColors());
            Mtextview.setTextSize(StextView.getTextSize());
            Mtextview.setText(datas.get(i).getDescription());
            linearLayout.addView(Mview);
            linearLayout.addView(Mimageview);
            linearLayout.addView(Mtextview);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener( this,sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(stepPresenter!=null){
            stepPresenter.destroy();
            stepPresenter=null;
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
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - lastTime;

        if (timeInterval < INTERVAL_TIME) return;

        float[] values = event.values;

        if (values[0] > 15){
            //想左摇晃
        }
        else if(values[0] < -15){
            //向右摇晃
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
