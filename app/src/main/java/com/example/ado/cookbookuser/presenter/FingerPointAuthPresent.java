package com.example.ado.cookbookuser.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import com.example.ado.cookbookuser.view.FingerPointAuthActivity;

@TargetApi(Build.VERSION_CODES.M)
public class FingerPointAuthPresent {

    private FingerPointAuthActivity fingerPointAuthActivity;


    private FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;
    private FingerprintManager.AuthenticationCallback mAuthCallback;

    public FingerPointAuthPresent(FingerPointAuthActivity fingerPointAuthActivity){
        this.fingerPointAuthActivity = fingerPointAuthActivity;
    }

    public void startFingerPointAuth(){
        prepareData();
        mFingerprintManager = getFingerprintManager(fingerPointAuthActivity);
        mFingerprintManager.authenticate(null, mCancellationSignal, 0, mAuthCallback, null);
    }

    private void prepareData() {
        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        if (mAuthCallback == null) {
            mAuthCallback = new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    Toast.makeText(fingerPointAuthActivity, "请尝试其他方法进入", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

                }

                @Override
                public void onAuthenticationFailed() {
                    Toast.makeText(fingerPointAuthActivity, "验证失败，指纹错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    fingerPointAuthActivity.onAuthenticationSucceeded();
                    Toast.makeText(fingerPointAuthActivity, "验证成功", Toast.LENGTH_SHORT).show();
                }
            };
        }
    }

    public static FingerprintManager getFingerprintManager(Context context) {
        FingerprintManager fingerprintManager = null;
        fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        return fingerprintManager;
    }
}
