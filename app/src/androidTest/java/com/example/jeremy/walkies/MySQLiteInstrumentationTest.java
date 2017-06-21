package com.example.jeremy.walkies;

import android.content.ContentValues;
import android.test.AndroidTestCase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * Created by Jeremy on 31/12/2015.
 */
public class MySQLiteInstrumentationTest extends AndroidTestCase
{
    private static String mDogID;
    private static String mDogStaff;
    private static String mDogAddress;
    private static String mDogDate;
    private static String mDogTime;
    private static String mDogType;
    private static String search;

    private static final String TAG = "DB TAG";
    private static MySQLiteHelper mySQLiteHelper;
    private static SQLiteDatabase db;
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDropDB()
    {
        assertTrue(mContext.deleteDatabase(MySQLiteHelper.DATABASE_NAME));
        Log.v(TAG, "testDropDB Pass");
    }


    public void testCreateDB()
    {
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(mContext);
        SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
        Log.v(TAG,"TestCreateDB Pass");
    }


    public void testInsertData()
    {
         mySQLiteHelper = new MySQLiteHelper(mContext);
         db = mySQLiteHelper.getWritableDatabase();

        mDogID = "1";
        mDogStaff = "Jeremy";
        mDogAddress = "14 Dog Walk";
        mDogDate = "13/09/2015";
        mDogTime = "12:05";
        mDogType = "Dog Sit";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.KEY_ID,mDogID);
        contentValues.put(MySQLiteHelper.KEY_STAFF,mDogStaff);
        contentValues.put(MySQLiteHelper.KEY_ADDRESS,mDogAddress);
        contentValues.put(MySQLiteHelper.KEY_DATE,mDogDate);
        contentValues.put(MySQLiteHelper.KEY_TIME,mDogTime);
        contentValues.put(MySQLiteHelper.KEY_TYPE,mDogType);

        mySQLiteHelper.addBook(new Booking(mDogStaff, mDogAddress, mDogDate, mDogTime, mDogType));

        Log.v(TAG,"AddedEntryToDB Pass");


    }


    public void testIsDataCorrectInDB()
    {
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(mContext);
        SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();
        Cursor cursor = db.query(mySQLiteHelper.Table_BOOKING,null,null,null,null,null,null);
        assertTrue(cursor.moveToFirst());

        int id = cursor.getColumnIndex(mySQLiteHelper.KEY_ID);
        String dbID = cursor.getString(id);

        int staffColumnIndex = cursor.getColumnIndex(mySQLiteHelper.KEY_STAFF);
        String Dbstaff = cursor.getString(staffColumnIndex);

        int addressColumnIndex = cursor.getColumnIndex(mySQLiteHelper.KEY_ADDRESS);
        String Dbaddress = cursor.getString(addressColumnIndex);

        int dateColumnIndex = cursor.getColumnIndex(mySQLiteHelper.KEY_DATE);
        String Dbdate = cursor.getString(dateColumnIndex);

        int timeColumnIndex = cursor.getColumnIndex(mySQLiteHelper.KEY_TIME);
        String Dbtime = cursor.getString(timeColumnIndex);

        int typeColumnIndex = cursor.getColumnIndex(mySQLiteHelper.KEY_TYPE);
        String Dbtype = cursor.getString(typeColumnIndex);

        assertEquals(mDogID, dbID);
        assertEquals(mDogStaff, Dbstaff);
        assertEquals(mDogAddress, Dbaddress);
        assertEquals(mDogDate, Dbdate);
        assertEquals(mDogTime, Dbtime);
        assertEquals(mDogType, Dbtype);
        cursor.close();
    }
    public void testSearchDB()
    {
         mySQLiteHelper = new MySQLiteHelper(mContext);
         db = mySQLiteHelper.getWritableDatabase();
        Cursor cursor = db.query(mySQLiteHelper.Table_BOOKING,null,null,null,null,null,null);
        assertTrue(cursor.moveToFirst());
        search = mDogDate;

        int dateColumnIndex = cursor.getColumnIndex(mySQLiteHelper.KEY_DATE);
        String Dbdate = cursor.getString(dateColumnIndex);


        mySQLiteHelper.getBookingByDate(search);
        assertEquals(mDogDate,Dbdate);

        cursor.close();
    }


}
