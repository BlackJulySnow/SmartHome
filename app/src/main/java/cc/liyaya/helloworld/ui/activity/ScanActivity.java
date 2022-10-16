package cc.liyaya.helloworld.ui.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cc.liyaya.helloworld.R;
import cc.liyaya.helloworld.databinding.ActivityScanBinding;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "ScanActivity";
    private ActivityScanBinding binding;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private BarcodeScanner scanner;

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        cameraProviderFuture = null;
        scanner = null;
        Log.i(TAG,"onDestroy");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan_back:
                finish();
                break;
        }
    }

    private class QRCodeAnalyzer implements ImageAnalysis.Analyzer {

        @Override
        public void analyze(ImageProxy imageProxy) {
            @SuppressLint("UnsafeOptInUsageError") Image mediaImage = imageProxy.getImage();
            if (mediaImage != null) {
                InputImage image =
                        InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                Task<List<Barcode>> result = scanner.process(image)
                        .addOnSuccessListener(barcodes -> {
                            // Task completed successfully
                            // ...
                            for(Barcode barcode : barcodes) {
                                Log.i(TAG, barcode.getRawValue());
                            }
                            imageProxy.close();
                        })
                        .addOnFailureListener(e -> {

                            Log.e(TAG, "ERROR");
                            imageProxy.close();
                        });
            }

        }
    }

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    private void allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{permission}, 1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScanBinding.inflate(getLayoutInflater());
        binding.scanBack.setOnClickListener(this);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        allPermissionsGranted();
        scanner = BarcodeScanning.getClient();
        cameraProviderFuture = ProcessCameraProvider.getInstance(getApplicationContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, getMainExecutor());
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .build();

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
        imageAnalysis.setAnalyzer(getMainExecutor(), new QRCodeAnalyzer() );
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.scanPreview.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, preview,imageAnalysis);
    }

}