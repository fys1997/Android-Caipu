package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ado.cookbookuser.R;

public class BootActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        mHandler.postDelayed(switchRunnable,5000);
    }

    private void switchToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private Runnable switchRunnable = new Runnable() {
        @Override
        public void run() {
            switchToMain();
        }
    };
}
