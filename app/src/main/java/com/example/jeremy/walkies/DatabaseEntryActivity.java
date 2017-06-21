package com.example.jeremy.walkies;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Jeremy on 18/12/2015.
 */
public class DatabaseEntryActivity extends Activity{

    Button confirmButton;
    Button cancelButton;
    EditText editTextStaff;
    EditText editTextAddress;
    EditText editTextDate;
    RadioButton radioButtonDogWalk;
    String typeDog;
    RadioButton radioButtonDogSit;
    boolean boolSubmit;
    boolean dogSitday;
    boolean dogTimeDay;
    boolean staffBusy;

    TimePicker timePicker;
    SQLiteDatabase sqLiteDatabase;
    MySQLiteHelper mySQLiteHelper;
    Calendar calendar = Calendar.getInstance();
    Cursor cursor;
    String type;


    private SimpleDateFormat simpleDateFormat;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_database_entry);
        mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        editTextStaff = (EditText) findViewById(R.id.editTextSTAFF);
        editTextAddress = (EditText) findViewById(R.id.editTextADDRESS);
        editTextDate = (EditText) findViewById(R.id.editTextDATE);
        radioButtonDogWalk = (RadioButton) findViewById(R.id.radioButtonDogWalk);
        radioButtonDogSit = (RadioButton) findViewById(R.id.radioButtonDogSit);


        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);




        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText();
            }
        };


        //When date edit text has focus then it opens the datepicker dialog to pick the date, instead of the user typing the date in
        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(DatabaseEntryActivity.this, date, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        //Time picker for the user to pick the hour
        timePicker = (TimePicker) findViewById(R.id.timePicker2);

        timePicker.setIs24HourView(true);
        timePicker.setCurrentMinute(0);

        //if cancel button nothing is added and just closed the application until the user puts in a name
        //Name is changable in the settings
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = timePicker.getCurrentHour(); // Gets the current hour of the phone clock
                String AMPM = "AM";
                // If hour is greater than 12 in the 24 hour clock e.g. 13, 14 etc then -12 and set it to PM
                // if hour is less than 12 e.g. 4,6,11 etc set it to AM
                if (hour > 12)
                {
                    hour = hour -12;
                    AMPM = "PM";

                }
                if( hour == 12)
                {
                    hour = 12;
                    AMPM = "PM";
                }

                String staff = editTextStaff.getText().toString();
                String address = editTextAddress.getText().toString();
                String date = editTextDate.getText().toString();
                // I am hard coding the minute to be 00 this is bad but cant figure out how to do it in timepicker so minute is disabled
                // There probably is a better way to do it but couldn't figure it out.
                String timep = hour + ":" + "00" + " " + AMPM;
                if(radioButtonDogSit.isChecked())
                {
                    checkDogSitDay(date,typeDog,staff);
                    typeDog = radioButtonDogSit.getText().toString();
                }
                else if (radioButtonDogWalk.isChecked())
                {
                    typeDog = radioButtonDogWalk.getText().toString();

                }

                checkStaff(date, staff);
                checkStaffDayTime(date, String.valueOf(hour), staff);

                submitOnlyIfNotEmpty();
                if (boolSubmit) { // Adding new entry to the database
                    mySQLiteHelper.addBook(new Booking(staff, address, date, timep, typeDog));
                    finish();

                }
                else
                {
                    if(!dogSitday & !staffBusy )
                    {
                        Toast.makeText(getApplicationContext(), R.string.fillTextFields,Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });



    }
    private void updateDateText()
    {
        //Updating and formating the date for submission
        String format = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format,Locale.UK);
        editTextDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void checkStaffDayTime(String day, String time, String staff)
    {
        try {
            //Checks the staff if they are busy or not at the time

            //Gates the date, checks the staff, gets the day checks the time. Sees if the staff is busy on that day
            if (mySQLiteHelper.getBookingByDate(day).staff.contains(staff) & mySQLiteHelper.getBookingByDate(day).startTime.contains(time)) {
                dogTimeDay = true;

                Toast.makeText(getApplicationContext(), R.string.workerSetAtThisTime, Toast.LENGTH_SHORT).show();


            } else {
                dogTimeDay = false;

            }
        }
        catch (NullPointerException e)
        {
            dogTimeDay = false;

        }

    }

    private void checkStaff(String day, String staff)
    {
        //If staff is doing dog sit then can't dog walk that day

        //Gets the dog sit string from Strings xml
            type = getResources().getString(R.string.dogSit);


        try {

            if (mySQLiteHelper.getBookingByDate(day).typeBox.contains(type) && mySQLiteHelper.getBookingByDate(day).staff.contains(staff)) {
                staffBusy = true;
                Toast.makeText(getApplicationContext(), R.string.workerSetAtThisTime, Toast.LENGTH_SHORT).show();


            } else {
                staffBusy = false;

            }
        }
        catch (NullPointerException e)
        {
            staffBusy = false;

        }

    }


    private void checkDogSitDay(String day, String typeDog, String staff)
    {


//Checks if dog sit on already on day if so doesn't allow the user and informs the user of this

try {


    if (mySQLiteHelper.getBookingByDate(day).typeBox.contains(typeDog) & mySQLiteHelper.getBookingByDate(day).staff.contains(staff)) {
        dogSitday = true;
        Toast.makeText(getApplicationContext(), R.string.dogSitAlreadyDay & R.string.workerSetAtThisTime, Toast.LENGTH_SHORT).show();


    } else {
        dogSitday = false;

    }
}
catch (NullPointerException e)
{
    dogSitday = false;

}






    }

    public void submitOnlyIfNotEmpty()
    {

        //Only submit if these have information in them
        boolean boolStaff = editTextStaff.getText().toString().length() > 1;
        boolean boolAddress = editTextAddress.getText().toString().length() >1;
        boolean boolDate = editTextDate.getText().toString().length() >1;

        //Only allows to be submitted if this is true
        if (boolStaff & boolAddress & boolDate & !dogSitday & !dogTimeDay & !staffBusy)
        {
             boolSubmit = true;
        }

    }






}
