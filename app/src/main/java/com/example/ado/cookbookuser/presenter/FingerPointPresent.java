package com.example.ado.cookbookuser.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

import com.example.ado.cookbookuser.view.FingerPointActivity;

@TargetApi(Build.VERSION_CODES.M)
public class FingerPointPresent {
    private FingerPointActivity fingerPointActivity;
    private FingerprintManager fingerprintManager;
    private boolean isSupport = false;

    public FingerPointPresent(FingerPointActivity fingerPointActivity){
        this.fingerPointActivity = fingerPointActivity;
        fingerprintManager = getFingerprintManager(fingerPointActivity);
        isSupport = (fingerprintManager != null && fingerprintManager.isHardwareDetected());
    }


    public static FingerprintManager getFingerprintManager(Context context) {
        FingerprintManager fingerprintManager = null;
        fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        return fingerprintManager;
    }

    public boolean isHasEnrolledFingerprints() {
        return fingerprintManager.hasEnrolledFingerprints();
    }

    public boolean isSupport() {
        return isSupport;
    }


}
