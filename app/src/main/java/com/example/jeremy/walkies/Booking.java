package com.example.jeremy.walkies;

import android.widget.CheckBox;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Jeremy on 14/12/2015.
 */
public class Booking {



    public String staff;
    public String address;
    public String date;
    public String typeBox;
    public String startTime;
    public int ID;


    public Booking()
    {

    }

    public Booking(String pStaff, String pAddress, String pDate, String pTime, String pType){


        this.staff = pStaff;
        this.address = pAddress;
        this.date = pDate;
        this.startTime = pTime;
        this.typeBox = pType;
    }

    public String toString(){
        return( "Dog [id=" + ID +
                "\nDate: " + date +
                "\n Start Time: " +startTime +
                "\n Type" + typeBox +
                "\nStaff: " + staff +
                "\nAddress " +address);
    }

    public void setStaff(String pStaff){ staff = pStaff; }

    public void setAddress(String pAddress) { address = pAddress;}

    public void setID(Integer pID) {ID = pID;}

    public void setTime (String pTime) {startTime = pTime;}

    public String getTime () {return startTime;}

    public void setTypeBox(String pType) {typeBox = pType;}

    public String getTypeBox () {return typeBox;}

    public  String getAddress() {return address;}

    public String getStaff() {return staff;}

    public void setDate(String pDate) {date = pDate;}

    public  String getDate() {return date;}



}
