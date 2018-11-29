package com.example.ado.cookbookuser.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserActivity extends BaseActivity implements View.OnClickListener{

    private EditUserPresenter editUserPresenter;

    private Toolbar toolbarEditUser;                    //EditUserActivity的toolbar
    private CircleImageView circleImageView;            //用户头像
    private TextView textView;                          //用户名
    private EditText editUserBirthday;                  //用户生日编辑
    private EditText editUserGender;                    //用户性别编辑

    //用户选择头像图片的方式
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Uri imageHeadShotUri;                                //用户头像地址

    private User userChanged = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        editUserPresenter = new EditUserPresenter(this);

        textView = findViewById(R.id.edit_layout_user_name);
        toolbarEditUser = findViewById(R.id.toolbar_edit_user);
        circleImageView = findViewById(R.id.edit_layout_headShot);
        editUserBirthday = findViewById(R.id.edit_user_birthday);
        editUserGender = findViewById(R.id.edit_user_gender);

        initLayout();

        circleImageView.setOnClickListener(this);
        editUserBirthday.setOnClickListener(this);
        editUserGender.setOnClickListener(this);
    }

    //初始化布局
    private void initLayout(){
        //设置toolbar
        setSupportActionBar(toolbarEditUser);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //初始化界面
        byte[] userForNowHeadShot = BaseActivity.userForNow.getHeadShot();
        Bitmap bitmap = BitmapFactory.decodeByteArray(userForNowHeadShot,0,userForNowHeadShot.length);
        Glide.with(EditUserActivity.this).load(bitmap).into(circleImageView);
        textView.setText(BaseActivity.userForNow.getName());
        editUserBirthday.setText(BaseActivity.userForNow.getBirthday());
        editUserGender.setText(BaseActivity.userForNow.getGender());

        //隐藏软键盘
        hideInputManager(EditUserActivity.this,editUserBirthday);
    }

    //隐藏软键盘
    public static void hideInputManager(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view !=null && imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.edit_layout_headShot:{
                showChoiceOfPic();
                break;
            }
            case R.id.edit_user_birthday:{
                showBirthday();
                break;
            }
            case R.id.edit_user_gender:{
                showGenderChooseDialog();
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
                    //本地cache文件夹
                    File outputImage = new File(getExternalCacheDir(),"cook_book_user_head_shot.jpg");
                    editUserPresenter.getPicByTakePhoto(outputImage);
                }else if(choicePic[which].equals("从相册中选择")){
                    editUserPresenter.getPicFromAlbum();

                }
            }
        });
        builder.show();

    }

    //弹出生日选择
    private void showBirthday(){
        Calendar cal;
        int year,month,day;
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                editUserBirthday.setText(year+"-"+(++month)+"-"+day);
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(EditUserActivity.this, 0,listener,year,month,day);
        ;
        DatePicker datePicker = dialog.getDatePicker();
        Date today = Calendar.getInstance().getTime();//当天
        try {
            datePicker.setMaxDate(today.getTime());// 最大日期
        } catch (Exception e) {

        }
        dialog.show();
    }

    //弹出性别选择
    private void showGenderChooseDialog() {
        final String[] genderArray = {"男","女"};
        int checkedItem = 0;
        String nowGender = editUserGender.getText().toString();
        if(nowGender.equals("女")) checkedItem = 1;
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setSingleChoiceItems(genderArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editUserGender.setText(genderArray[which]);
                dialog.dismiss();
            }
        });
        builder3.show();
    }

    public void onGetPicByTakePhoto(Uri imageUri){
        this.imageHeadShotUri = imageUri;
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
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageHeadShotUri));
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
                String birthday = editUserBirthday.getText().toString();
                String gender = editUserGender.getText().toString();
                userChanged.setBirthday(birthday);
                userChanged.setGender(gender);
                userChanged.updateAll("name =?", BaseActivity.userForNow.getName());

                //更新当前用户
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
