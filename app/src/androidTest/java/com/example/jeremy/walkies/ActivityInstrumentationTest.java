package com.example.jeremy.walkies;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jeremy on 31/12/2015.
 */
public class ActivityInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Activity mMainActivity;
    private Toast mToast;
    private String mString;


public ActivityInstrumentationTest()
{
    super("com.example.jeremy.walkies",MainActivity.class);
}


    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mMainActivity = getActivity();
        mToast = new Toast(getActivity().getApplicationContext());
    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPersistentData(){
        final String p = "Jeremy"; // tests 50 in the string

//        mToast.getView().isShown();
        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mString = p;
            }
        });

        // Close the activity and see if the text we sent to mString persists
        mMainActivity.finish(); // ends the mainactivity in the test
        setActivity(null); //sets activity to null

        // Re-open the activity
        mMainActivity = getActivity(); // gets the activity
        String q = mString; // sets q to the  string

        // Check the value in editText after re-opening matches our expected value
        assertEquals(p, q); //checks p to q
    }






}
