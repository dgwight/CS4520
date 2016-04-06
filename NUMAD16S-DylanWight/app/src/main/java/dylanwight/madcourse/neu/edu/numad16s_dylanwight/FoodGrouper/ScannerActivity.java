package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

/**
 * Created by DylanWight on 4/5/16.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.wordpress.priyankvex.easyocrscannerdemo.Config;
import com.wordpress.priyankvex.easyocrscannerdemo.EasyOcrScanner;
import com.wordpress.priyankvex.easyocrscannerdemo.EasyOcrScannerListener;
import com.wordpress.priyankvex.easyocrscannerdemo.FileUtils;

import java.io.File;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;


public class ScannerActivity extends AppCompatActivity implements EasyOcrScannerListener {

    EasyOcrScanner mEasyOcrScanner;
    TextView textView;
    ProgressDialog mProgressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        // initialize EasyOcrScanner instance.
        mEasyOcrScanner = new EasyOcrScanner(ScannerActivity.this, "EasyOcrScanner",
                com.wordpress.priyankvex.easyocrscannerdemo.Config.REQUEST_CODE_CAPTURE_IMAGE, "eng");

        // Set ocrScannerListener
        mEasyOcrScanner.setOcrScannerListener(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEasyOcrScanner.takePicture();
            }
        });


        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.init(FileUtils.getDirectory("EasyOcrScanner") + "/", "eng");

        Bitmap xando = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.img_3280);
        baseApi.setImage(xando);
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        imageView.setImageBitmap(xando);
        textView.setText(recognizedText);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Call onImageTaken() in onActivityResult.
        if (resultCode == RESULT_OK && requestCode == com.wordpress.priyankvex.easyocrscannerdemo.Config.REQUEST_CODE_CAPTURE_IMAGE){
            mEasyOcrScanner.onImageTaken();
        }
    }

    /**
     * Callback when after taking picture, scanning process starts.
     * Good place to show a progress dialog.
     * @param filePath file path of the image file being processed.
     */
    @Override
    public void onOcrScanStarted(String filePath) {
        mProgressDialog = new ProgressDialog(ScannerActivity.this);
        mProgressDialog.setMessage("Scanning...");
        mProgressDialog.show();

        File imgFile = new  File(filePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
    }

    /**
     * Callback when scanning is finished.
     * Good place to hide teh progress dialog.
     * @param bitmap Bitmap of image that was scanned.
     * @param recognizedText Scanned text.
     */
    @Override
    public void onOcrScanFinished(Bitmap bitmap, String recognizedText) {
        textView.setText(recognizedText);
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
