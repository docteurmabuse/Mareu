package com.openclassrooms.mareu.ui.meeting_list.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.ui.meeting_list.NewMeetingActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final Calendar clr = Calendar.getInstance();
    public static final int minutes = clr.get(Calendar.MINUTE);
    public static int hours = clr.get(Calendar.HOUR_OF_DAY);
    public static int day = clr.get(Calendar.DAY_OF_MONTH);
    public static int month = clr.get(Calendar.MONTH);
    public static int year = clr.get(Calendar.YEAR);
    private static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
    private static Date convertedDate = null;
    private static MeetingApiService mApiService = DI.getMeetingApiService();
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static boolean isValideDate(String date) {
        try {
            convertedDate = formatter.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNotValidTime(List<Meeting> meetings, String mDateString, String placeSelected) throws ParseException {
        boolean validTime = false;
        for (Meeting meeting : meetings) {
            Date date2 = sdf.parse(mDateString);
            Date date1 = meeting.getmDate();
            String mPlace = meeting.getmPlace();
            assert date2 != null;
            long differenceinMn = Math.abs((date2.getTime() - date1.getTime()) / 60000);
            if (mPlace.equals(placeSelected)) {
                if (differenceinMn >= 45) {
                    validTime = true;
                } else {
                    validTime = false;
                    break;
                }
            } else {
                validTime = true;
            }
        }
        return !validTime;
    }

    public static boolean isValidEmail(String mParticipants) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        StringTokenizer email = new StringTokenizer(mParticipants, ",");
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

    public static void addNewMeeting(int mSize, String mDateString, String place, String subject, String participants, Context context) {
        int id = mSize + 1;
        int avatar = getRandomColor();
        formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        try {
            convertedDate = sdf.parse(mDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = convertedDate;
        Meeting meeting = new Meeting(id, avatar, date, place, subject, participants);
        mApiService.addMeeting(meeting);
        ((NewMeetingActivity) context).finish();
    }

    public static void requestFocus(View view, Context context) {
        if (view.requestFocus()) {
            ((NewMeetingActivity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public static boolean validateSubject(String mSubject, Context context, EditText view) {
        if (mSubject.trim().isEmpty()) {
            ((NewMeetingActivity) context).lSubject.setError("Ce champ est requis!");
            requestFocus(view, context);
            return false;
        } else if (mSubject.length() < 1) {
            ((NewMeetingActivity) context).lSubject.setError("Ce champ doit êter supérieur à 1 caractères!");
            requestFocus(view, context);
            return false;
        } else {
            ((NewMeetingActivity) context).lSubject.setErrorEnabled(false);
            return true;
        }
    }


}
