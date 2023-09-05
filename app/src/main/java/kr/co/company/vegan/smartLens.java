package kr.co.company.vegan;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class smartLens extends AppCompatActivity {

    ImageView imageView;
    Button startButton;
    Button stopButton;
    String TAG = "MainActivity";
    ProcessCameraProvider processCameraProvider;
    //int lensFacing = CameraSelector.LENS_FACING_FRONT;
    int lensFacing = CameraSelector.LENS_FACING_BACK;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

//        Toolbar toolbar = findViewById(R.id.toolbar5);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);

        try {
            processCameraProvider = ProcessCameraProvider.getInstance(this).get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(smartLens.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    bindImageAnalysis();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCameraProvider.unbindAll();
            }
        });
    }

    void bindImageAnalysis() {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(),
                new ImageAnalysis.Analyzer() {
                    @Override
                    public void analyze(@NonNull ImageProxy image) {
                        /*
                        @SuppressLint("UnsafeExperimentalUsageError")
                        Image mediaImage = image.getImage();
                        */
                        ///*
                        @SuppressLint("UnsafeExperimentalUsageError")
                        Image mediaImage = image.getImage();
                        Bitmap bitmap = ImageUtil.mediaImageToBitmap(mediaImage);
                        //*/
                        /*
                        @SuppressLint("UnsafeExperimentalUsageError")
                        Image mediaImage = image.getImage();
                        byte[] byteArray = ImageUtil.mediaImageToByteArray(mediaImage);
                        */
                        /*
                        @SuppressLint("UnsafeExperimentalUsageError")
                        Image mediaImage = image.getImage();
                        ByteBuffer byteBuffer = ImageUtil.mediaImageToByteBuffer(mediaImage);
                        */

                        float rotationDegrees = image.getImageInfo().getRotationDegrees();
                        Bitmap rotatedBitmap = ImageUtil.rotateBitmap(bitmap, rotationDegrees);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(rotatedBitmap);
                            }
                        });

                        image.close();
                    }
                }
        );

        processCameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis);
    }

    @Override
    protected void onPause() {
        super.onPause();
        processCameraProvider.unbindAll();
    }

}