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
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    private MeetingApiService mApiService = DI.getMeetingApiService();
    private String mDateString;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy" + " " + "hh:mm");


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
                try {
                    if (!validateSubject() || !validateParticipants() || !validateDate() || !validateDate() || !validateTime()) {
                        Snackbar.make(v, "Veuillez remplir les champs en rouge correctement", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        addNewMeeting();
                        Snackbar.make(v, "La réunion a bien été ajouter!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private boolean validateSubject() {
        if (mSubject.getText().toString().trim().isEmpty()) {
            lSubject.setError("Ce champ est requis!");
            requestFocus(mSubject);
            return false;
        } else if (mSubject.getText().toString().length() < 2) {
            lSubject.setError("Ce champ doit êter supérieur à 2 caractères!");
            requestFocus(mSubject);
            return false;
        } else {
            lSubject.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateParticipants() {
        if (mParticipants.getText().toString().trim().isEmpty()) {
            lPartcipants.setError("Ce champ est requis!");
            requestFocus(mParticipants);
            return false;

        } else if (!isValidEmail()) {
            lPartcipants.setError("Ce champ doit être au format email (monemail@mail.com,monemail2@mail.com)!");
            requestFocus(mParticipants);
            return false;
        } else {
            lPartcipants.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isValidEmail() {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        StringTokenizer email = new StringTokenizer(mParticipants.getText().toString(), ",");
        boolean isValidEmail = false;
        while (email.hasMoreElements()) {
            Matcher matcher = pattern.matcher(email.nextToken());
            if (!matcher.matches()) {
                isValidEmail = false;
                break;
            } else {
                isValidEmail = true;
            }
        }
        return isValidEmail;
    }

    private boolean validateTime() throws ParseException {
        if (mTime.getText().toString().trim().isEmpty()) {
            lTime.setError("Ce champ est requis!");
            return false;
        } else if (mDate.getText().toString().trim().isEmpty()) {
            lDate.setError("Ce champ est requis!");
            return false;
        } else if (!mTime.getText().toString().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3])h[0-5][0-9]$")) {
            lTime.setError("Ce champ doit être au format Heure (13:00)!");
            requestFocus(mTime);
            return false;
        } else if (!isValideTime()) {
            lTime.setError("Une réunion est déjà prévu dans la même salle dans un intervalle de 45mn!");
            requestFocus(mTime);
            return false;
        } else {
            lTime.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isValideTime() throws ParseException {
        String newMeetingTime = mTime.getText().toString().replace("h", ":");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.FRENCH);
        Date time2 = null;
        Date time1 = format.parse(newMeetingTime.replace("h", ":"));
        boolean validTime = false;
        for (Meeting meeting : mApiService.getMeetings()) {
            String meetingTime = meeting.getmTime();
            time2 = format.parse(meetingTime.replace("h", ":"));
            long differenceinMn = (time2.getTime() - time1.getTime()) / 60000;
            DateFormat formatter2 = DateFormat.getInstance();
            SimpleDateFormat format3 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.FRENCH);
            formatter2 = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRANCE);
            Date date2 = sdf.parse(mDateString);
            Date date1 = meeting.getmDate();

            assert date1 != null;
            validTime = differenceinMn / 60000 >= 45 || !date1.equals(date2);
        }
        return validTime;

    }

    private boolean validateDate() {
        if (mDate.getText().toString().trim().isEmpty()) {
            lDate.setError("Ce champ est requis");
            return false;
        } else if (!isValideDate()) {
            lDate.setError("Ce champ doit être au format date!");
            requestFocus(mDate);
            return false;
        } else {
            lDate.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isValideDate() {
        formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        try {
            convertedDate = formatter.parse(mDate.getText().toString());
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
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
        int mSize = mApiService.getMeetings().size();
        int id = mSize + 1;
        int avatar = getRandomColor();
        String fDate = mDate.getText().toString();
        formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        try {
            //convertedDate = formatter.parse(fDate);
            convertedDate = sdf.parse(mDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String subject = mSubject.getText().toString();
        Date date = convertedDate;
        String time = mTime.getText().toString().replace("h", ":");
        String place = mPlaceList.getSelectedItem().toString();
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
                        mDateString = mDateString + " " + String.format("%02d:%02d", mHour, mMinutes);
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
                final Calendar clr = Calendar.getInstance();
                int day = clr.get(Calendar.DAY_OF_MONTH);
                int month = clr.get(Calendar.MONTH);
                int year = clr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(NewMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDateString = dayOfMonth + "/" + (monthOfYear + 1) + '/' + year;
                        mDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + '/' + year);
                      //  mTime.setText("");
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
