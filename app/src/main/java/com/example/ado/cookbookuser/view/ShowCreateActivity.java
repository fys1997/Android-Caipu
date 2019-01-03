package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.CreateCookBook;
import com.example.ado.cookbookuser.view.adapter.CookStepShowAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ShowCreateActivity extends BaseActivity {

    private Toolbar toolbarShowCreate;                      //toolbar
    private ImageView cookbookCover;                        //食谱封面
    private TextView cookbookName;                          //食谱名
    private TextView cookbookMaterial;                      //食谱用料
    private RecyclerView recyclerView;                      //展示步骤的recyclerView
    private int cookbookId;                                 //要展示的食谱对应的id
    private CreateCookBook createCookBook;                  //要展示的食谱
    private List<String> steps;                             //制作的步骤

    private CookStepShowAdapter cookStepShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_create);

        //获取要展示的食谱的id，并在数据库中找到
        Intent intent = getIntent();
        steps = new ArrayList<>();
        cookbookId = intent.getIntExtra("id",0);
        createCookBook = DataSupport.find(CreateCookBook.class,cookbookId);

        //获取控件
        toolbarShowCreate = findViewById(R.id.toolbar_show_create);
        cookbookCover = findViewById(R.id.cookbook_cover);
        cookbookName = findViewById(R.id.cookbook_name);
        cookbookMaterial = findViewById(R.id.cookbook_material);
        recyclerView = findViewById(R.id.cookbook_steps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

        initData();
        cookStepShowAdapter = new CookStepShowAdapter(steps);
        recyclerView.setAdapter(cookStepShowAdapter);

        //设置toolbar
        setToolbar(toolbarShowCreate);
    }

    //将获取到的食谱的封面图片，名字，用料展示出来，步骤存储到steps数组中
    private void initData(){
        byte[] coverBytes = createCookBook.getCover();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(coverBytes,0,coverBytes.length);
        Glide.with(this).load(coverBytes).into(cookbookCover);
        cookbookName.setText(createCookBook.getName());
        cookbookMaterial.setText(createCookBook.getMaterial());
        if(createCookBook.getStep1()!=null) steps.add(createCookBook.getStep1());
        if(createCookBook.getStep2()!=null) steps.add(createCookBook.getStep2());
        if(createCookBook.getStep3()!=null) steps.add(createCookBook.getStep3());
        if(createCookBook.getStep4()!=null) steps.add(createCookBook.getStep4());
        if(createCookBook.getStep5()!=null) steps.add(createCookBook.getStep5());
        if(createCookBook.getStep6()!=null) steps.add(createCookBook.getStep6());
        if(createCookBook.getStep7()!=null) steps.add(createCookBook.getStep7());
        if(createCookBook.getStep8()!=null) steps.add(createCookBook.getStep8());
        if(createCookBook.getStep9()!=null) steps.add(createCookBook.getStep9());
        if(createCookBook.getStep10()!=null) steps.add(createCookBook.getStep10());
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
