package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.User;
import com.example.ado.cookbookuser.view.Fragment.CreateFragment;
import com.example.ado.cookbookuser.view.Fragment.FavFragment;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class UserActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbarUser;                                   //UserActivity的toolbar
    private ImageView userHandShot;                                //用户头像
    private CollapsingToolbarLayout collapsingToolbarLayout;       //折叠栏
    private AppBarLayout appBarLayout;                             //appBar
    private TextView headerTitle;                                       //顶部标题
    private FloatingActionButton fabEditInformation;               //编辑信息按键
    private ViewPager viewPager;                                   //收藏和创建的食谱pager
    private List<Fragment> pagers;                                     //viewPager的页面
    private Button tabFavorite;                                    //收藏tab
    private Button tabCreate;                                      //创建的食谱tab

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
        viewPager = findViewById(R.id.fav_create_view_pager);
        tabFavorite = findViewById(R.id.tab_favorite);
        tabCreate = findViewById(R.id.tab_create);

        //设置toolbar
        setSupportActionBar(toolbarUser);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //collapsingToolbarLayout.setTitle(myself.getName());

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

        //设置监听事件
        fabEditInformation.setOnClickListener(this);
        tabFavorite.setOnClickListener(this);
        tabCreate.setOnClickListener(this);

        initPager();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //登录状态
        if(BaseActivity.userForNow != null){
            byte[] userForNowHeadShot = BaseActivity.userForNow.getHeadShot();
            Bitmap bitmap = BitmapFactory.decodeByteArray(userForNowHeadShot,0,userForNowHeadShot.length);
            Glide.with(UserActivity.this).load(bitmap).into(userHandShot);
        }else{
            Glide.with(UserActivity.this).load(R.drawable.pic_default).into(userHandShot);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab_edit_information:{
                Intent intent = new Intent(UserActivity.this,EditUserActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tab_create:{
                viewPager.setCurrentItem(1);
                break;
            }
            case R.id.tab_favorite:{
                viewPager.setCurrentItem(0);
                break;
            }
            default:
                break;
        }
    }

    private void initPager(){
        pagers = new ArrayList<>();
        pagers.add(new FavFragment());
        pagers.add(new CreateFragment());

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return pagers.get(i);
            }

            @Override
            public int getCount() {
                return pagers.size();
            }
        };

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i == 0){
                    tabFavorite.setBackgroundResource(R.drawable.tab_border);
                    tabCreate.setBackgroundResource(R.drawable.tab_default);
                }else if(i == 1){
                    tabCreate.setBackgroundResource(R.drawable.tab_border);
                    tabFavorite.setBackgroundResource(R.drawable.tab_default);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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

                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
