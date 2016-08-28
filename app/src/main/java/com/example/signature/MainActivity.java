package com.example.signature;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SignaturePad mSignaturePad;
    Button btn_save_pad_img, btn_clear_pad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_clear_pad = (Button) findViewById(R.id.btn_clear_pad);
        btn_save_pad_img = (Button) findViewById(R.id.btn_save_pad_img);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
            }

            @Override
            public void onClear() {

            }
        });


        btn_clear_pad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
            }
        });

        btn_save_pad_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = mSignaturePad.getSignatureBitmap();

                String folderName = "Signatures";

                File path_to_downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator + folderName);
                if (!path_to_downloads.isDirectory() && !path_to_downloads.exists()) {
                    path_to_downloads.mkdir();
                }

                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "_";
                    File file = new File(path_to_downloads.getPath()+File.separator+imageFileName+".jpg");

                    FileOutputStream outStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                    Toast.makeText(MainActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
                    mSignaturePad.clear();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


    }
}
