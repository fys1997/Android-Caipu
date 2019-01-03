package com.example.ado.cookbookuser.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.presenter.FingerPointAuthPresent;


public class FingerPointAuthActivity extends BaseActivity {

    private FingerPointAuthPresent fingerPointAuthPresent;

    private FingerprintManager fingerprintManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_point_auth);

        fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);

        //若无指纹
        if(!fingerprintManager.hasEnrolledFingerprints()){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        fingerPointAuthPresent = new FingerPointAuthPresent(this);

        //开始验证指纹
        fingerPointAuthPresent.startFingerPointAuth();
    }

    //验证成功
    public void onAuthenticationSucceeded(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
