package com.example.jeremy.walkies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Spannable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Jeremy on 14/12/2015.
 */
public class MainFragment extends Fragment {


        TextView welcomeTextView;
        public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
         public   String name;
        public static  MainFragment newInstance(String message)
        {
            MainFragment f = new MainFragment();
            Bundle bdl = new Bundle(1);
            // This is what you do if you want to pass information through
            bdl.putString(EXTRA_MESSAGE, message);


            f.setArguments(bdl);
            return f;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
            //Inflate the layout for this fragment



            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        public MainFragment()
        {

        }
    @Override
    public void  onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        welcomeTextView = (TextView) getActivity().findViewById(R.id.textViewWelcome);
        //Welcome message for the user
        welcomeTextView.setText(getResources().getString(R.string.welcome));
        welcomeTextView.append(" " + MainActivity.userName + " " + getResources().getString(R.string.To) + " "  +  getResources().getString(R.string.app_name));




    }



}
