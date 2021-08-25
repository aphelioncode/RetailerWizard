package com.snakewizard.retailerwizard.apputil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AppUtil {
    static public boolean hasCameraPermission(Context context) {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    static public void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.CAMERA},
                0
        );
    }

    // string.strip() is a function introduced since java 11
    // in java 8, we manually rewrite this function
    static public String strip(String str){
        if(str==null){return null;}
        char[] cs=str.toCharArray();
        int p1=0,p2=cs.length;
        while(p1<p2&&(cs[p1]==' '||cs[p2-1]==' ')) {
            if(cs[p1]==' '){
                p1++;
                continue;}
            if(cs[p2-1]==' '){
                p2--;}
        }
        return new String(cs,p1,p2-p1);
    }
}
