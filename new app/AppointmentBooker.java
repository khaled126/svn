package com.example.mohsher.drsdemo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.ArrayList;
import java.util.Calendar;

import static android.graphics.Color.BLACK;

public class AppointmentBooker extends AppCompatActivity {

    //Variables
    private Toolbar toolbar;
    private TextView dateTime;
    public static AppointmentBooker appointmentBooker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booker);

        appointmentBooker = this;

        //Get IDs
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        dateTime = (TextView) findViewById(R.id.personalData);

        //Use app bar as default toolbar instead of action bar
        setSupportActionBar(toolbar);

        //App bar text
        getSupportActionBar().setTitle("BOOK APPOINTMENT");

        //Show Up icon (back)
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set Fonts
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Drugs.otf");
        dateTime.setTypeface(typeFace);



    }

    public void bookAppointment2(View v)
    {
        //Server Appointment code
        //Get reserved slots

        //Intent
        Intent intent = new Intent(AppointmentBooker.this , AppointmentBooker2.class) ;
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle appbar item clicks
        switch (item.getItemId()) {
            //Return back
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
