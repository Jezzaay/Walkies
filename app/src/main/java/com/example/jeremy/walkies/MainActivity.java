package com.example.jeremy.walkies;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "ILt4aQhSlislTVEkDiCvqopFx";
    private static final String TWITTER_SECRET = "JrMYqpsusRj3d6R5gltAhNIbSirUwNN7Mf7L50FsHtVnsRhbdo";


    public static String userName = ""; // Static so that in the mainfragment can show the name of the user as a personalisation message
    MyPageAdapter myPageAdapter;
    ViewPager mPager;




    AlertDialog.Builder dialogBuilder;
    ListView listView;
    public  static  String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

    //Creating the fragments and viewpager
        List<Fragment> fragments = getFragments();
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setAdapter(myPageAdapter);

        callName(); // Call name function to call load user name or gets the user dialog menu up


        listView = (ListView) findViewById(R.id.list_item);
        mPager.setCurrentItem(1); //sets the item to the second fragment in list which is the main fragment
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }


    //Fragment List
    //Only main and booking due to camera has issues of it being in a fragment
    private List<android.support.v4.app.Fragment> getFragments(){
        List<android.support.v4.app.Fragment> fList = new ArrayList<android.support.v4.app.Fragment>();
        fList.add(BookingFragment.newInstance("Booking Fragment"));
        fList.add(MainFragment.newInstance("Main Fragment"));


        return fList;
    }


    @Override
    public void onResume() {
        loadUserName();

        super.onResume();

    }

    @Override
    public void onPause()
    {
        saveUserName();
        super.onPause();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
          //  Intent i = new Intent(this,UserSettingsActivity.class);
          //  startActivity(i);
            return true;

            //TODO Add settings screen so user can change username at a later date

        }
        else if (id== R.id.action_camera)
        {
            Intent intent = new Intent(this, CameraActivity.class);
            this.startActivity(intent);

        }
        else if (id==R.id.action_booking)
        {
            //Goes to the booking fragment
            mPager.setCurrentItem(0);

        }
        else if (id==R.id.action_home)
        {
            //Goes to the Main fragment
            mPager.setCurrentItem(1);
        }
    else if (id==R.id.search_bar)
        {
            Intent intent = new Intent(this, SearchActivity.class);
            this.startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void saveUserName()
    {
        //Share preference to keep the user name for the welcome message for future use.
        SharedPreferences sharedUserName = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedUserName.edit();

        editor.putString(String.valueOf(R.string.username),userName);

        editor.apply();


    }

    private void loadUserName() {

     //   SharedPreferences sharedPrefs = PreferenceManage.getDefaultSharedPreferences(this);


        //Loading the username from preference
        SharedPreferences sharedUserName = getPreferences(Context.MODE_PRIVATE);


        name = sharedUserName.getString(String.valueOf(R.string.username), userName);

        if(name.equals(""))
        {
            Toast.makeText(getApplicationContext(), R.string.noNameEntered, Toast.LENGTH_SHORT).show();

        }
        else {



         //   userName = sharedPrefs.getString("userName", null);
          userName = name;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome) + " " + userName, Toast.LENGTH_SHORT).show();
        }

    }



    private void userNameDialog()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText inPut = new EditText(this);


        dialogBuilder.setTitle(R.string.whatName);

        dialogBuilder.setView(inPut);
       dialogBuilder
               .setCancelable(false)
               .setPositiveButton(R.string.confirm,new DialogInterface.OnClickListener()
               {
                  public void onClick(DialogInterface dialog, int id)
                  {
                      //Setting the username to be whatever the user put in the text box
                      userName = inPut.getText().toString();
                      //Toast welcome  - Would have prefer snackbar but due to limitations I had to use Toast.
                      Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome)  + userName, Toast.LENGTH_SHORT).show();
                  }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                       System.exit(0);
                   }
               });


        AlertDialog dialogName  = dialogBuilder.create();
        dialogName.show(); // Showing the dialog.
    }

    public void callName()
    {
        //Checking if username is empty if it is then it calls the dialog box to allow the user to input a username
        //once username is entered the user cannot change it
        //Should have an option for the user to change the name

        loadUserName();
        if (userName.equals(""))
        {
            userNameDialog();
        }
        else if (!userName.equals(""))
        {
            loadUserName();
        }
    }



}
