package com.example.jeremy.walkies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jeremy on 17/12/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DogsDB2";
    public static final String Table_BOOKING = "dogs";

    public static final String KEY_ID = "_id";
    public static final String KEY_STAFF = "_staff";
    public static final String KEY_ADDRESS = "_address";
    public static final String KEY_DATE =  "_date";
    public static final String KEY_TIME = "_time";
    public static final String KEY_TYPE = "_type";

    public static final String[] COLUMNS = {KEY_ID,KEY_STAFF,KEY_ADDRESS,KEY_DATE,KEY_TIME, KEY_TYPE};




    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create  table

        String query = "CREATE TABLE " + Table_BOOKING + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_STAFF+" TEXT," +
                KEY_ADDRESS +  " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT," +
                KEY_TYPE + " TEXT" + ");";

        // create  table
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS dogs");

        // create fresh books table
        this.onCreate(db);
    }

    //-----------------------------------------------------------------------
    //CRUD Operations - Create (add), Read (get), Update, Delete

    public void addBook(Booking book){ // adding a book

        Log.d("addBook", book.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        String n = db.getPath();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_STAFF, book.staff);
        values.put(KEY_ADDRESS, book.address);
        values.put(KEY_DATE,book.date);
        values.put(KEY_TIME,book.startTime);
        values.put(KEY_TYPE,book.typeBox);
        // 3. insert
        db.insert(Table_BOOKING, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    //getting a single entry from the database to show it
    //Not implemented but functionality is there if needed to be changed in the future

    public Booking getBook(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(Table_BOOKING, // a. table
                        COLUMNS, // b. column names
                        "_id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Booking book = new Booking();
        book.ID= Integer.parseInt(cursor.getString(0));
        book.staff = cursor.getString(1);
        book.address = cursor.getString(2);
        book.date = cursor.getString(3);
        book.startTime = cursor.getString(4);
        book.typeBox = cursor.getString(5);

        Log.d("getBooking(" + id + ")", book.toString());

        // 5. return book
        return book;
    }

    // Get All Books
    public List<Booking> getAllBooks() {
        List<Booking> books = new LinkedList<Booking>();

        // 1. build the query
        String query = "SELECT  * FROM " + Table_BOOKING;

        // 2. get reference to writable DBw1
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Booking book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Booking();
                book.ID  = Integer.parseInt(cursor.getString(0));
                book.staff = cursor.getString(1);
                book.address = cursor.getString(2);
                book.date = cursor.getString(3);
                book.startTime = cursor.getString(4);
                book.typeBox = cursor.getString(5);

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        // return books
        return books;
    }



    // Updating single entry
    //Not implemented but functionality is there if needed to be changed in the future

    public int updateBook(Booking book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("_staff", book.staff);
        values.put("_address", book.address);
        values.put("_date",book.date);
        values.put("_time",book.startTime);
        values.put("_type",book.typeBox);
        // 3. updating row
        int i = db.update(Table_BOOKING, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(book.ID) }); //selection args

        // 4. close
        db.close();

        return i;

    }



    // Deleting single entry from database
    //Not implemented but functionality is there if needed to be changed in the future
    public void deleteBook(Booking book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(Table_BOOKING,
                KEY_ID+" = ?",
                new String[] { String.valueOf(book.ID) });

        // 3. close
        db.close();

        Log.d("deleteBook", book.toString());

    }

    public Booking getBookingByDate(String date){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(Table_BOOKING, // a. table
                        COLUMNS, // b. column names
                        KEY_DATE + " = ?", // c. selections
                        new String[] { date }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor == null)
            return  null;
            else if (!cursor.moveToFirst())
        {
            cursor.close();
            return null;
        }

        // 4. build book object
        Booking book = new Booking();
        book.ID = Integer.parseInt(cursor.getString(0));
        book.staff = cursor.getString(1);
        book.address = cursor.getString(2);
        book.date = cursor.getString(3);
        book.startTime = cursor.getString(4);
        book.typeBox = cursor.getString(5);

        Log.d("getBook(" + date + ")", book.toString());

        // 5. return book
        return book;
    }

}

