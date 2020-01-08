package com.openclassrooms.mareu.ui.meeting_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.addNewMeeting;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.clr;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.day;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.hours;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.isNotValidTime;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.minutes;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.month;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.validateDate;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.validateParticipants;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.validateSubject;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.validateTime;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.year;

public class NewMeetingActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    EditText mDate;
    TimePickerDialog timePicker;
    EditText mTime;
    Spinner mPlaceList;
    EditText mParticipants;
    public TextInputLayout lSubject, lPartcipants, lDate, lTime;
    EditText mSubject;
    Button mButton;

    private MeetingApiService mApiService = DI.getMeetingApiService();
    private String placeSelected;
    private String mDateString;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);

    public NewMeetingActivity() {
    }

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
        mPlaceList = findViewById(R.id.place_spinner);
        initPlaceListener();
        lSubject = findViewById(R.id.subject);
        lPartcipants = findViewById(R.id.participants_layout);
        lDate = findViewById(R.id.date_layout);
        lTime = findViewById(R.id.time_layout);
        mSubject = findViewById(R.id.name_input);
        mParticipants = findViewById(R.id.participants_input);
        mSubject.addTextChangedListener(new ValidationTextWatcher(mSubject));
        mDate.addTextChangedListener(new ValidationTextWatcher(mDate));
        mTime.addTextChangedListener(new ValidationTextWatcher(mTime));
        mParticipants.addTextChangedListener(new ValidationTextWatcher(mParticipants));
    }

    private void initPlaceListener() {
        mPlaceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                placeSelected = mPlaceList.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initBtn() {
        mButton = findViewById(R.id.form_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateSubject(mSubject.getText().toString(), NewMeetingActivity.this, mSubject) || !validateDate(mDate.getText().toString(), NewMeetingActivity.this, mDate) || !validateDate(mDate.getText().toString(), NewMeetingActivity.this, mDate) || !validateTime(mTime.getText().toString(), NewMeetingActivity.this, mTime, mDateString, mDate.getText().toString(), placeSelected) || isNotValidTime(mApiService.getMeetings(), mDateString, placeSelected) || !validateParticipants(mParticipants.getText().toString(), NewMeetingActivity.this, mParticipants)) {
                    Snackbar.make(v, "Veuillez remplir les champs en rouge correctement", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    addNewMeeting(mApiService.getMeetings().size(), mDateString, mPlaceList.getSelectedItem().toString(), mSubject.getText().toString(), mParticipants.getText().toString(), NewMeetingActivity.this);
                    Snackbar.make(v, "La réunion a bien été ajouter!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void initTime() {
        mTime = findViewById(R.id.time_input);
        mTime.setInputType(InputType.TYPE_NULL);
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //time picker dialog
                timePicker = new TimePickerDialog(NewMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int mHour, int mMinutes) {
                        mDateString = mDate.getText().toString() + " " + String.format(mHour + ":" + mMinutes);
                        mTime.setText(String.format("%02dh%02d", mHour, mMinutes));
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
                // date picker dialog
                datePicker = new DatePickerDialog(NewMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mDateString = String.format("%02d/%02d", day, month + 1) + '/' + year;
                        mDate.setText(mDateString);
                    }
                }, year, month, day);
                datePicker.getDatePicker().setMinDate(clr.getTimeInMillis());
                datePicker.show();
            }
        });
    }

    private class ValidationTextWatcher implements TextWatcher {
        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.name_input:
                    validateSubject(mSubject.getText().toString(), NewMeetingActivity.this, mSubject);
                    break;
                case R.id.date_input:
                    validateDate(mDate.getText().toString(), NewMeetingActivity.this, mDate);
                    break;
                case R.id.time_input:
                    validateTime(mTime.getText().toString(), NewMeetingActivity.this, mTime, mDateString, mDate.getText().toString(), placeSelected);
                    break;
                case R.id.participants_input:
                    validateParticipants(mParticipants.getText().toString(), NewMeetingActivity.this, mParticipants);
                    break;
            }
        }
    }
}
