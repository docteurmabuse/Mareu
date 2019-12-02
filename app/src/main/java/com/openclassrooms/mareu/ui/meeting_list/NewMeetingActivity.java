package com.openclassrooms.mareu.ui.meeting_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mareu.R;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.util.Calendar;
import java.util.Random;

public class NewMeetingActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    EditText mDate;
    TimePickerDialog timePicker;
    EditText mTime;
    EditText mPlace;
    EditText mParticipants;
    EditText mSubject;
    Button mButton;
    private MeetingApiService mApiService;

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
        initDate();
        initTime();
        initBtn();
        initViews();

    }

    private void initViews() {
        mPlace = findViewById(R.id.place_input);
        mSubject = findViewById(R.id.name_input);
        mParticipants = findViewById(R.id.participants_input);
    }
    private void initBtn() {
        mButton = findViewById(R.id.form_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMeeting();
                Snackbar.make(v, "La réunion a bien été ajouter!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void addNewMeeting() {
        int mSize = mApiService.getMeetings().size();
        int id = mSize + 1;
        int avatar = getRandomColor();
        String date = mDate.getText().toString();
        String time = mTime.getText().toString();
        String place = mPlace.getText().toString();
        String subject = mSubject.getText().toString();
        String participants = mParticipants.getText().toString();
        Meeting meeting = new Meeting(id, avatar, date, time, place, subject, participants);
        mApiService.addMeeting(meeting);
    }

    private void initTime() {
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

    public void initDate() {
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
    }

}