package com.example.ado.cookbookuser.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.GetPicUtil;
import com.example.ado.cookbookuser.model.LightSensorUtil;
import com.example.ado.cookbookuser.model.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.os.Environment.getExternalStorageState;

public class BaseActivity extends AppCompatActivity {


    public static User userForNow = null;                   //当前登录的用户

    //public final static String KEY_PASSWORD = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";
    public final static String KEY_PASSWORD = "12345678";   //密码加密的key

    private LightSensorUtil lightSensorUtil;                //光照传感器的功能类

    public static Boolean isLightSensorOpen = false;        //是否开启光照

    public static final int TAKE_PHOTO = 1;                 //获取图片的模式为拍照
    public static final int CHOOSE_PHOTO = 2;               //获取图片的模式为从相册中选择



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //若光照感应打开，则开始进行光照检测
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if(isLightSensorOpen && currentNightMode == Configuration.UI_MODE_NIGHT_NO){
            onLightSensorBegin();
        }

        String storageState = getExternalStorageState();

        //判断是否存在可用的的SDCard，若userForNow为null，为其赋值
        if (userForNow == null && storageState.equals(Environment.MEDIA_MOUNTED)) {

            //记录userForNow的文件夹名
            String userForNowFile = getExternalCacheDir().getAbsolutePath()  + File.separator + "userForNowFile.txt";
            File file = new File(userForNowFile);
            if (!file.exists()){
                try{
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            StringBuilder stringBuilder = new StringBuilder("");

            //从文件夹中读取userForNow
            try {
                FileInputStream fileInputStream = new FileInputStream(userForNowFile);
                byte[] buffer = new byte[10];
                int len = fileInputStream.read(buffer);
                if(len>0) stringBuilder.append(new String(buffer,0,len));
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String userForNowName = stringBuilder.toString();

            //从数据库中找到用户
            if(userForNowName != "" && !userForNowName.equals("")) {
                List<User> users = DataSupport.findAll(User.class);
                for(User user:users){
                    if (user.getName().equals(userForNowName)) {
                        userForNow = user;
                        //Toast.makeText(this,userForNow.getName(),Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }else{
                userForNow = null;
            }

        }
    }

    //bitmap转byte数组
    public byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //提示用户光照较弱，建议切换模式
    public void onLightSensorChanged(float lightWeight){
        //Toast.makeText(this, "" + lightWeight, Toast.LENGTH_SHORT).show();

        View view = getWindow().findViewById(R.id.snack_bar);
        if (view == null) return;
        Snackbar snackbar = Snackbar.make(view,"检测到您的环境光线较弱,建议设置为夜间模式",Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    //开始进行光照检测
    public void onLightSensorBegin(){
        lightSensorUtil = new LightSensorUtil(getApplicationContext(),this);
    }

    //设置toolbar
    public void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    //弹出用户选择头像图片的方式
    public void showChoiceOfPic(final String filename,final GetPicUtil getPicUtil){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] choicePic = {"拍照","从相册中选择"};
        builder.setItems(choicePic, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(choicePic[which].equals("拍照")){
                    //本地cache文件夹
                    //File outputImage = new File(getExternalCacheDir(),"cook_book_user_head_shot.jpg");
                    File outputImage = new File(getExternalCacheDir(),filename);
                    getPicUtil.getPicByTakePhoto(outputImage);
                }else if(choicePic[which].equals("从相册中选择")){
                    getPicUtil.getPicFromAlbum();

                }
            }
        });
        builder.show();

    }

    public void onGetPicByTakePhoto(Uri imageUri){
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

    //展示图片
    public void  onDisplayImage(String imagePath){

    }

    //图片获取失败
    public void onImageNotFind(){
        Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
    }
}
