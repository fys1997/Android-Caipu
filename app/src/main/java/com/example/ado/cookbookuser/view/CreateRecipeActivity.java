package com.example.ado.cookbookuser.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.presenter.FingerPointPresent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.File;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Environment;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.view.adapter.CRRecyclerAdapter;

public class CreateRecipeActivity extends AppCompatActivity {

    private List<CreateRecipeActivity> steplist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CRRecyclerAdapter adapter = new CRRecyclerAdapter(steplist);
        recyclerView.setAdapter(adapter);
    }





//===============================
//选取照片功能
//    public static final int TAKE_PHOTO = 1;
//
//    public static final int CHOOSE_PHOTO = 2;
//
//    private ImageView picture;
//
//    private Uri imageUri;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_recipe);
//        Button takePhote = findViewById(R.id.take_photo);
//        picture = findViewById(R.id.picture);
//        Button chooseFromAlbum = findViewById(R.id.choose_from_album);
//        takePhote.setOnClickListener(new View.OnClickListener() {
//
//                                         @Override
//                                         public void onClick(View view) {                                     //创建File对象，用于存储拍照后的照片
//                                             File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
//                                             try {
//                                                 if (outputImage.exists()){
//                                                     outputImage.delete();
//                                                 }
//                                                 outputImage.createNewFile();
//                                             }
//                                             catch (IOException e){ e.printStackTrace();
//                                             }
//                                             if (Build.VERSION.SDK_INT >= 24) {
//                                                 imageUri = FileProvider.getUriForFile(CreateRecipeActivity.this,"com.example.cameraalbumtest.fileprovider",outputImage);
//                                             }
//                                             else {
//                                                 imageUri = Uri.fromFile(outputImage);
//                                             }                      //启动相机程序
//                                             Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                                             intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                                             startActivityForResult(intent,TAKE_PHOTO);
//                                         }
//                                     }
//        );
//        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(CreateRecipeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){ ActivityCompat.requestPermissions(CreateRecipeActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }
//                else {
//                    openAlbum();
//                }
//            }
//        });
//    }
//    private void openAlbum(){ Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
//    } @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { switch (requestCode){ case 1: if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ openAlbum();
//    }
//    else {
//        Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
//    }
//        break;
//        default: break;
//    }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) { switch (requestCode){ case TAKE_PHOTO: if (resultCode == RESULT_OK){
//        try { //将拍摄的照片显示出来
//            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//            picture.setImageBitmap(bitmap);
//        }
//        catch (FileNotFoundException e){ e.printStackTrace();
//        }
//    } break;
//        case CHOOSE_PHOTO: if (resultCode == RESULT_OK){ //判断手机系统版本号
//            if (Build.VERSION.SDK_INT >= 19){ //4.4及以上系统使用这个方法处理图片
//                handleImageOnKitKat(data);
//            }else { //4.4以下系统使用这个放出处理图片
//                handleImageBeforeKitKat(data);
//            }
//        } break;
//        default: break;
//    }
//    }
//    @TargetApi(19) private void handleImageOnKitKat(Intent data){
//        String imagePath = null;
//        Uri uri = data.getData();
//        if (DocumentsContract.isDocumentUri(this,uri)){                    //如果是document类型的Uri,则通过document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
//                String id = docId.split(":")[1];//解析出数字格式的id
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
//            }
//            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
//                imagePath = getImagePath(contentUri,null);
//            }
//        }
//        else if ("content".equalsIgnoreCase(uri.getScheme())){ //如果是content类型的Uri，则使用普通方式处理
//            imagePath = getImagePath(uri,null);
//        }
//        else if ("file".equalsIgnoreCase(uri.getScheme())){ //如果是file类型的Uri，直接获取图片路径即可
//            imagePath = uri.getPath();
//        }
//        diaplayImage(imagePath);//根据图片路径显示图片
//    }
//    private void handleImageBeforeKitKat(Intent data){
//        Uri uri = data.getData();
//        String imagePath = getImagePath(uri,null);
//        diaplayImage(imagePath);
//    }
//    private String getImagePath(Uri uri,String selection){
//        String path = null;
//        //通过Uri和selection来获取真实的图片路径
//        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
//        if (cursor != null){
//            if (cursor.moveToFirst()){
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//    private void diaplayImage(String imagePath){
//        if (imagePath != null){
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            picture.setImageBitmap(bitmap);
//        }
//        else {
//            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
//        }
//    }

}
