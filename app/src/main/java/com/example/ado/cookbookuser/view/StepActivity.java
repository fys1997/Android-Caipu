package com.example.ado.cookbookuser.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.CreateStoreUtil;
import com.example.ado.cookbookuser.model.FavCookBook;
import com.example.ado.cookbookuser.model.FavStoreUtil;
import com.example.ado.cookbookuser.presenter.StepPresenter;
import com.example.ado.cookbookuser.view.Interface.StepViewI;
import java.util.ArrayList;

public class StepActivity extends BaseActivity implements StepViewI,SensorEventListener {
    private  int menuId;
    private boolean islike=false;
    private int linearlayoutViewSize=0;
    private boolean isSensor=true;
    private LinearLayout linearLayout;
    private Intent intent;
    private String menu;//记录保存要查看的菜谱名
    private TextView textView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private StepPresenter stepPresenter;//该activity对应的present接口
    private Toolbar toolbarStep;
    private ArrayList<String>menuNames;//记录了列表菜谱名
    private int position;//此菜谱在之前界面list中的位置
    private ArrayList<Integer>menuIDs;//记录了列表菜谱ID
    private int type;
    private SensorManager sensorManager;
    private FloatingActionButton likeButton;
    //private TextView textView;
    private Sensor sensor;

    private static final int INTERVAL_TIME = 700;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclickview);

        toolbarStep = findViewById(R.id.toolbar_step);

        setToolbar(toolbarStep);

        intent = getIntent();
        menu=intent.getStringExtra("name");//此处可能为menu与id
        position=Integer.parseInt(intent.getStringExtra("position"));
        type=Integer.parseInt(intent.getStringExtra("type"));
        stepPresenter=new StepPresenter(this);
        imageView=(ImageView)findViewById(R.id.itemClickImageView);
        textView=(TextView)findViewById(R.id.RItemText);
        likeButton=(FloatingActionButton)findViewById(R.id.like);
        //recyclerView=(RecyclerView)findViewById(R.id.menuRecyclerView);
        if(type==0) {
            stepPresenter.operation(menu);
            menuNames=intent.getStringArrayListExtra("list");
        }
        else {
            stepPresenter.anotherOperation(Integer.parseInt(menu));
            menuIDs=intent.getIntegerArrayListExtra("list");
        }

        initSensor();
        setLikeButtonClickListener();
    }

    private void initSensor(){
        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastTime = System.currentTimeMillis();
    }

    //初始化这个新界面，当可以找到相关数据的时候
    @Override
    public void initStepUI(ArrayList<RecyclerItem> datas,String imageurl){
        Glide.with(this.getBaseContext()).load(imageurl).into(imageView);
        if(type==0)
        textView.setText(datas.get(0).getTitlle());
        else
            textView.setText(datas.get(0).getTitlle());
        menu=datas.get(0).getTitlle();
        menuId=datas.get(0).getId();
        if(FavStoreUtil.isCookbookInFav(menuId)){
            islike = true;
            Glide.with(getBaseContext()).load(R.drawable.like_red).into(likeButton);
        }
        linearLayout=findViewById(R.id.linerlayout);
        if(linearlayoutViewSize!=0)
        linearLayout.removeViews(4,linearlayoutViewSize);
        linearlayoutViewSize=0;
        for (int i=0;i<datas.get(0).getIngredientsName().size();i++){
            View lineview=getLayoutInflater().inflate(R.layout.menu_item,null).findViewById(R.id.lineIngredients);
            TextView lineTextView=getLayoutInflater().inflate(R.layout.menu_item,null).findViewById(R.id.ingredients);
            ViewGroup.LayoutParams layoutParams;
            //灰线
            layoutParams=lineview.getLayoutParams();
            View Mview=new View(this);
            Mview.setLayoutParams(layoutParams);
            Mview.setBackground(lineview.getBackground());
            //加textView
            TextView inTextView=new TextView(this);
            layoutParams=lineTextView.getLayoutParams();
            inTextView.setLayoutParams(layoutParams);
            inTextView.setTextColor(lineTextView.getTextColors());
            //此处加入内容
            String content=datas.get(0).getIngredientsName().get(i);
            content += ":  ";
            content=content+datas.get(0).getIngredientsNumber().get(i);
            inTextView.setText(content);
            inTextView.setTextSize(lineTextView.getTextSize());
            //加入view
            linearLayout.addView(Mview);
            linearLayout.addView(inTextView);
            linearlayoutViewSize=linearlayoutViewSize+2;

        }
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
            linearlayoutViewSize=linearlayoutViewSize+3;
            isSensor=true;
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
        if(islike && !FavStoreUtil.isCookbookInFav(menuId))
            FavStoreUtil.saveCookbookToFav(menuId);
        else if(!islike&&FavStoreUtil.isCookbookInFav(menuId)){
            FavStoreUtil.deleteByCookId(menuId);
        }
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
        if(isSensor) {

            if (values[0] < -15) {
                if (type == 0) {
                    position--;
                    if (position < 0)
                        position = menuNames.size() - 1;
                    menu=menuNames.get(position);
                    stepPresenter.operation(menuNames.get(position));
                    isSensor=false;
                } else {
                    position--;
                    if (position < 0)
                        position = menuIDs.size()-1;
                    stepPresenter.anotherOperation(menuIDs.get(position));
                    isSensor=false;
                }
                event.values[0] = 0;
                //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                //想左摇晃
            } else if (values[0] > 15) {
                if (type == 0) {
                    position++;
                    position = position % menuNames.size();
                    menu=menuNames.get(position);
                    stepPresenter.operation(menuNames.get(position));
                    isSensor=false;
                } else {
                    position++;
                    position = position % menuIDs.size();
                    stepPresenter.anotherOperation(menuIDs.get(position));
                    isSensor=false;
                }
                event.values[0] = 0;
                //向右摇晃
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setLikeButtonClickListener(){
        likeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(BaseActivity.userForNow == null){
                    Toast.makeText(StepActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StepActivity.this,LoginActivity.class));
                    return;
                }
                if(islike) {
                    Glide.with(getBaseContext()).load(R.drawable.like).into(likeButton);
                    Toast.makeText(StepActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    //FavStoreUtil.deleteCookbookFromFav();
                    islike=false;
                }
                else {
                    Glide.with(getBaseContext()).load(R.drawable.like_red).into(likeButton);
                    Toast.makeText(StepActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    //FavStoreUtil.saveCookbookToFav(menu);
                    islike=true;
                }
            }
        });
    }


}
