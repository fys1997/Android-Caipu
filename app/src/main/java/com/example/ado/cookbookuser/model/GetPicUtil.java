package com.example.ado.cookbookuser.model;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.example.ado.cookbookuser.view.BaseActivity;
import com.example.ado.cookbookuser.view.EditUserActivity;
import com.example.ado.cookbookuser.view.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class GetPicUtil {

    private Uri imageUri;
    private BaseActivity activity;

    public GetPicUtil(BaseActivity activity){
        this.activity = activity;
    }


    //通过拍照获取头像
    public void getPicByTakePhoto(File outputImage){
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUri =  FileProvider.getUriForFile(activity,"com.example.ado.cookbookuser.fileprovider",outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }
        activity.onGetPicByTakePhoto(imageUri);
    }

    //从相册中获取头像
    public void getPicFromAlbum(){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            activity.onGetPicFromAlbum();
        }
    }

    //Api大于19时，获取图片地址并展示
    @TargetApi(19)
    public void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(activity,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    //Api小于19时，获取图片地址并展示
    public void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    //获取图片地址
    public String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = activity.getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToNext()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //展示图片
    public void displayImage(String imagePath){
        if(imagePath != null){
            activity.onDisplayImage(imagePath);
        }else{
            activity.onImageNotFind();
        }
    }
}
