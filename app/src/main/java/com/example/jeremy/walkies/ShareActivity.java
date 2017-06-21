
package com.example.jeremy.walkies;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import io.fabric.sdk.android.Fabric;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;


public class ShareActivity  extends Activity {

    private TwitterLoginButton loginButton;

    ImageView mImageView;



    TweetComposer.Builder builder = new TweetComposer.Builder(this);

    ImageButton sendtoTwitter;
    ImageButton sendEmail;
    File imageFile;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);
        mImageView = (ImageView) findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        int value = bundle.getInt("photo");
        String value2 = bundle.getString("send_photo");


            imageFile = new File(value2);
            imageUri = Uri.fromFile(imageFile);

        Bitmap photoCap = BitmapFactory.decodeFile(value2);
        mImageView.setImageBitmap(photoCap);
        sendtoTwitter = (ImageButton) findViewById(R.id.sendToTwitter);
        sendEmail = (ImageButton) findViewById(R.id.sendEmail);


        //Sending an email with the photo which was just taken, asks the user which email client they wish to use that is on their device
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(emailIntent, "Choose an Email Client"));
            }
        });



        //Sends an intent to twitter with the image.
        //Opens twitter if installed on device else will open a web browser to twitter with the image attached.
        sendtoTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.text("")
                        .image(imageUri);
                builder.show();
            }
        });
            //Twitter api connection
        TwitterAuthConfig authConfig =  new TwitterAuthConfig("bTD7w3Eog6F96AvtWNewttHZq", "xlS5taaZhlO34WFQHEh9YgBiFJnUFKyECZIoUNnJZ7TpwVLZmm");
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());

        //Login button enables you to login to twitter via the site or app connecting your account with the app
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>()
        {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                loginButton.setVisibility(View.INVISIBLE);

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);


    }

}
