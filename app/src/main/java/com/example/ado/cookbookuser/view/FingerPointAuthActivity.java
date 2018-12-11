package com.example.ado.cookbookuser.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.presenter.FingerPointAuthPresent;


public class FingerPointAuthActivity extends BaseActivity {

    private FingerPointAuthPresent fingerPointAuthPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_point_auth);

        fingerPointAuthPresent = new FingerPointAuthPresent(this);

        fingerPointAuthPresent.startFingerPointAuth();
    }

    public void onAuthenticationSucceeded(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
