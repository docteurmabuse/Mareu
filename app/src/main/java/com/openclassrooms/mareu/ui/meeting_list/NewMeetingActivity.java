package com.openclassrooms.mareu.ui.meeting_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mareu.R;

import java.util.Calendar;

public class NewMeetingActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    EditText mDate;
    TimePickerDialog timePicker;
    EditText mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDate = findViewById(R.id.date_input);
        mDate.setInputType(InputType.TYPE_NULL);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar clr = Calendar.getInstance();
                int day = clr.get(Calendar.DAY_OF_MONTH);
                int month = clr.get(Calendar.MONTH);
                int year = clr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(NewMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + '/' + year);
                    }
                }, year, month, day);
                datePicker.getDatePicker().setMinDate(clr.getTimeInMillis());
                datePicker.show();
            }
        });

        mTime = findViewById(R.id.time_input);
        mTime.setInputType(InputType.TYPE_NULL);
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar clr = Calendar.getInstance();
                int hours = clr.get(Calendar.HOUR_OF_DAY);
                final int minutes = clr.get(Calendar.MINUTE);
                //time picker dialog
                timePicker = new TimePickerDialog(NewMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int mHour, int mMinutes) {
                        mTime.setText(mHour + ":" + mMinutes);
                    }
                }, hours, minutes, true);
                timePicker.show();
            }
        });
    }

}
