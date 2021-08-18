package com.snakewizard.retailerwizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.snakewizard.retailerwizard.checkin.CommodityViewActivity;
import com.snakewizard.retailerwizard.checkout.CheckoutCounter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button checkout=findViewById(R.id.button1);
        checkout.setOnClickListener(this::checkoutCounter);
        Button dataUpdate=findViewById(R.id.button2);
        dataUpdate.setOnClickListener(this::dataUpdate);
    }

    public  void dataUpdate(View view){
        Intent intent=new Intent(this, CommodityViewActivity.class);
        startActivity(intent);
    }

    public void checkoutCounter(View view){
        if (hasCameraPermission()) {
            Intent intent=new Intent(this, CheckoutCounter.class);
            startActivity(intent);
        } else {
            requestPermission();
        }
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA},
                0
        );
    }




}