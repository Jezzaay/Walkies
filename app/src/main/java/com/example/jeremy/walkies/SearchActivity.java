package com.example.jeremy.walkies;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Jeremy on 27/12/2015.
 */
public class SearchActivity extends Activity implements SearchView.OnQueryTextListener {

    CustomCursorAdapter customCursorAdapter;
    SQLiteDatabase sqLiteDatabase;
    MySQLiteHelper db;
    Cursor cursor;
    ListView listView;
    SearchView searchView;
    Calendar calendar = Calendar.getInstance();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        listView = (ListView) findViewById(R.id.list);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint(getString(R.string.search_label)); // query hint to show in the searchview

        //sets the onquerytextlistener to this activity
        searchView.setOnQueryTextListener(this);

        //calls database method
        getDataBase();

        listView.setAdapter(customCursorAdapter);


        //On close listener so that the DB will show all information
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchDB(" ");
                return false;
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(SearchActivity.this, date, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }

    public boolean onQueryTextChange(String newText) {

        //searches the Database when the text is changed in the searchview
        searchDB(newText);


        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        //searches the Database when the text is submitted in the searchview

        searchDB(query);

        return false;
    }

    @Override
    public void onDestroy()
    {
        //close the cursor when not in use
        cursor.close();

        super.onDestroy();

    }
    @Override
    protected void onNewIntent(Intent intent) {

        //Intent to search
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchDB(query);

        }
    }


    public void getDataBase()

    {

        //Gets the database to show in this listview
        db = new MySQLiteHelper(this);

        db.getAllBooks();
        sqLiteDatabase = db.getReadableDatabase();
        //ordering by id
        String query = "SELECT * FROM Dogs ORDER BY _id ASC";

        cursor = sqLiteDatabase.rawQuery(query,null);


        customCursorAdapter = new CustomCursorAdapter(this,R.layout.list_item,cursor,0);


    }


    public void searchDB(String search)
    {
        listView.setAdapter(null);


        listView.setAdapter(customCursorAdapter);
        db.getBookingByDate(search);
        sqLiteDatabase = db.getReadableDatabase();
        String query = "SELECT * FROM Dogs WHERE _date LIKE '%"+search+"%'"; // Sets the listview query to be what is LIKE of the user has entered to the search


        cursor = sqLiteDatabase.rawQuery(query,null);


        customCursorAdapter = new CustomCursorAdapter(this,R.layout.list_item,cursor,0);
    }



}
