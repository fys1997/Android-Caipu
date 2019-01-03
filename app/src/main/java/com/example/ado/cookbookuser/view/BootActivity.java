package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ado.cookbookuser.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BootActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        //获取指纹功能是否开启
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            //对应的文件名
            String fingerPointFile = getExternalCacheDir().getAbsolutePath() + File.separator + "isFingerPointAuthOpen.txt";
            File file = new File(fingerPointFile);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(fingerPointFile);
                    outputStream.write("false".getBytes());
                    outputStream.close();
                    mHandler.postDelayed(switchRunnable,5000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            StringBuilder stringBuilder = new StringBuilder("");


            try {
                FileInputStream fileInputStream = new FileInputStream(fingerPointFile);
                byte[] buffer = new byte[10];
                int len = fileInputStream.read(buffer);
                if(len>0) stringBuilder.append(new String(buffer,0,len));
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String isFingerPointAuthOpen = stringBuilder.toString();

            //根据指纹功能是否打开来进行不同的跳转
            if(isFingerPointAuthOpen.equals("false")){
                mHandler.postDelayed(switchRunnable,5000);
            }else if(isFingerPointAuthOpen.equals("true")){
                mHandler.postDelayed(switchToFingerPointAuthRunnable,5000);
            }
        }
        
    }

    //跳转到主界面
    private void switchToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    //跳转到指纹验证界面
    private void switchToFingerPointAuth(){
        Intent intent = new Intent(this,FingerPointAuthActivity.class);
        startActivity(intent);
        finish();
    }


    private Runnable switchRunnable = new Runnable() {
        @Override
        public void run() {
            switchToMain();
        }
    };

    private Runnable switchToFingerPointAuthRunnable = new Runnable() {
        @Override
        public void run() {
            switchToFingerPointAuth();
        }
    };
}
