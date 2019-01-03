package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;

public class UserActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbarUser;                                   //UserActivity的toolbar
    private ImageView userHandShot;                                //用户头像
    private CollapsingToolbarLayout collapsingToolbarLayout;       //折叠栏
    private AppBarLayout appBarLayout;                             //appBar
    private TextView headerTitle;                                       //顶部标题
    private FloatingActionButton fabEditInformation;               //编辑信息按键
    private TextView tabCreate;                                      //创建的食谱tab
    private TextView myUserName;
    private TextView myBirthday;
    private TextView myGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        toolbarUser = findViewById(R.id.toolbar_user);
        userHandShot = findViewById(R.id.user_headShot);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout = findViewById(R.id.appBar);
        headerTitle = findViewById(R.id.header);
        fabEditInformation = findViewById(R.id.fab_edit_information);
        tabCreate = findViewById(R.id.tab_create);
        myUserName = findViewById(R.id.my_user_name);
        myBirthday = findViewById(R.id.my_birthday);
        myGender = findViewById(R.id.my_gender);

        setToolbar(toolbarUser);

        //设置监听事件
        fabEditInformation.setOnClickListener(this);
        tabCreate.setOnClickListener(this);

        //设置折叠事件
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if(Math.abs(i) >= appBarLayout.getTotalScrollRange()){
                    headerTitle.setVisibility(View.VISIBLE);
                }else{
                    headerTitle.setVisibility(View.GONE);
                }
            }
        });



    }

    private void initMyInformation(){
        myUserName.setText(BaseActivity.userForNow.getName().toString());
        myBirthday.setText(BaseActivity.userForNow.getBirthday().toString());
        myGender.setText(BaseActivity.userForNow.getGender().toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initMyInformation();
        initWidget();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab_edit_information:{
                Intent intent = new Intent(UserActivity.this,EditUserActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }

    private void initWidget(){
        //登录状态
        if(BaseActivity.userForNow != null){
            byte[] userForNowHeadShot = BaseActivity.userForNow.getHeadShot();
            Bitmap bitmap = BitmapFactory.decodeByteArray(userForNowHeadShot,0,userForNowHeadShot.length);
            Glide.with(UserActivity.this).load(bitmap).into(userHandShot);
        }else{
            Glide.with(UserActivity.this).load(R.drawable.pic).into(userHandShot);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_user,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.add:{
                Intent intent = new Intent(this,CreateCookbookActivity.class);
                startActivity(intent);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
