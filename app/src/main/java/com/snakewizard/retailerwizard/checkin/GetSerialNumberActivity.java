package com.snakewizard.retailerwizard.checkin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Size;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.snakewizard.retailerwizard.R;
import com.snakewizard.retailerwizard.apputil.AppUtil;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetSerialNumberActivity extends AppCompatActivity {


    ConstraintLayout container;
    TextView text_view;
    PreviewView view_finder;

    Executor executor;
    private long mLastAnalysisResultTime;

    private Camera camera;
    Intent replyIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_serial_number);

        container = findViewById(R.id.camera_container);
        text_view = findViewById(R.id.text_prediction);
        view_finder = findViewById(R.id.view_finder);

        executor = Executors.newSingleThreadExecutor();

        if (AppUtil.hasCameraPermission(this)) {
           startCamera();
        }else{
            AppUtil.requestCameraPermission(this);
        }



    }


    private void startCamera() {

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture
                = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        executor = Executors.newSingleThreadExecutor();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(224, 224))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {

                if(SystemClock.elapsedRealtime() - mLastAnalysisResultTime < 200) {
                    image.close();
                    return;
                }
                @SuppressLint("UnsafeOptInUsageError") Image mediaImage = image.getImage();
                if (mediaImage != null) {
                    InputImage inputimage =
                            InputImage.fromMediaImage(mediaImage, image.getImageInfo().getRotationDegrees());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            BarcodeScanner scanner = BarcodeScanning.getClient();

                            // [START run_detector]
                            final String[] rawValue = {"Wait..."};
                            Task<List<Barcode>> result = scanner.process(inputimage)
                                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                                        @Override
                                        public void onSuccess(List<Barcode> barcodes) {
                                            for (Barcode barcode: barcodes) {
                                                rawValue[0] = barcode.getRawValue();
                                                text_view.setText(String.format(Locale.US, "%s", rawValue[0]));
                                                replyIntent = new Intent();

                                                replyIntent.putExtra("serialNumber", rawValue[0]);
                                                setResult(RESULT_OK, replyIntent);
                                                break;
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });

                        }
                    });
                }

                mLastAnalysisResultTime = SystemClock.elapsedRealtime();
                image.close();
            }
        });

        cameraProvider.unbindAll();
        camera = cameraProvider.bindToLifecycle((LifecycleOwner)this,
                cameraSelector, imageAnalysis, preview);

        preview.setSurfaceProvider(view_finder.getSurfaceProvider());
        if(replyIntent!=null){this.finish();}

    }
}