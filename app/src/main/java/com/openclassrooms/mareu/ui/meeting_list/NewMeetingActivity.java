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
import com.openclassrooms.mareu.ui.meeting_list.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.addNewMeeting;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.clr;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.day;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.hours;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.isNotValidTime;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.isValidEmail;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.minutes;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.month;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.requestFocus;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.validateSubject;
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
                try {
                    if (!validateSubject(mSubject.getText().toString(), NewMeetingActivity.this, mSubject) || !validateDate() || !validateDate() || !validateTime() || isNotValidTime(mApiService.getMeetings(), mDateString, placeSelected) || !validateParticipants()) {
                        Snackbar.make(v, "Veuillez remplir les champs en rouge correctement", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        addNewMeeting(mApiService.getMeetings().size(), mDateString, mPlaceList.getSelectedItem().toString(), mSubject.getText().toString(), mParticipants.getText().toString(), NewMeetingActivity.this);
                        Snackbar.make(v, "La réunion a bien été ajouter!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validateParticipants() {
        if (mParticipants.getText().toString().trim().isEmpty()) {
            lPartcipants.setError("Ce champ est requis!");
            requestFocus(mParticipants, NewMeetingActivity.this);
            return false;
        } else if (!isValidEmail(mParticipants.getText().toString())) {
            lPartcipants.setError("Ce champ doit être au format email (monemail@mail.com,monemail2@mail.com)!");
            requestFocus(mParticipants, NewMeetingActivity.this);
            return false;
        } else {
            lPartcipants.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTime() throws ParseException {
        Date time = null;
        if (mDateString != null) {
            time = sdf.parse(mDateString);
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //getTime() returns the current date in default time zone
        Date now = calendar.getTime();

        if (mTime.getText().toString().trim().isEmpty()) {
            lTime.setError("Ce champ est requis!");
            return false;
        } else if (mDate.getText().toString().trim().isEmpty()) {
            lDate.setError("Ce champ est requis!");
            return false;
        } else if (time.before(now)) {
            lTime.setError("Vous ne pouvez pas organiser de réunion avant l'heure actuelle!");
            return false;
        } else if (!mTime.getText().toString().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3])h[0-5][0-9]$")) {
            lTime.setError("Ce champ doit être au format Heure (13:00)!");
            requestFocus(mTime, NewMeetingActivity.this);
            return false;
        } else if (isNotValidTime(mApiService.getMeetings(), mDateString, placeSelected)) {
            lTime.setError("Une réunion est déjà prévu dans la même salle dans un intervalle de 45mn!");
            requestFocus(mTime, NewMeetingActivity.this);
            return false;
        } else {
            lTime.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDate() {
        if (mDate.getText().toString().trim().isEmpty()) {
            lDate.setError("Ce champ est requis");
            return false;
        } else if (!Utils.isValideDate(mDate.getText().toString())) {
            lDate.setError("Ce champ doit être au format date!");
            requestFocus(mDate, NewMeetingActivity.this);
            return false;
        } else {
            lDate.setErrorEnabled(false);
            return true;
        }
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
                    validateDate();
                    break;
                case R.id.time_input:
                    try {
                        validateTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.participants_input:
                    validateParticipants();
                    break;
            }
        }
    }
}
