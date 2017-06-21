package com.example.jeremy.walkies;


import android.app.Activity;


import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CameraActivity extends Activity {

    ImageButton takePhotoImageButton;
    ImageButton sendPhotoImageButton;
    TextureView mTextureView;
    ImageView mImageView;
    String mImagePath;
    Uri Uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.camera);
        takePhotoImageButton = (ImageButton) findViewById(R.id.picture);
        mTextureView = (TextureView) findViewById(R.id.View);
        mImageView = (ImageView) findViewById(R.id.imageView);
        takePhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });

        sendPhotoImageButton = (ImageButton) findViewById(R.id.sendButton);

        sendPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, ShareActivity.class);
                intent.putExtra("photo", mImagePath);
                intent.putExtra("send_photo",mImagePath);
                startActivity(intent);
            }
        });



    }

    static final int REQUEST_IMAGE_CAPTURE = 1;




    //Taking the photo
    public void takePhoto(View view)
    {
        //Calling the camera capture intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try
        {
            photoFile = createImageFile();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(takePictureIntent,1);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mImagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public File createImageFile() throws IOException{

        //file names for the images
        //unique file names
        //YEAR MONTH DATE HOUR MINUTES AND SECONDS
        // No one photo will ever have the same name with this format.
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File dir = new File(filepath.getAbsolutePath() + "/Walkies");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.d("Walkies", "failed to create directory");
            }
        }

        File image = File.createTempFile(imageFileName,".jpg", dir);

        mImagePath = image.getAbsolutePath();
        galleryAddPic();
        return  image;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap photoCap = BitmapFactory.decodeFile(mImagePath);
            mImageView.setImageBitmap(photoCap);
            sendPhotoImageButton.setVisibility(View.VISIBLE);
        }
    }


}