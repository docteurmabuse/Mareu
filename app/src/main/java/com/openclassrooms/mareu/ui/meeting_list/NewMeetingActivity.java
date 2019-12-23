package com.openclassrooms.mareu.ui.meeting_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
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
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class NewMeetingActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    EditText mDate;
    TimePickerDialog timePicker;
    EditText mTime;
    Spinner mPlaceList;
    EditText mParticipants;
    TextInputLayout lSubject, lPartcipants, lDate, lTime;
    EditText mSubject;
    Button mButton;

    SimpleDateFormat df;
    DateFormat formatter = null;
    Date convertedDate = null;


    public NewMeetingActivity() {
        df = new SimpleDateFormat("dd/MM/yyyy");
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

    private void initBtn() {
        mButton = findViewById(R.id.form_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateSubject() || !validateParticipants() || !validateDate() || !validateDate() || !validateTime()) {
                    Snackbar.make(v, "Veuillez remplir les champs en rouge correctement", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    addNewMeeting();
                    Snackbar.make(v, "La réunion a bien été ajouter!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    private boolean validateSubject() {
        if (mSubject.getText().toString().trim().isEmpty()) {
            lSubject.setError("Ce champ est requis!");
            requestFocus(mSubject);
            return false;
        } else if (mSubject.getText().toString().length() > 5) {
            lSubject.setError("Le champ doit êter supérieur à 5 caractères!");
            requestFocus(mSubject);
            return false;
        } else {
            lSubject.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateParticipants() {
        if (mParticipants.getText().toString().trim().isEmpty()) {
            lPartcipants.setError("*Ce champ est requis!");
            requestFocus(mParticipants);
            return false;

        } else if (mParticipants.getText().toString().length() > 5) {
            lSubject.setError("Le champ doit êter supérieur à 5 caractères!");
            requestFocus(mSubject);
            return false;
        } else {
            lPartcipants.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTime() {
        if (mDate.getText().toString().trim().isEmpty()) {
            lDate.setError("Ce champ est requis!");
            return false;
        } else if (mDate.getText().toString().length() > 4) {
            lSubject.setError("Le champ doit êter supérieur à 5 caractères!");
            requestFocus(mSubject);
            return false;
        } else {
            lTime.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDate() {
        if (mTime.getText().toString().trim().isEmpty()) {
            lTime.setError("Ce champ est requis");
            return false;
        } else if (mTime.getText().toString().length() > 4) {
            lSubject.setError("Le champ doit êter supérieur à 5 caractères!");
            requestFocus(mSubject);
            return false;
        } else {
            lDate.setErrorEnabled(false);
            return true;
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void addNewMeeting() {
        MeetingApiService mApiService = DI.getMeetingApiService();
        int mSize = mApiService.getMeetings().size();
        int id = mSize + 1;
        int avatar = getRandomColor();
        String fDate = mDate.getText().toString();
        formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        try {
            convertedDate = formatter.parse(fDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = convertedDate;
        String time = mTime.getText().toString();
        String place = mPlaceList.getSelectedItem().toString();
        String subject = mSubject.getText().toString();
        String participants = mParticipants.getText().toString();
        Meeting meeting = new Meeting(id, avatar, date, time, place, subject, participants);
        mApiService.addMeeting(meeting);
        finish();
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
                        mTime.setText(mHour + "h" + mMinutes);
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
                    validateSubject();
                    break;
                case R.id.date_input:
                    validateDate();
                    break;
                case R.id.time_input:
                    validateTime();
                    break;
                case R.id.participants_input:
                    validateParticipants();
                    break;
            }
        }
    }


}
