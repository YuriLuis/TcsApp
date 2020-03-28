package com.senac.tcs.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public void galleryAddPic(String TAG, Activity ativ, String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        Log.i(TAG, "Adiciona Galeria: " + currentPhotoPath);
        ativ.sendBroadcast(mediaScanIntent);
        //Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(galleryIntent, GALLERY);
    }

    public void saveImage(Mat input, String TAG, Activity ativ, File storageDir) {
        boolean touched = true;
        if (!input.empty()) {
            Mat mRGBT = input.t();
            Core.flip(mRGBT, mRGBT, 1);
            Imgproc.resize(mRGBT, mRGBT, input.size());

            if (touched) {
                touched = false;
                String currentPhotoPath = "";
                final Bitmap bitmap = Bitmap.createBitmap(mRGBT.cols(), mRGBT.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(mRGBT, bitmap, true);
                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    FileOutputStream fos = null;
                    try {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "BMP_" + timeStamp + "_";
                        File image = File.createTempFile(imageFileName,".BMP", storageDir);
                        currentPhotoPath = image.getAbsolutePath();
                        image.delete();
                        //Log.i(TAG, currentPhotoPath);
                        fos = new FileOutputStream(new File(currentPhotoPath));
                        stream.writeTo(fos);
                    } catch(IOException ioe) {
                        ioe.printStackTrace();
                    } finally {
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //if (!currentPhotoPath.isEmpty()) {
                //    galleryAddPic(TAG, ativ, currentPhotoPath);
                //}
            }
            //  Intent activityIntent = new Intent(this, MainActivity.class);
            //  startActivity(activityIntent);
        }
    }
}
