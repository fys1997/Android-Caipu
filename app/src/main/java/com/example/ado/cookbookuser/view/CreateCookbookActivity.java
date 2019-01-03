package com.example.ado.cookbookuser.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.CreateCookBook;
import com.example.ado.cookbookuser.model.CreateStoreUtil;
import com.example.ado.cookbookuser.model.GetPicUtil;
import com.example.ado.cookbookuser.view.adapter.CookStepAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateCookbookActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbarCreateCookbook;
    private RecyclerView recyclerView;
    private CookStepAdapter cookStepAdapter;
    private TextView stepMore;
    private TextView stepLess;
    private Button submitCookbook;
    private EditText inputCookbookMaterial;
    private TextView inputCookbookName;
    private ImageView cookbookCover;
    private TextView placeHolder;
    private CreateCookBook cookBook = new CreateCookBook();

    private Bitmap coverBitmap;
    private int stepSize = 0;
    private GetPicUtil getPicUtil;
    private Uri imageCoverUri;

    private List<String> stepIds;
    private List<String> stepDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cookbook);
        initData();

        getPicUtil = new GetPicUtil(this);
        cookStepAdapter = new CookStepAdapter(stepIds);
        toolbarCreateCookbook = findViewById(R.id.toolbar_create_cookbook);

        cookbookCover = findViewById(R.id.cookbook_cover);
        placeHolder = findViewById(R.id.place_holder);
        inputCookbookName = findViewById(R.id.select_cookbook_name);
        inputCookbookMaterial = findViewById(R.id.input_cookbook_material);
        recyclerView = findViewById(R.id.cookbook_steps);
        stepMore = findViewById(R.id.step_more);
        stepLess = findViewById(R.id.step_less);
        submitCookbook = findViewById(R.id.submit_cookbook);

        cookbookCover.setOnClickListener(this);
        placeHolder.setOnClickListener(this);
        stepMore.setOnClickListener(this);
        stepLess.setOnClickListener(this);
        submitCookbook.setOnClickListener(this);

        initLayout();
    }

    private void initLayout(){
        setToolbar(toolbarCreateCookbook);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cookStepAdapter);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }

    private void initData(){
        stepIds = new ArrayList<>();
        stepIds.add("步骤1");
        stepIds.add("步骤2");
        stepIds.add("步骤3");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cookbook_cover:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定要移除图片吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cookbookCover.setVisibility(View.GONE);
                        placeHolder.setVisibility(View.VISIBLE);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            }
            case R.id.place_holder:{
                showChoiceOfPic("cook_book_cover.jpg",getPicUtil);
                break;
            }
            case R.id.step_more:{
                cookStepAdapter.addNewItem();
                stepSize++;
                stepLess.setVisibility(View.VISIBLE);
                if(stepSize == 7){
                    stepMore.setVisibility(View.GONE);
                }
                break;
            }
            case R.id.step_less:{
                cookStepAdapter.deleteItem();
                stepSize--;
                if(stepSize == 0){
                    stepLess.setVisibility(View.GONE);
                }
                stepMore.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.submit_cookbook:{
                if(!hasInputError()){
                    cookBook.setCover(bitmapToByteArray(coverBitmap));
                    cookBook.setName(inputCookbookName.getText().toString());
                    cookBook.setMaterial(inputCookbookMaterial.getText().toString());
                    if(stepDetails.size()>=1)cookBook.setStep1(stepDetails.get(0));
                    if(stepDetails.size()>=2)cookBook.setStep2(stepDetails.get(1));
                    if(stepDetails.size()>=3)cookBook.setStep3(stepDetails.get(2));
                    if(stepDetails.size()>=4)cookBook.setStep4(stepDetails.get(3));
                    if(stepDetails.size()>=5)cookBook.setStep5(stepDetails.get(4));
                    if(stepDetails.size()>=6)cookBook.setStep6(stepDetails.get(5));
                    if(stepDetails.size()>=7)cookBook.setStep7(stepDetails.get(6));
                    if(stepDetails.size()>=8)cookBook.setStep8(stepDetails.get(7));
                    if(stepDetails.size()>=9)cookBook.setStep9(stepDetails.get(8));
                    if(stepDetails.size()>=10)cookBook.setStep10(stepDetails.get(9));
                    CreateStoreUtil.saveCookbookToCreate(cookBook);
                    Toast.makeText(this, "保存食谱成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
            }
        }
    }

    private boolean hasInputError(){
        if(cookbookCover.getVisibility() == View.GONE){
            Toast.makeText(this, "请上传食谱封面", Toast.LENGTH_SHORT).show();
            return true;
        }else if(inputCookbookName.getText().toString().equals("")){
            Toast.makeText(this, "请输入食谱名称", Toast.LENGTH_SHORT).show();
            return true;
        }else if(inputCookbookMaterial.getText().toString().equals("")){
            Toast.makeText(this, "请输入食谱用料", Toast.LENGTH_SHORT).show();
            return true;
        }else if(hasEmptyStep()){
            Toast.makeText(this, "存在空步骤，请输入完整", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean hasEmptyStep(){
        stepDetails = new ArrayList<>();
        for(int i = 0;i<recyclerView.getChildCount();i++){
            LinearLayout linearLayout = (LinearLayout) recyclerView.getChildAt(i);
            EditText editText = linearLayout.findViewById(R.id.step_detail);
            if(editText.getText().toString().equals("")){
                return true;
            }else{
                stepDetails.add(editText.getText().toString());
            }
        }
        return false;
    }

    public void onGetPicByTakePhoto(Uri imageUri){
        super.onGetPicByTakePhoto(imageUri);
        this.imageCoverUri = imageUri;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case TAKE_PHOTO:{
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCoverUri));
                        Glide.with(this).load(bitmap).into(cookbookCover);

                        coverBitmap = bitmap;
                        cookbookCover.setVisibility(View.VISIBLE);
                        placeHolder.setVisibility(View.GONE);
//                        circleImageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case CHOOSE_PHOTO:{
                if(resultCode == RESULT_OK){
                    if(resultCode == RESULT_OK){
                        if(Build.VERSION.SDK_INT >= 19){
                            getPicUtil.handleImageOnKitKat(data);
                        }else{
                            getPicUtil.handleImageBeforeKitKat(data);
                        }
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    //展示图片并保存
    public void onDisplayImage(String imagePath){
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Glide.with(this).load(imagePath).into(cookbookCover);
        coverBitmap = bitmap;
        cookbookCover.setVisibility(View.VISIBLE);
        placeHolder.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
    }
}
