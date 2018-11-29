package com.example.ado.cookbookuser.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

    //当前登录的用户
    public static User userForNow = null;

    //创建数据库
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    //从缓存中获得当前的登录用户
    @Override
    protected void onStart() {
        super.onStart();

        String storageState = Environment.getExternalStorageState();

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
                        Toast.makeText(this,userForNow.getName(),Toast.LENGTH_SHORT).show();
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



}
