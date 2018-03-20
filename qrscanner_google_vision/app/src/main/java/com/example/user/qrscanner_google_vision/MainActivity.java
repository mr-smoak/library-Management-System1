package com.example.user.qrscanner_google_vision;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.TextView;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView txtView;
    SurfaceView cameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.txtContent);
         cameraView = (SurfaceView)findViewById(R.id.surfaceView);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this) .setBarcodeFormats(Barcode.QR_CODE) .build();

        final CameraSource cameraSource = new CameraSource .Builder(this, barcodeDetector) .setRequestedPreviewSize(640, 480) .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() { @Override public void surfaceCreated(SurfaceHolder holder) {
             try { cameraSource.start(cameraView.getHolder()); } catch (IOException ie) { Log.e("CAMERA SOURCE", ie.getMessage()); }
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            cameraSource.stop();
        }
        });
            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray barcodes = detections.getDetectedItems();

                    if (barcodes.size() != 0) { txtView.post(new Runnable() {  public void run() { Barcode a=(Barcode) barcodes.valueAt(0);txtView.setText( a.rawValue ); } }); }
                }
            });



     }
}
