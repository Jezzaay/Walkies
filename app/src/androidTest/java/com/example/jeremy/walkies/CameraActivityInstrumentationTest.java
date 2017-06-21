package com.example.jeremy.walkies;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

/**
 * Created by Jeremy on 31/12/2015.
 */
public class CameraActivityInstrumentationTest extends ActivityInstrumentationTestCase2<CameraActivity> {


    private Activity mCameraActivity;
    private Intent mLaunchIntent;

    public CameraActivityInstrumentationTest()
    {
        super("com.example.jeremy.walkies",CameraActivity.class);
    }
    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mCameraActivity = getActivity();
        mLaunchIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivity(mLaunchIntent);


    }

    public void testCameraIntentWorked()
    {
        getActivity().startActivity(mLaunchIntent);
        assertEquals(mLaunchIntent, new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }


}
