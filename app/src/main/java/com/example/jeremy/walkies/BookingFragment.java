package com.example.jeremy.walkies;


import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Time;
import java.util.Locale;


/**
 * Created by Jeremy on 18/11/2015.
 */
public class BookingFragment extends Fragment {


    ListView listView;
    ImageButton addButton;
     CustomCursorAdapter customCursorAdapter;
    SQLiteDatabase sqLiteDatabase;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    MySQLiteHelper db;
    Cursor cursor;
    public static  BookingFragment newInstance(String message)
    {
        BookingFragment f = new BookingFragment();
        Bundle bdl = new Bundle(1);
        // This is what you do  if you want to pass information through
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.fragment_booking, container, false);
        listView = (ListView) view.findViewById(R.id.list_item);
        addButton = (ImageButton) view.findViewById(R.id.addButton);



        //calls getdatabase method
        getDataBase();
        //Opens the entry form to add new to database
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DatabaseEntryActivity.class);
                startActivity(intent);


            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ON CLICK TO EDIT ITEM

                //nothing is here because I could not figure out how to edit the information when i pass it to entry form to edit it
                // It is left as a placeholder so that I was gonig to do this but lack of time.
                //I am going to come back to this at a later date




            }
        });


        return  view;

    }


    public void onResume()
    {
        getDataBase();
        super.onResume();

    }




    public void onDestroy()
    {
        cursor.close(); // closes cursor when ends
        super.onDestroy();
    }


        public void getDataBase()

        {
            listView.setAdapter(customCursorAdapter); // sets listview to the custom cursor

            db = new MySQLiteHelper(getActivity());

                db.getAllBooks(); // gets everything in the database
                sqLiteDatabase = db.getReadableDatabase(); // reads the database

            String query = "SELECT * FROM Dogs ORDER BY _id ASC"; // orders the listview by id from ascending order

            cursor = sqLiteDatabase.rawQuery(query,null); // gets the cursor to  query

            // uses the custom cursor to put into the list item
             customCursorAdapter = new CustomCursorAdapter(getActivity(),R.layout.list_item,cursor,0);

        }

        @Override
    public void  onActivityCreated(Bundle savedInstanceState)
        {
            super.onActivityCreated(savedInstanceState);
            listView = (ListView)  getActivity().findViewById(R.id.list_item);

        }





}
