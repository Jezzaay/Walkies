/*
package com.example.jeremy.walkies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

*/
/**
 * Created by Jeremy on 18/11/2015.
 *
 *
 * THIS IS UNUSED due to the fact I was going to make a fragment camera but I could not get in time
 * Left it in the files for when I come back to it at a later date to do this
 *
 *//*


public class CameraFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";


    public static final CameraFragment newInstance(String message)
    {
        CameraFragment f = new CameraFragment();
        Bundle bdl = new Bundle(1);

        // This is what you do  if you want to pass information through
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        return  inflater.inflate(R.layout.camera, container, false);

    }
    static final int REQUEST_IMAGE_CAPTURE = 1;



    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
   //     if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    public File createOutputFileLocation() throws IOException {

        //External Private
        // File filepath = Environment.getExternalStorageDirectory();
        File filepath = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES);

        //Create a new folder in the sd card / emulated external storage
        File dir = new File(filepath.getAbsolutePath() + "/ImageApp");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.d("ImageApp", "failed to create directory");}
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile = new File(dir +
                "/IMG_" + timeStamp + ".jpg");

        return mediaFile;

    }
    public void capture(View v) {

// Take a picture
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Get filename
        try {
            File fileUri = createOutputFileLocation();
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != 		null) {
                takePictureIntent.putExtra
                        (MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult
                        (takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }catch(Exception e){
            Toast.makeText(context.getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }

    }



}
*/
