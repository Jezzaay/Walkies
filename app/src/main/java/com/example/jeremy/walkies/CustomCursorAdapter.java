package com.example.jeremy.walkies;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Jeremy on 18/12/2015.
 */
public class CustomCursorAdapter extends ResourceCursorAdapter{


    //Custom cursor to display the information in the list view

    public CustomCursorAdapter(Context c, int layout, Cursor cursor, int flags)
    {
        super(c,layout,cursor,flags);
        this.notifyDataSetChanged();
    }

    @Override
    public void bindView(View v, Context context, Cursor cursor)
    {

        //Getting all the information needed to show in the listview.


            TextView staff = (TextView) v.findViewById(R.id.textViewSTAFF);
            staff.setText(cursor.getString(cursor.getColumnIndex("_staff")));

            TextView address = (TextView) v.findViewById(R.id.textViewADDRESS);
            address.setText(cursor.getString(cursor.getColumnIndex("_address")));

            TextView date = (TextView) v.findViewById(R.id.textViewDATE);
            date.setText(cursor.getString(cursor.getColumnIndex("_date")));

            TextView time = (TextView) v.findViewById(R.id.textViewTIME);
            time.setText(cursor.getString(cursor.getColumnIndex("_time")));

            TextView type = (TextView) v.findViewById(R.id.textViewTYPE);
            type.setText(cursor.getString(cursor.getColumnIndex("_type")));




    }



}
