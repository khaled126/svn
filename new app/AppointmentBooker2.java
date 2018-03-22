package com.example.mohsher.drsdemo;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import static android.graphics.Color.BLACK;

public class AppointmentBooker2 extends AppCompatActivity {

    //Variables
    private Toolbar toolbar;
    private MaterialCalendarView materialCalendarView;
    private TextView dateTime;
    private TextView appointmentDateTF;
    private TextView appointmentDateValueTF;
    private TextView appointmentTimeTF;
    private TextView appointmentTimeValueTF;
    private LinearLayout dateLL;
    private LinearLayout timeLL;
    private LinearLayout appLL;
    private Button submitBTN;
    private AppointmentsFragment appointmentsFragment;

    public void showTextFields(CalendarDay date, String selectedSlot)
    {
        appointmentDateTF = (TextView) findViewById(R.id.appointmentDateTF);
        appointmentDateValueTF = (TextView) findViewById(R.id.appointmentDateValueTF);
        appointmentTimeTF = (TextView) findViewById(R.id.appointmentTimeTF);
        appointmentTimeValueTF = (TextView) findViewById(R.id.appointmentTimeValueTF);
        dateLL = (LinearLayout) findViewById(R.id.dateLL);
        timeLL = (LinearLayout) findViewById(R.id.timeLL);
        appLL = (LinearLayout) findViewById(R.id.appLL);
        submitBTN = (Button) findViewById(R.id.submitBTN);

        appointmentDateTF.setText("Appointment Date: ");
        appointmentDateValueTF.setText(date.getDay() + "/" + (date.getMonth()+1) + "/" + date.getYear());
        appointmentTimeTF.setText("Appointment Time: ");
        appointmentTimeValueTF.setText(selectedSlot);
        //dateLL.setBackground(getResources().getDrawable(R.drawable.rectangle));
        //timeLL.setBackground(getResources().getDrawable(R.drawable.rectangle));
        appLL.setBackground(getResources().getDrawable(R.drawable.rectangle));


    }

    //Show available time slots for selected date in an Alert Dialog
    public void showSlots(final CalendarDay date)
    {
        //Get available slots for date

        //Build AlertDialog
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(AppointmentBooker2.this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select time slot");

        //Populate Array dialog List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AppointmentBooker2.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("9:00 am");
        arrayAdapter.add("9:30 am");
        arrayAdapter.add("12:30 pm");
        arrayAdapter.add("13:00 pm");
        arrayAdapter.add("13:30 pm");
        arrayAdapter.add("16:30 pm");
        arrayAdapter.add("18:30 pm");


        //Set onClick for Cancel Button
        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //Set adapter for AlertDialog List
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Place selected slot in selectedSlot String
                final String selectedSlot = arrayAdapter.getItem(which);

                //Show confirmation AlertDialog
                AlertDialog.Builder builderInner = new AlertDialog.Builder(AppointmentBooker2.this);
                builderInner.setMessage(selectedSlot);
                builderInner.setTitle("Selected time slot is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        showTextFields(date, selectedSlot);
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booker2);

        //Get IDs
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.appointmentsCLNDR);
        dateTime = (TextView) findViewById(R.id.dateTime);


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


        ArrayList<CalendarDay> enabledDates = new ArrayList<>();


        enabledDates.add(new CalendarDay(2017, 0, 15));


        materialCalendarView.addDecorator(new DayEnableDecorator(BLACK, enabledDates));

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                showSlots(date);
            }
        });


    }

    //Submit appointment details to server
    public void submitAppointment(View v)
    {
        finish();
        AppointmentBooker.appointmentBooker.finish();
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
