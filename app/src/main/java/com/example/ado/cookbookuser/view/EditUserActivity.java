package com.example.ado.cookbookuser.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.User;
import com.example.ado.cookbookuser.presenter.EditUserPresenter;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserActivity extends BaseActivity implements View.OnClickListener{

    private EditUserPresenter editUserPresenter;

    private Toolbar toolbarEditUser;                    //EditUserActivity的toolbar
    private CircleImageView circleImageView;            //用户头像
    private TextView textView;                          //用户名

    //用户选择头像图片的方式
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Uri imageUri;                                //用户头像地址

    private User userChanged = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        editUserPresenter = new EditUserPresenter(this);

        textView = findViewById(R.id.edit_layout_user_name);
        toolbarEditUser = findViewById(R.id.toolbar_edit_user);
        circleImageView = findViewById(R.id.edit_layout_headShot);

        //设置toolbar
        setSupportActionBar(toolbarEditUser);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        byte[] userForNowHeadShot = BaseActivity.userForNow.getHeadShot();
        Bitmap bitmap = BitmapFactory.decodeByteArray(userForNowHeadShot,0,userForNowHeadShot.length);
        Glide.with(EditUserActivity.this).load(bitmap).into(circleImageView);
        textView.setText(BaseActivity.userForNow.getName());

        circleImageView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.edit_layout_headShot:{
                showChoiceOfPic();
                break;
            }
        }
    }

    //弹出用户选择头像图片的方式
    private void showChoiceOfPic(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.this);
        final String[] choicePic = {"拍照","从相册中选择"};
        builder.setItems(choicePic, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(choicePic[which].equals("拍照")){
                    File outputImage = new File(getExternalCacheDir(),"cook_book_user_head_shot.jpg");
                    editUserPresenter.getPicByTakePhoto(outputImage);
                }else if(choicePic[which].equals("从相册中选择")){
                    editUserPresenter.getPicFromAlbum();

                }
            }
        });
        builder.show();

    }

    public void onGetPicByTakePhoto(Uri imageUri){
        this.imageUri = imageUri;
        //打开相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    public void onGetPicFromAlbum(){
        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    //展示图片并保存
    public void onDisplayImage(String imagePath){
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        userChanged.setHeadShot(bitmapToByteArray(bitmap));

        Glide.with(this).load(imagePath).into(circleImageView);
    }

    //图片获取失败
    public void onImageNotFind(){
        Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    onGetPicFromAlbum();
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case TAKE_PHOTO:{
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        userChanged.setHeadShot(bitmapToByteArray(bitmap));

                        Glide.with(this).load(bitmap).into(circleImageView);
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
                            editUserPresenter.handleImageOnKitKat(data);
                        }else{
                            editUserPresenter.handleImageBeforeKitKat(data);
                        }
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.save:{
                userChanged.updateAll("name =?", BaseActivity.userForNow.getName());
                List<User> users = DataSupport.findAll(User.class);
                for(User user:users){
                    if (user.getName().equals(BaseActivity.userForNow.getName())) {
                        BaseActivity.userForNow = user;
                        break;
                    }
                }
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_edit_user,menu);
        return true;
    }


}
