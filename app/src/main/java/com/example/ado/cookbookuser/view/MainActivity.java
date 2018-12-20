package com.example.ado.cookbookuser.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.presenter.MainPresenter;
import com.example.ado.cookbookuser.view.Fragment.MainFragment;
import com.example.ado.cookbookuser.view.Interface.MainViewI;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;
import com.example.ado.cookbookuser.view.adapter.MFragmentAdapter;
import com.example.ado.cookbookuser.view.adapter.MRecyclerAdapter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
View.OnClickListener,MainViewI
{
    private DrawerLayout drawerLayout;                   //侧边栏
    private NavigationView navigationView;               //侧边栏导航栏
    private View navLayout;                              //侧边栏导航栏头部布局
    private Menu navMenu;                                //侧边栏导航栏菜单
    private CircleImageView navCircleImageView;          //侧边栏导航栏头部布局的头像
    private TextView navUserName;                        //侧边栏导航栏头部布局的用户名
    private Toolbar toolbarMain;                         //MainActivity的toolbar
    private TextView quitUser;                           //退出登录按键
    private FloatingActionButton fabUser;                //右下方用户按键
    private EditText searchEdit;                         //搜索栏

    private TabLayout tabLayout;
    private ArrayList<String>tabdata=new ArrayList<>();
    private MainPresenter mainPresenter;
    private ViewPager viewPager;
    private ArrayList<Fragment>fragments=new ArrayList<>();
    private MFragmentAdapter pagerAdapter;
    private MRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private int position=0;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter=new MainPresenter(this);

        //创建本地数据库
        LitePal.getDatabase();

        quitUser = findViewById(R.id.quit_user);
        toolbarMain = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_user_view);
        fabUser = findViewById(R.id.fab_user);
        navLayout = navigationView.getHeaderView(0);
        navCircleImageView = navLayout.findViewById(R.id.nav_user_headShot);
        navUserName = navLayout.findViewById(R.id.nav_user_name);
        navMenu = navigationView.getMenu();
        tabLayout=findViewById(R.id.tabLayout);
        searchEdit = findViewById(R.id.search_edit);

        initLayout();

        //设置按键监听
        quitUser.setOnClickListener(this);
        fabUser.setOnClickListener(this);
        navCircleImageView.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        //初始化用户数据库
//        DataSupport.deleteAll(User.class);
//        String userForNowFile = getExternalCacheDir().getAbsolutePath()  + File.separator + "userForNowFile.txt";
//        File file = new File(userForNowFile);
//        if (file.exists()){
//            file.delete();
//            try{
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //初始化界面
    private void initLayout(){
        //设置toolbar
        setSupportActionBar(toolbarMain);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        //菜谱界面展示逻辑
        initTabData();
        initTab(tabdata);
        initViewPager();
        initTabListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //隐藏侧边栏
        drawerLayout.closeDrawers();
        searchEdit.setFocusableInTouchMode(false);
        searchEdit.setFocusable(false);
        setOnsearchEditClick();

        //登录状态
        if(BaseActivity.userForNow != null) {

            quitUser.setVisibility(View.VISIBLE);
            fabUser.show();

            byte[] userForNowHeadShot = BaseActivity.userForNow.getHeadShot();
            Bitmap bitmap = BitmapFactory.decodeByteArray(userForNowHeadShot,0,userForNowHeadShot.length);
            Glide.with(MainActivity.this).load(bitmap).into(navCircleImageView);
            navUserName.setText(BaseActivity.userForNow.getName());

            navMenu.findItem(R.id.nav_myFavorite).setVisible(true);
            navMenu.findItem(R.id.nav_myCookbook).setVisible(true);
            navMenu.findItem(R.id.nav_setting).setVisible(true);
            navMenu.findItem(R.id.nav_switch).setVisible(true);
            navMenu.findItem(R.id.nav_exit).setVisible(true);
            navMenu.findItem(R.id.nav_changePwd).setVisible(true);
        }
        else{
            quitUser.setVisibility(View.GONE);
            fabUser.hide();

            Glide.with(MainActivity.this).load(R.drawable.pic).into(navCircleImageView);
            navUserName.setText("未登录");

            navMenu.findItem(R.id.nav_myFavorite).setVisible(false);
            navMenu.findItem(R.id.nav_myCookbook).setVisible(false);
            //navMenu.findItem(R.id.nav_setting).setVisible(false);
            navMenu.findItem(R.id.nav_switch).setVisible(false);
            navMenu.findItem(R.id.nav_changePwd).setVisible(false);
            //navMenu.findItem(R.id.nav_exit).setVisible(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_user:{
                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.quit_user:{
                userOut("退出登录","您确定要退出登录吗?");
                break;
            }
            case R.id.nav_user_headShot:{
                if(BaseActivity.userForNow == null){
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this,UserActivity.class);
                    startActivity(intent);
                }
                break;
            }
            default:
                break;
        }
    }

    //menu onclick

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_myFavorite:{

                break;
            }
            case R.id.nav_myCookbook:{

                break;
            }
            case R.id.nav_setting:{

                break;
            }
            case R.id.nav_dark_theme:{
                Intent intent = new Intent(this,ThemeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_changePwd:{
                Intent intent = new Intent(this,ChangePwdActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_fingerPoint:{
                Intent intent = new Intent(MainActivity.this,FingerPointActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_switch:{
                userOut("切换帐号","您确定要切换帐号吗?");
                break;
            }
            case R.id.nav_exit:{
                finish();
                break;
            }

        }

        return true;
    }

    private void userOut(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userForNowFile = getExternalCacheDir().getAbsolutePath()  + File.separator + "userForNowFile.txt";
                File file = new File(userForNowFile);
                if (file.exists()){
                    file.delete();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BaseActivity.userForNow = null;
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
    }

    @Override
    public void setRecyclerItemData(ArrayList<RecyclerItem>data) {
        recyclerView=fragments.get(position).getView().findViewById(R.id.Frecyclerview);
        recyclerAdapter=(MRecyclerAdapter)recyclerView.getAdapter();
        recyclerAdapter.addData(data);
        recyclerAdapter.notifyDataSetChanged();
        setRecyclerItemClickListener();
        if (pullToRefreshLayout!=null)
            pullToRefreshLayout.finishLoadMore();
    }

    public void initTabData() {
        tabdata.add("家常菜");
        tabdata.add("快手菜");
        tabdata.add("创意菜");
        tabdata.add("素菜");
        tabdata.add("凉菜");
        tabdata.add("烘焙");
        tabdata.add("面食");
        tabdata.add("汤");
        tabdata.add("自制调味料");
    }

    public void initTab(ArrayList<String>tabdata) {
        for(int i=0;i<tabdata.size();i++)
        {
            tabLayout.addTab(tabLayout.newTab().setText(tabdata.get(i)));
        }
    }

    public void initViewPager() {
        viewPager=findViewById(R.id.MainViewPager);
        for(int i=0;i<9;i++)
        {
            MainFragment fragment=new MainFragment();
            fragments.add(fragment);
        }
        pagerAdapter=new MFragmentAdapter(fragments,getSupportFragmentManager(),tabdata);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        mainPresenter.GetAndSetMovieData(Integer.toString(((MainFragment)fragments.get(position)).getItems().size()), position+1);
    }

    public void initTabListening() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                position=pos;
                recyclerView = fragments.get(position).getView().findViewById(R.id.Frecyclerview);
                if(((MRecyclerAdapter)recyclerView.getAdapter()).getData().size()==0)
                mainPresenter.GetAndSetMovieData(Integer.toString(((MainFragment)fragments.get(position)).getItems().size()), position+1);
                else {
                    setRecyclerItemClickListener();
                    mainPresenter.setRecyclerlistener();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void initRefrshListening(){
        pullToRefreshLayout=((MainFragment)fragments.get(position)).getPullToRefreshLayout();
        if(pullToRefreshLayout!=null){
            pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
                @Override
                public void refresh() {
                    pullToRefreshLayout.finishRefresh();
                }

                @Override
                public void loadMore() {
                    mainPresenter.GetAndSetMovieData(Integer.toString(((MainFragment)fragments.get(position)).getItems().size()), position+1);
                }
            });
        }
    }
    @Override
    public void setRecyclerItemClickListener(){
        recyclerAdapter=(MRecyclerAdapter)recyclerView.getAdapter();
        ((MRecyclerAdapter)recyclerView.getAdapter()).setOnItemClickListener(new OnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(MainActivity.this, StepActivity.class);
                intent.putExtra("name",recyclerAdapter.getData().get(position).getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mainPresenter!=null){
            mainPresenter.destroy();
            mainPresenter=null;
        }
    }
    public void setOnsearchEditClick(){
        searchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
